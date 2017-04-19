package com.common.util;

import android.content.Context;
import android.widget.TextView;

import com.common.Common;
import com.common.util.glide.C_ImageLoaderManager;

import java.io.File;

/**
 * Created by ricky on 2016/07/26.
 * <p>
 * 缓存管理工具类
 */
public class C_CacheUtil {

    public static long getWholeCacheSizeLong() {
        return C_FileUtil.getFolderSize(new File(Common.ROOT_PATH));
    }

    public static long getHttpCacheSizeLong() {
        return C_FileUtil.getFolderSize(new File(Common.net.HTTP_CATCH_PATH));
    }

    public static String getCacheSize() {
        return C_FileUtil.formatSize(C_FileUtil.getFolderSize(new File(Common.ROOT_PATH)));
    }

    public static void setCacheSize(final TextView tv) {
        if (tv != null) {
            tv.post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(getCacheSize());
                }
            });
        }
    }

    public static void clearCache(Context context, C_FileUtil.FileDeleteListener listener) {
        C_ImageLoaderManager.clearCache(context);
        C_FileUtil.deleteAllFiles(Common.ROOT_PATH, listener);
    }
}
