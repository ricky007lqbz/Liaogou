package com.common.util.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.common.Common;

import java.io.File;

/**
 * Created by ricky on 2016/06/21.
 * <p/>
 * 自定义的glide配置
 */
public class C_CustomGlideModule implements GlideModule {

    public static final int DISK_CACHE_SIZE = 1024 * 1024 * 200; //默认缓存目录大小为30M

    @Override
    public void applyOptions(final Context context, GlideBuilder glideBuilder) {
        glideBuilder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        glideBuilder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(new File(Common.image.IMAGE_CACHE_PATH), DISK_CACHE_SIZE);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }
}
