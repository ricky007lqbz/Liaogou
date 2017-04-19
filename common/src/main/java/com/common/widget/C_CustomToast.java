package com.common.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by ricky on 2016/05/02.
 * <p/>
 * 可以调控显示时间的自定义toast
 */
public class C_CustomToast {
    private WindowManager wdm;
    private View mView;
    private WindowManager.LayoutParams params;

    private boolean isAddView; //是否已经添加了view

    public C_CustomToast(View view) {

        this.mView = view;

        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.gravity = Gravity.CENTER;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        wdm = (WindowManager) view.getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void setAnimationStyle(int style) {
        if (params != null) {
            params.windowAnimations = style;
        }
    }

    public void setGravity(int gravity) {
        if (params != null) {
            params.gravity = gravity;
        }
    }

    public void show(long time) {
        if (isAddView) {
            wdm.updateViewLayout(mView, params);
        } else {
            wdm.addView(mView, params);
        }
        isAddView = true;
        mHandler.postDelayed(runnable, time);
    }

    public void cancel() {
        if (isAddView) {
            wdm.removeView(mView);
        }
        isAddView = false;
    }

    private final Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };
}
