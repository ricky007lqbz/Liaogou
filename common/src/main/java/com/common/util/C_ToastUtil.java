package com.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.common.widget.C_CustomToast;

/**
 * Created by ricky on 2016/05/10.
 * <p/>
 * Toast的工具类
 */
public class C_ToastUtil {

    private static Toast mToast;
    private static Context mContext;

    /**
     * 初始化必须在application里运行，否则会内存溢出
     */
    public static void init(Context context) {
        mContext = context;
    }

    public static void onDestory() {
        if (mToast != null) {
            mToast = null;
        }
    }

    //=================只显示最后一个toast====================//

    public static void showSingle(int resId) {
        getSingleToast(resId).show();
    }

    public static void showSingle(int resId, long time) {
        getSingleToast(resId).show();
    }

    public static void showSingle(String text) {
        getSingleToast(text).show();
    }

    public static void showSingleLong(int resId) {
        getSingleLongToast(resId).show();
    }

    public static void showSingleLong(String text) {
        getSingleLongToast(text).show();
    }

    //=================正常显示toast====================//

    public static void show(int resId) {
        getToast(resId).show();
    }

    public static void show(String text) {
        if (!TextUtils.isEmpty(text)) {
            //默认只显示最后一个toast
            getSingleToast(text).show();
        }
    }

    public static void showLong(int resId) {
        getLongToast(resId).show();
    }

    public static void showLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            getLongToast(text).show();
        }
    }

    //====================带显示时间参数的toast=====================//

    public static void show(View view, long time) {
        C_CustomToast myToast = new C_CustomToast(view);
        myToast.show(time);
    }

    public static void show(View view, long time, int gravity, int animStyle) {
        C_CustomToast myToast = new C_CustomToast(view);
        myToast.setAnimationStyle(animStyle);
        myToast.setGravity(gravity);
        myToast.show(time);
    }


    private static Toast getSingleToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        return mToast;
    }

    private static Toast getSingleToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getSingleToast(View view) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    private static Toast getSingleLongToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, resId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(resId);
        }
        return mToast;
    }

    private static Toast getSingleLongToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    private static Toast getToast(int resId) {
        return Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
    }

    private static Toast getToast(String text) {
        return Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
    }

    private static Toast getLongToast(int resId) {
        return Toast.makeText(mContext, resId, Toast.LENGTH_LONG);
    }

    private static Toast getLongToast(String text) {
        return Toast.makeText(mContext, text, Toast.LENGTH_LONG);
    }
}
