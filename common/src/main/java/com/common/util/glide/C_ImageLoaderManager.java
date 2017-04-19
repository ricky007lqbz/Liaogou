package com.common.util.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;

/**
 * Created by ricky on 2016/06/21.
 * <p/>
 * 加载图片（Glide）相关处理的管理器
 */
public class C_ImageLoaderManager {

    /**
     * 初始化Glide
     */
    public static void init(Context context) {
        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    /**
     * 清除缓存
     */
    public static void clearCache(final Context context) {
        //清除内存缓存
        Glide.get(context).clearMemory();
        //清除磁盘缓存
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }
}
