package com.common.util;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Ziwu on 2016/5/20.
 * <p/>
 * 屏幕相关数据
 */
public class C_DisplayUtil {

    private int getScreenHight(Activity activity) {
        // 获取屏幕高
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        int screenHeight = point.y + (isMeizu() ? 0 : getNavigationBarHeight(activity));
        return screenHeight;
    }

    /**
     * 获取虚拟按键栏高度（已经判断有无navigation bar)
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }


    public static boolean isHTC() {
        return Build.BRAND.equalsIgnoreCase("HTC");
    }

    /**
     * 判断是否meizu手机
     * <p/>
     * 此时时刻，用的就是meizu测试哇， 泪流满面！！！
     *
     * @return
     */
    public static boolean isMeizu() {
        return Build.BRAND.equals("Meizu");
    }

    /**
     * 获取魅族手机底部虚拟键盘高度
     * <p/>
     * 非所有meizu 系统都是有smart Bar底部导航。
     *
     * @param context
     * @return
     */
    public static int getSmartBarHeight(Context context) {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /* status bar */

    /**
     * 这个不行的哦 ！ 是没有沉浸式window top 值才有效
     *
     * @param context
     * @return
     */
    @Deprecated
    public static int getStatusBarHeightNoTranslucent(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }


    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * @param context
     * @return window 是否有状态栏沉浸式
     */
    public static boolean isTranslucentStatus(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if ((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS & context.getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @return window 是否有底部导航沉浸式
     */
    public static boolean isTranslucentNavigation(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if ((WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION & context.getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 按屏幕宽度获取view等比高度
//     *
//     * @return 屏幕宽等比高度
//     * <p/>
//     */
//    public static int getRatioHeightByScreenWidth(float ratio) {
//    //// FIXME: 2016/8/24
//        return (int) (C.SCREEN_WIDTH * ratio);
//    }
//
//    /**
//     * 足球是 高宽比 134 /304
//     * <p/>
//     * 设计 120 /320
//     * <p/>
//     * UI测量 191 / 510
//     *
//     * @return
//     */
//    public static int getRecommnedViewHeight() {
//        return getRatioHeightByScreenWidth(134.f / 304.f);
//    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
