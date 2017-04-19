package com.common.util.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.common.Common;
import com.common.util.C_L;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * Created by ricky on 2016/07/04.
 * <p>
 * bitmap处理工具类
 */
public class C_BitmapUtils {
    /**
     * 将bitmap文件转换成字节数组.
     *
     * @param bm     需要转换的bitmap文件
     * @param format 转换后的文件格式:分别有Bitmap.CompressFormat.PNG、Bitmap.CompressFormat.
     *               JPEG、Bitmap.CompressFormat.WEBP
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat format) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(format, 90, bao);
        return bao.toByteArray();
    }

    /**
     * 压缩图片:将原始图片压缩至指定的width宽度,其高度自动等比压缩.
     * 注:若原始图片的宽度小于width参数,则不对其进行压缩,直接返回原始图片的Bitmap.
     *
     * @param filePath 原始图片文件的路径
     * @param width    压缩后的图片宽度 (目标宽度)
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressWithWidth(String filePath, int width) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        /** inJustDecodeBounds true 时bitmap是null */
        if (bitmap == null) {
            C_L.e("bitmap is null ");
        }

        int bmpWidth = options.outWidth;// 原始宽度
        int bmpHeight = options.outHeight;

        // 08-04 14:13:42.993: I/C_BitmapUtils(13499): bmpWidth 3264bmpHeight 2448

        if (bmpWidth <= 0)
            return null;

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, options); // crash 1
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        /** inJustDecodeBounds false 时才是真正取得bitmap */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            C_L.d("Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1");
        }

        float scaleWidth;
        float scaleHeight;

        if (bmpWidth > width) {
            scaleWidth = width / (float) bmpWidth;
            scaleHeight = width / (float) bmpWidth;
        } else {
            scaleWidth = 1;
            scaleHeight = 1;
        }

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
                bmpHeight, matrix, false);

        // 因为如果scaleWidth与scaleHeight都是1的话,那么就等于是原图,createBitmap返回的也是原始bitmap.
        // 所以只有新建了bitmap,才能将原始bitmap回收掉,不然调用的时候就会因为bitmap已回收而挂掉.
        if (bitmap != resizeBitmap) {
            bitmap.recycle();
        }

        return resizeBitmap;
    }

    /**
     * 缩放图片
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * @param mBitmap
     * @param context
     * @param path    指定路径
     * @return
     */
    public static String saveBitmapToSdcard(Bitmap mBitmap, Context context, String path) {

        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {// 如果SD卡不存在，直接返回
            return null;
        }

        if (path == null) {
            Calendar c = Calendar.getInstance();
            String name = "" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
                    + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MILLISECOND);
            path = Common.ROOT_PATH + "54qiumi" + name + ".png";
        }

        File dirFile = new File(Common.ROOT_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            if (mBitmap != null)
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            // 发送广播通知相册更新
            Uri localUri = Uri.fromFile(f);
            Intent localIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            context.sendBroadcast(localIntent);
            return f.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    /**
     * @param filePath  原始图片文件的路径
     * @param destWidth 压缩（放大）后的图片宽度 (目标宽度)
     * @return （猜测）滤镜要处理的bitmap 高宽像素都不能是奇数 ；否则加滤镜效果会变形 。
     */
    public static Bitmap compressFilterBitmap(String filePath, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap;
        try {
            BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        /** inJustDecodeBounds true 时bitmap是null */
        int bmpWidth = options.outWidth;// 原始宽度
        int bmpHeight = options.outHeight;
        if (bmpWidth <= 0 || bmpHeight <= 0)
            return null;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, options); // crash 1
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        /** inJustDecodeBounds false 时才是真正取得bitmap */
        float scaleWidth = 1;
        float scaleHeight = 1;
        /** 按高还是宽压缩 */
        if (((float) bmpWidth) / bmpHeight > ((float) destWidth) / destHeight) {
            if (bmpWidth > destWidth) {
                scaleWidth = ((float) destWidth) / (bmpWidth);
                scaleHeight = scaleWidth;
            }
        } else {
            if (bmpHeight > destHeight) {
                scaleHeight = ((float) destHeight) / bmpHeight;
                scaleWidth = scaleHeight;
            }
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
                bmpHeight, matrix, false);

        /**消除生成后的图片为高宽像素为奇数**/
        /** 裁剪成高宽像素为偶数 ;没必要这样处理拉，TUSDK 官方已经重现图片加滤镜扭曲的问题  ： 会在下一个版本中修复。*/
//        if (resizeBitmap.getWidth() % 2 == 1 || resizeBitmap.getHeight() % 2 == 1) {
//            if (resizeBitmap.getWidth() % 2 == 1) {
//                bmpWidth = resizeBitmap.getWidth() - 1;
//            } else {
//                bmpWidth = resizeBitmap.getWidth();
//            }
//            if (resizeBitmap.getHeight() % 2 == 1) {
//                bmpHeight = resizeBitmap.getHeight() - 1;
//            } else {
//                bmpHeight = resizeBitmap.getHeight();
//            }
//            resizeBitmap = Bitmap.createBitmap(resizeBitmap, 0, 0, bmpWidth, bmpHeight);
//        }
        /** 因为如果scaleWidth与scaleHeight都是1的话,那么就等于是原图,createBitmap返回的也是原始bitmap.
         所以只有新建了bitmap,才能将原始bitmap回收掉,不然调用的时候就会因为bitmap已回收而挂掉.*/
        if (bitmap != resizeBitmap) {
            bitmap.recycle();
        }
        return resizeBitmap;
    }

    public static Bitmap roundCrop(BitmapPool pool, Bitmap source, float radius) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    public static Bitmap roundCrop(BitmapPool pool, Bitmap source, float radius, boolean tl, boolean tr, boolean bl, boolean br) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        Path path = roundedRect(0, 0, source.getWidth(), source.getHeight(), radius, radius, tl, tr, bl, br);
        canvas.drawPath(path, paint);
        return result;
    }

    public static Path roundedRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean bl, boolean br
    ) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else {
            path.rLineTo(0, -ry);
            path.rLineTo(-rx, 0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else {
            path.rLineTo(-rx, 0);
            path.rLineTo(0, ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else {
            path.rLineTo(0, ry);
            path.rLineTo(rx, 0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else {
            path.rLineTo(rx, 0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }
}
