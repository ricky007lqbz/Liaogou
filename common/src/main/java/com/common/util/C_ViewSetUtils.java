package com.common.util;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * Created by ricky on 2016/07/18.
 * <p/>
 * 设置视图控件的工具类
 */
public class C_ViewSetUtils {

    /**
     * 设置margin值
     */
    public static void setMargins(View v, int left, int top, int right, int bottom) {
        if (v == null) {
            return;
        }
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        try {
            Class[] clazz = new Class[4];
            for (int i = 0; i < 4; i++) {
                clazz[i] = int.class;
            }
            Method m = lp.getClass().getMethod("setMargins", clazz);
            m.invoke(lp, left, top, right, bottom);
            //默认设置marginStart和marginRight
            setMarginStart(v, left);
            setMarginEnd(v, right);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置marginTop值
     */
    public static void setMarginTop(View v, int marginTop) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        setMargins(v, params.leftMargin, marginTop, params.rightMargin, params.bottomMargin);
    }

    /**
     * 设置marginBottom值
     */
    public static void setMarginBottom(View v, int marginBottom) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        setMargins(v, params.leftMargin, params.topMargin, params.rightMargin, marginBottom);
    }

    /**
     * 设置marginLeft值
     */
    public static void setMarginLeft(View v, int marginLeft) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        setMargins(v, marginLeft, params.topMargin, params.rightMargin, params.bottomMargin);
    }

    /**
     * 设置marginRight值
     */
    public static void setMarginRight(View v, int marginRight) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        setMargins(v, params.leftMargin, params.topMargin, marginRight, params.bottomMargin);
    }

    /**
     * 设置margin值
     */
    public static void setMarginEnd(View v, int marginEnd) {
        //版本小于16不执行该方法
        if (v == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        try {
            Class clazz = int.class;
            Method m = lp.getClass().getMethod("setMarginEnd", clazz);
            m.invoke(lp, marginEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置margin值
     */
    public static void setMarginStart(View v, int marginStart) {
        //版本小于16不执行该方法
        if (v == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        try {
            Class clazz = int.class;
            Method m = lp.getClass().getMethod("setMarginStart", clazz);
            m.invoke(lp, marginStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
