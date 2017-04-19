package com.common.util.image;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.common.Common;
import com.common.util.C_ToastUtil;

import java.io.File;

/**
 * Created by ricky on 2016/07/04.
 * <p>
 * 通过系统相册获取图片的方法
 */
public class C_ImageGetBySysHelper {

    public static final int CUT_SIZE = 400;

    public static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 3;// 剪切

    private static C_ImageGetBySysHelper instance;

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    private C_ImageGetBySysHelper() {
    }

    public static C_ImageGetBySysHelper getInstance() {
        if (instance == null) {
            instance = new C_ImageGetBySysHelper();
        }
        return instance;
    }

    /**
     * 开启相册获取图片
     */
    public void startActivityForGalleryResult(Activity activity) {
        //激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 开启相机获取图片
     */
    public void startActivityForCameraResult(Activity activity) {
        //激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //判断存储卡是否可以用， 可用进行储存
        if (hasSdCard()) {
            tempFile = new File(Common.image.IMAGE_DOWNLOAD_PATH, PHOTO_FILE_NAME);
            //从文件中创建Uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private boolean hasSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 剪切图片
     */
    public void startActivityForCutResult(Activity activity, Uri uri) {
        //裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框的比例 1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", CUT_SIZE);
        intent.putExtra("outputY", CUT_SIZE);

        //图片格式
        intent.putExtra("outputFormat", "JPEG");
        //取消人脸识别
        intent.putExtra("noFaceDetection", true);
        //返回数据 ：true 返回bitmap / false 返回 uri .
        intent.putExtra("return-data", false);
        // 指定裁剪后的数据保存路径，可以不指定。
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public Uri onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                //从相册返回的数据
                if (data != null) {
                    return data.getData();
                }
                break;
            case PHOTO_REQUEST_CAMERA:
                //从相机返回数据
                if (hasSdCard()) {
                    return Uri.fromFile(tempFile);
                } else {
                    C_ToastUtil.showSingle("未找到存储卡，无法保存照片");
                }
                break;
            case PHOTO_REQUEST_CUT:
                //从剪切图片返回的数据
                if (data != null) {
                    return data.getData();
                }
                break;
        }
        return null;
    }
}


