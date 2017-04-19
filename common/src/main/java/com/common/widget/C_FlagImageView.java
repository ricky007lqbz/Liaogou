package com.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.common.R;
import com.common.util.C_ResUtil;


/**
 * Created by Ziwu on 2016/6/6.
 * <p>
 * 带图标的ImageView
 */
public class C_FlagImageView extends ImageView {
    public static final int FLAG_LOCATION_LEFT_TOP = 0;
    public static final int FLAG_LOCATION_LEFT_BOTTOM = 1;
    public static final int FLAG_LOCATION_RIGHT_TOP = 2;
    public static final int FLAG_LOCATION_RIGHT_BOTTOM = 3;
    public static final int FLAG_LOCATION_CENTER = 4;
    private Bitmap bitmap;
    private int location;
    private boolean show;

    public C_FlagImageView(Context context) {
        super(context);
    }

    public C_FlagImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setResId(int resId) {
        this.bitmap = BitmapFactory.decodeResource(this.getResources(), resId);
        this.invalidate();
    }

    public void setDrawable(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        this.bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(this.bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        this.invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.invalidate();
    }

    public void setLocation(int location) {
        this.location = location;
        this.invalidate();
    }

    public void showFlag() {
        this.show = true;
        this.invalidate();
    }

    public void hiddenFlag() {
        this.show = false;
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.show && this.bitmap != null) {
            int iw = this.getMeasuredWidth();
            int ih = this.getMeasuredHeight();
            int bw = this.bitmap.getWidth();
            int bh = this.bitmap.getHeight();
            switch (this.location) {
                case FLAG_LOCATION_LEFT_TOP:
                    canvas.drawBitmap(this.bitmap, 0.0F, 0.0F, (Paint) null);
                    break;
                case FLAG_LOCATION_LEFT_BOTTOM:
                    canvas.drawBitmap(this.bitmap, 0.0F, (float) (ih - bh), (Paint) null);
                    break;
                case FLAG_LOCATION_RIGHT_TOP:
                    canvas.drawBitmap(this.bitmap, (float) (iw - bw), C_ResUtil.getDimens(R.dimen._15dp), (Paint) null);
                    break;
                case FLAG_LOCATION_RIGHT_BOTTOM:
                    canvas.drawBitmap(this.bitmap, (float) (iw - bw - _4dp), (float) (ih - bh - _4dp), (Paint) null);
                    break;
                case FLAG_LOCATION_CENTER:
                    canvas.drawBitmap(this.bitmap, (float) (iw - bw) / 2.0F, (float) (ih - bh) / 2.0F, (Paint) null);
            }
        }
    }

    private static int _4dp = C_ResUtil.getDimens(R.dimen._4dp);
}
