package com.common.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.common.Common;
import com.common.util.C_L;
import com.common.util.C_LocalShareHelper;
import com.common.util.C_NetworkUtil;
import com.common.util.C_ResUtil;
import com.common.util.C_ToastUtil;
import com.common.util.glide.C_ImageLoaderManager;

/**
 * Created by ricky on 2016/08/17.
 * <p/>
 * 必须在app创建的时候初始化的数据
 */
public class C_Init {

    /**
     * 初始化必须在application里运行，否则会内存溢出
     */
    public static void init(Application context, boolean isDebug) {
        Common.IS_DEBUG = isDebug;
        C_L.init(Common.IS_DEBUG); //初始化打印log工具
        C_NetworkUtil.init(context); //初始化网络工具
        C_LocalShareHelper.init(context); //初始化本地保存工具
        C_ToastUtil.init(context); // 初始化toast工具
        C_ImageLoaderManager.init(context);//初始化图片管理器
        C_ResUtil.init(context); //初始化资源管理工具
    }

    public static void initPackageName(String host, String packageName) {
        //请求host
        Common.net.HOST = host;
        Common.PACKAGE_NAME = packageName;
    }

    public static void initCommon(int dfCircleImg, int dfImg, String apiLogin) {
        Common.init(dfCircleImg, dfImg, apiLogin);
    }

    /**
     * Environment.getDataDirectory() = /data
     * Environment.getDownloadCacheDirectory() = /cache
     * Environment.getExternalStorageDirectory() = /mnt/sdcard
     * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
     * Environment.getRootDirectory() = /system
     * getPackageCodePath() = /data/app/com.my.app-1.apk
     * getPackageResourcePath() = /data/app/com.my.app-1.apk
     * getCacheDir() = /data/data/com.my.app/cache
     * getDatabasePath(“test”) = /data/data/com.my.app/databases/test
     * getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
     * getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
     * getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
     * getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
     * getFilesDir() = /data/data/com.my.app/files
     * <p/>
     * <p/>
     * <p>
     * 可以看到，
     * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，
     * <p>
     * 否则就调用getCacheDir()方法来获取缓存路径。前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个路径，
     * <p>
     * 而后者获取到的是 /data/data/<application package>/cache 这个路径。
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
