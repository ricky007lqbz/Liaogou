package com.common.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.common.Common;
import com.common.R;
import com.common.util.C_DevUtil;
import com.common.util.C_NetworkUtil;
import com.common.util.C_ResUtil;
import com.common.util.C_StringUtil;
import com.common.util.glide.transformation.C_GlideCircleTransform;
import com.common.util.glide.transformation.C_GlideRoundTransform;

/**
 * Created by ricky on 2016/05/17.
 * <p/>
 * 加载图片的工具类
 */
public class C_ImageLoader {

    private static final String SUFFIX_GIF = ".gif";

    private static final int default_size = 200;

    /**
     * 设置圆形图片
     */
    public static void setCircleImageByUrl(Context context, final ImageView iv, String url) {
        setCircleImageByUrl(context, iv, url, Common.image.DF_CIRCLE_IMG);
    }

    /**
     * 设置圆形图片
     */
    public static void setCircleImageByUrl(Context context, final ImageView iv, Uri uri, int defResId) {
        setCircleImageByUri(context, iv, uri, defResId);
    }

    //一种设置加载圆形图片的方法，暂时注释，用第二种方法加载
    public static void setCircleImageByUrl(final Context context, ImageView iv, String url, int defResId) {
        if (iv == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop() //自适应imageView
                .placeholder(defResId)
                .error(defResId)
                .transform(new C_GlideCircleTransform(context))
                .into(iv);
    }


    /**
     * 设置圆形图片
     */
    public static void setCircleImageByUri(final Context context, final ImageView iv, Uri uri, int defResId) {
        if (iv == null) {
            return;
        }
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .centerCrop() //自适应imageView
                .placeholder(defResId)
                .error(defResId)
                .transform(new C_GlideCircleTransform(context))
                .into(iv);
    }

    /**
     * 设置圆角图片
     */
    public static void setRoundImageByUrl(final Context context,
                                          final ImageView iv,
                                          String url,
                                          int defResId,
                                          int width,
                                          int height,
                                          int radiusDp) {
        if (iv == null || TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .fitCenter()
                .placeholder(defResId)
                .error(defResId)
                .override(width, height)
                .transform(new C_GlideRoundTransform(context, radiusDp))
                .into(iv);
    }

    /**
     * 设置圆角图片
     */
    public static void setRoundImageByUrl(final Context context,
                                          final ImageView iv,
                                          String url,
                                          int defResId,
                                          int radiusDp) {
        if (iv == null || TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .fitCenter()
                .placeholder(defResId)
                .error(defResId)
                .transform(new C_GlideRoundTransform(context, radiusDp))
                .into(iv);
    }

    /**
     * 设置圆角图片
     */
    public static void setRoundImageByUri(final Context context,
                                          final ImageView iv,
                                          Uri uri,
                                          int defResId,
                                          int radius) {
        if (iv == null) {
            return;
        }
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .placeholder(defResId)
                .error(defResId)
                .transform(new C_GlideRoundTransform(context, radius))
                .into(iv);
    }

    /**
     * 设置圆角图片
     */
    public static void setRoundImageByUrl(Context context, ImageView iv, String url, int defResId) {
        setRoundImageByUrl(context, iv, url, defResId, 2); //如果不设置圆角，则默认为2dp
    }

    public static void setRoundImageByUri(Context context, ImageView iv, Uri uri, int defResId) {
        setRoundImageByUri(context, iv, uri, defResId, 2); //如果不设置圆角，则默认为2dp
    }

    public static void setImageByUrl(Context context, ImageView iv, String url, int w, int h) {
        if (iv == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (w == 0) {
            w = default_size;
        }
        if (h == 0) {
            h = default_size;
        }
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(Common.image.DF_IMG)
                .error(Common.image.DF_IMG)
                .override(w, h)
                .crossFade()
                .into(iv);
    }


    public static void setImageByUrlFitCenter(Context context, ImageView iv, String url) {
        setImageByUrlFitCenter(context, iv, url, Common.image.DF_IMG);
    }

    public static void setImageByUrlFitCenter(Context context, ImageView iv, String url, int defResId) {
        if (iv == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defResId)
                .error(defResId)
                .crossFade()
                .into(iv);
    }


    public static void setImageByUriCenterCrop(Context context, ImageView iv, Uri uri, int defResId) {
        if (iv == null) {
            return;
        }
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .placeholder(defResId)
                .error(defResId)
                .crossFade()
                .into(iv);
    }

    public static void setImageByUriCenterCrop(Context context, ImageView iv, Uri uri) {
        setImageByUriCenterCrop(context, iv, uri, Common.image.DF_IMG);
    }

    public static void setImageByUrlCenterCrop(Context context, ImageView iv, String url) {
        setImageByUrlCenterCrop(context, iv, url, Common.image.DF_IMG);
    }

    public static void setImageByUrlCenterCrop(Context context, ImageView iv, String url, int defResId) {
        if (iv == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defResId)
                .error(defResId)
                .crossFade()
                .into(iv);
    }

    /**
     * 默认以屏幕尺寸为最大宽度加载图片
     */
    public static void setImageByUrlFitScreenWidth(Context context, ImageView iv, String url) {
        setImageByUrlFitWidth(context, iv, url, Common.image.DF_IMG, C_DevUtil.getScreenWidth(context));
    }

    public static void setImageByUrlFitWidth(Context context, ImageView iv, String url, int widthSet) {
        setImageByUrlFitWidth(context, iv, url, Common.image.DF_IMG, widthSet);
    }

    /**
     * 按明确的宽度加载图片
     */
    public static void setImageByUrlFitWidth(Context context, final ImageView iv, final String url, int defResId, final int widthSet) {
        if (iv == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .skipMemoryCache(true)
                .placeholder(defResId)
                .error(defResId)
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        int[] temp = C_StringUtil.getImageViewSizeBySize(url, widthSet);
                        int width = temp[0];
                        int height = temp[1];
                        ViewGroup.LayoutParams params = iv.getLayoutParams();
                        params.width = width;
                        params.height = height;
                        iv.setLayoutParams(params);

                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        iv.setImageBitmap(resource);
                    }
                });
    }

    /**
     * 不同网络情况加载GIF / 或GIF首帧。
     *
     * @param context
     * @param iv
     * @param url
     * @param defResId
     * @param width
     * @param isDependsWifi 是否必须在wifi加载
     */
    public static void setGifByUrlFitWidth(Context context, final ImageView iv, String url, int defResId, final int width, final int height, boolean isDependsWifi) {
        /**只在wifi下加载*/
        if (isDependsWifi) {
            /**有wifi直接加载gif*/
            if (C_NetworkUtil.isWifiConnected()) {
                setGifByUrlFitWidth(context, iv, url, defResId, width, height);
            } else {
                /**无wifi直接加载首帧*/
                setImageByUrlFitWidth(context, iv, url, defResId, width);
            }
        } else {
            /**不考虑网络类型*/
            setGifByUrlFitWidth(context, iv, url, defResId, width, height);
        }
    }

    /**
     * @param context
     * @param iv
     * @param url
     * @param defResId
     * @param width
     * @param height
     */
    public static void setImageFitWH(Context context, final ImageView iv, String url, int defResId, final int width, final int height) {
        if (iv == null)
            return;
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        if (url.contains(SUFFIX_GIF)) {
            Glide.with(context)
                    .load(url)
                    .asGif()
                    .fitCenter()
                    .placeholder(defResId)
                    .error(defResId)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv);
        } else {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .fitCenter()
                    .skipMemoryCache(true)
                    .placeholder(defResId)
                    .error(defResId)
                    .into(iv);
        }
    }

    /**
     * 明确按宽度加载GIF
     */
    public static void setGifByUrlFitWidth(Context context, final ImageView iv, String url, int defResId, final int width, final int height) {
        if (iv == null)
            return;
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        if (url.contains(SUFFIX_GIF)) {
            DiskCacheStrategy strategy;
            if (width < C_ResUtil.getDimens(R.dimen._200dp)) {
                strategy = DiskCacheStrategy.RESULT;
            } else {
                strategy = DiskCacheStrategy.NONE;
            }
            Glide.with(context)
                    .load(url)
                    .asGif()
                    .fitCenter()
                    .placeholder(defResId)
                    .error(defResId)
                    .skipMemoryCache(true)
                    .override(width, height)
                    .diskCacheStrategy(strategy)
                    .into(iv);
        } else {
            setImageByUrlFitWidth(context, iv, url, defResId, width);
        }
    }

    /**
     * 明确按宽度加载GIF
     */
    public static void setGifByUriFitWidth(Context context, final ImageView iv, Uri uri) {
        if (iv == null || uri == null)
            return;
        Glide.with(context)
                .load(uri)
                .asGif()
                .placeholder(Common.image.DF_IMG)
                .error(Common.image.DF_IMG)
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(iv);
    }

    /**
     * 明确固定高度宽度 焦点图
     *
     * @param context
     * @param iv
     * @param url
     * @param defResId
     * @param widthSet
     */
    public static void setImageByUrlOverWH(Context context, final ImageView iv, String url, int defResId, final int widthSet) {
        if (iv == null)
            return;
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .fitCenter()
                .placeholder(defResId)
                .error(defResId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(iv);
    }


    /**
     * 按最大宽度加载网络图片（默认占位图）
     */
    public static void setImageByUrlFitMaxWidth(Context context, ImageView iv, String url, int maxWidthSet) {
        setImageByUrlFitMaxWidth(context, iv, url, Common.image.DF_IMG, maxWidthSet);
    }

    /**
     * 按最大宽度加载网络图片
     */
    public static void setImageByUrlFitMaxWidth(Context context, final ImageView iv, final String url, int defResId, final int maxWidthSet) {
        if (iv == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(defResId);
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(defResId)
                .error(defResId)
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        int[] temp = C_StringUtil.getImageViewSizeBySize(url, maxWidthSet);
                        int width = temp[0];
                        int height = temp[1];

                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.width = width;
                        layoutParams.height = height;
                        iv.setLayoutParams(layoutParams);
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        iv.setImageBitmap(resource);
                    }
                });
    }
}
