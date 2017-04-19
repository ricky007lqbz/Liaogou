package com.common.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.common.Common;
import com.common.exception.C_HttpNoFoundException;
import com.common.util.C_ArrayUtil;
import com.common.util.C_L;
import com.common.util.C_ToastUtil;
import com.common.util.rxjava.C_RxResultHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ricky on 2016/10/8.
 * <p>
 * 下载相关
 */

public class C_HttpDownload {

    private Map<String, Call> mRequestQueueMap;

    private static C_HttpDownload instance;

    private C_HttpDownload() {
        mRequestQueueMap = new HashMap<>();
    }

    public static C_HttpDownload getInstance() {
        if (instance == null) {
            instance = new C_HttpDownload();
        }
        return instance;
    }

    private void addRequestQueue(String url, Call call) {
        if (C_ArrayUtil.isEmpty(mRequestQueueMap)) {
            mRequestQueueMap.put(url, call);
        }
    }

    /**
     * 清空请求队列
     */
    private void removeRequestQueue(String url) {
        Call call = mRequestQueueMap.get(url);
        if (null != call) {
            try {
                if (!call.isExecuted()) {
                    call.execute();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清空所有的请求队列
     */
    public void clearAllRequestQueue() {
        if (!C_ArrayUtil.isEmpty(mRequestQueueMap)) {
            mRequestQueueMap.clear();
        }
    }

    /**
     * 下载文件
     */
    public void downloadFile(final String url, final String filePath, final C_OnDownloadRequestListener<String> listener) {
        Call<ResponseBody> call = C_HttpManager.getDefaultService().downloadFileFromNet(url);
        removeRequestQueue(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    C_RxResultHelper.createData(response.body())
                            .subscribeOn(Schedulers.newThread()) //在新线程中实现该方法
                            .map(new Func1<ResponseBody, String>() {
                                @Override
                                public String call(ResponseBody responseBody) {
                                    String fileName = getFileName(url);
                                    if (saveFileToDisc(filePath, fileName, responseBody, listener)) { //保存成功
                                        return filePath + fileName;//返回文件路径
                                    }
                                    return null;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())//在Android主线程中展示
                            //添加订阅，回调传给listener
                            .subscribe(C_RxResultHelper.createObserver(url, -1, true, listener));
                } else {
                    if (response.code() > 400) {
                        listener.onRequestFailed(-1, new C_HttpNoFoundException(), url);
                    }
                    if (listener != null) {
                        listener.onRequestFinish(-1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener != null) {
                    listener.onRequestFailed(-1, t, url);
                    listener.onRequestFinish(-1);
                }
            }
        });
        addRequestQueue(url, call);
    }

    /**
     * 下载图片
     */
    public void downloadPic(final String url, final C_OnNetRequestListener<Bitmap> listener) {
        Call<ResponseBody> call = C_HttpManager.getDefaultService().downloadPicFromNet(url);
        removeRequestQueue(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    C_RxResultHelper.createData(response.body())
                            .subscribeOn(Schedulers.newThread()) //在新线程中实现该方法
                            .map(new Func1<ResponseBody, Bitmap>() {
                                @Override
                                public Bitmap call(ResponseBody responseBody) {
                                    String fileName = getImageName(url, getSuffix(response));
                                    if (savePicToDisc(fileName, responseBody)) { //保存成功
                                        return BitmapFactory.decodeFile(Common.image.IMAGE_DOWNLOAD_PATH + fileName);//返回一个bitmap对象
                                    }
                                    return null;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())//在Android主线程中展示
                            //添加订阅，回调传给listener
                            .subscribe(C_RxResultHelper.createObserver(url, -1, true, listener));
                } else {
                    if (listener != null) {
                        listener.onRequestFinish(-1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener != null) {
                    listener.onRequestFailed(-1, t, url);
                    listener.onRequestFinish(-1);
                }
            }
        });
        addRequestQueue(url, call);
    }

    private String getFileName(String url) {

        if (!TextUtils.isEmpty(url)) {
            String name = url.substring(url.lastIndexOf("/"));

            String[] strings = name.split("\\?");
            if (strings.length > 0) {
                name = strings[0];
            }
            return name;
        }
        return null;
    }

    private String getImageName(String url, String suffix) {

        if (!TextUtils.isEmpty(url)) {
            String name = url.substring(url.lastIndexOf("/"));

            String[] strings = name.split("\\?");
            if (strings.length > 0) {
                name = strings[0];
            }
            name = name.replace(".jpg", "").replace(".png", "")
                    .replace(".gif", "");
            name += suffix;
            return name;
        }
        return System.currentTimeMillis() + ".jpg";
    }

    private String getSuffix(Response response) {
        Headers headers = response.headers();
        String suffix = ".jpg";
        if (null != headers && null != (suffix = headers.get("Content-Type"))) {
            suffix = suffix.replace("image/", ".");
        }
        if (suffix != null) {
            if (!suffix.endsWith(".jpg") && !suffix.endsWith(".png")
                    && !suffix.endsWith(".gif")
                    && !suffix.endsWith(".jpeg")
                    ) {
                suffix = ".jpg";
            }
        }
        return suffix;
    }

    /**
     * 保存图片
     */
    private boolean savePicToDisc(String fileName, ResponseBody body) {
        return saveFileToDisc(Common.image.IMAGE_DOWNLOAD_PATH, fileName, body, null);
    }

    /**
     * 保存图片
     */
    private boolean saveFileToDisc(String filePath, String fileName, ResponseBody body, C_OnDownloadRequestListener<String> listener) {
        if (body == null || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            C_L.e("C_HttpDownload", "文件名或文件路径为空！");
            return false;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdir()) {
                return false;
            }
        }
        try {
            //要下载文件的大小
            long fileSize = body.contentLength();

            File file = new File(filePath + fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            } else {
                if (filePath.equals(Common.image.IMAGE_DOWNLOAD_PATH)) {
                    C_ToastUtil.show("图片已经保存过了！");
                    return false;
                } else {
                    if (fileSize <= file.length()) {
                        C_L.w("C_HttpDownload", "文件已经保存了！");
                        return false;
                    }
                }
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    if (listener != null) {
                        listener.onDownloading(fileSizeDownloaded, fileSize, false);
                    }
                    C_L.d("saveFileToDisc", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                if (listener != null) {
                    listener.onDownloading(fileSizeDownloaded, fileSize, true);
                }
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
