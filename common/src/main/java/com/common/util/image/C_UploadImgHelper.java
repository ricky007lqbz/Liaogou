package com.common.util.image;

import android.graphics.Bitmap;

import com.common.util.C_L;
import com.common.util.C_LocalDataManager;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2016/07/04.
 * <p>
 * 上传图片的工具类
 *
 */
public class C_UploadImgHelper {

    private static C_UploadImgHelper instance;

    private UploadManager uploadManager;
    public static List<String> upLoadedImages = new ArrayList<>();// 上传成功图片保存

    private C_UploadImgHelper(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public static C_UploadImgHelper getInstance() {
        if (instance == null) {
            instance = new C_UploadImgHelper(new UploadManager());
        }
        return instance;
    }

    /**
     * 上传多张图片
     */
    public void upLoadPictures(List<String> picturesPaths,
                               OnPhotoUploadListener onPhotoUploadListener) {
        upLoadedImages.clear(); // 先把上传成功的图片url清空
        if (picturesPaths != null && picturesPaths.size() > 0) {
            upLoadPicture(picturesPaths, 0, onPhotoUploadListener);
        } else {
            onPhotoUploadListener.onUploadAllSuccess(null);
        }
    }

    /**
     * 上传单张图片
     */
    public void upLoadPicture(String imgPath, OnPhotoUploadListener onPhotoUploadListener) {
        List<String> picturesPaths = new ArrayList<>();
        picturesPaths.add(imgPath);
        upLoadPictures(picturesPaths, onPhotoUploadListener);
    }

    /**
     * 递归调用上传
     */
    private void upLoadPicture(final List<String> picturesPaths, final int position,
                               final OnPhotoUploadListener onPhotoUploadListener) {
        if (uploadManager == null | picturesPaths == null | onPhotoUploadListener == null)
            return;
        if (position == 0) {
            onPhotoUploadListener.onUploadStart(position);
        }
//        String key = System.currentTimeMillis() + picturesPaths.get(position);
        String key = System.currentTimeMillis() + "" + new Object().hashCode();
        String picturesPath = picturesPaths.get(position);
        final Bitmap bitmap = C_BitmapUtils.compressWithWidth(picturesPath, 800);        // 压缩成宽度为800
        // 上传回调函数
        UpCompletionHandler upCompletionHandler = new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info == null) {
                    onPhotoUploadListener.onUploadFailed(position);                        // 上传失败
                    return;
                }
                if (!info.isOK()) {
                    C_L.d("upload " + res);
                    onPhotoUploadListener.onUploadFailed(position);                        // 上传失败
                } else {
                    if (bitmap == null)
                        return;
                    // 上传成功
                    String upLoadedImage = C_LocalDataManager.getQiNiuDomain()
                            + key
                            + "?w=" + bitmap.getWidth() + "&h="
                            + bitmap.getHeight();
                    upLoadedImages.add(upLoadedImage);
                    onPhotoUploadListener.onUploadSuccess(position,
                            upLoadedImage);
                    if (position + 1 < picturesPaths.size()) {
                        // 还没全部完成，继续上传
                        upLoadPicture(picturesPaths, position + 1, onPhotoUploadListener);
                    } else {
                        // 全部上传完成
                        onPhotoUploadListener.onUploadAllSuccess(upLoadedImages);
                    }
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        };
        // 上传进度回调。
        UpProgressHandler upProgressHandler = new UpProgressHandler() {

            @Override
            public void progress(String key, double percent) {
                onPhotoUploadListener.progress(key, percent);
            }
        };

        // 如果需要进度通知、crc校验、中途取消、指定mimeType则需要填写相应字段
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                upProgressHandler, null);
        if (bitmap != null)
            if (picturesPath.endsWith(".gif")) {
                uploadManager
                        .put(new File(picturesPath), key + ".gif", C_LocalDataManager.getQiNiuToken(),
                                upCompletionHandler, uploadOptions);
            } else {
                uploadManager.put(C_BitmapUtils.bitmap2Bytes(bitmap,
                        Bitmap.CompressFormat.JPEG), key + ".jpg", C_LocalDataManager.getQiNiuToken(),
                        upCompletionHandler, uploadOptions);
            }
        else {
            if (position + 1 < picturesPaths.size()) {
                // 还没全部完成，继续上传
                upLoadPicture(picturesPaths, position + 1, onPhotoUploadListener);
            }
        }

    }

    /**
     * 上传图片回调
     */
    public interface OnPhotoUploadListener {
        // 图片上传开始
        void onUploadStart(int position);

        // 图片上传成功
        void onUploadSuccess(int position, String url);

        // 图片上传失败
        void onUploadFailed(int position);

        // 上传进度
        void progress(String key, double percent);

        // 图片全部上传成功
        void onUploadAllSuccess(List<String> upLoadedImages);
    }


}
