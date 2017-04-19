package com.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Method;

/**
 * Created by Ziwu on 2016/5/20.
 * <p/>
 * 处理System UI  actionBar navigationBar statusBar 的显示隐藏状态的工具类
 */
public class C_SystemUIUtil {

    /**
     * 全屏 通用办法
     * <p/>
     * 此种办法可以应用于几乎所有版本(2.3, 4.x, 5.x), 但是在4.x上无法有隐藏动画效果
     *
     * @param activity 当前activity
     */
    public static void setFullScreen(Activity activity, boolean isFullScreen) {
        if (activity == null)
            return;
        // FullScreen
        if (isFullScreen)
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // Cancel
        else
            activity.getWindow().setFlags(~WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * * 还原 系统 UI默认显示状态
     * <p/>
     * View.SYSTEM_UI_FLAG_VISIBLE Level 14
     * 默认标记
     * <p/>
     * View.SYSTEM_UI_FLAG_LOW_PROFILE Level 14
     * 低功耗模式, 会隐藏状态栏图标, 在4.0上可以实现全屏
     * <p/>
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE Level 16
     * 保持整个View稳定, 常跟bar 悬浮, 隐藏共用, 使View不会因为SystemUI的变化而做layout
     * <p/>
     * View.SYSTEM_UI_FLAG_FULLSCREEN Level 16
     * 状态栏隐藏
     * <p/>
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN Level 16
     * 状态栏上浮于Activity
     * <p/>
     * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION Level 14
     * 隐藏导航栏,
     * =========================================================================
     * 4.0 - 4.3如果使用这个属性,将会导致在下一次touch时候自动show出status跟navigation bar,源于系统clear掉其所有的状态
     * =========================================================================
     * <p/>
     * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION Level 16
     * 导航栏上浮于Activity
     * <p/>
     * View.SYSTEM_UI_FLAG_IMMERSIVE Level 19
     * Kitkat新加入的Flag, 沉浸模式, 可以隐藏掉status跟navigation bar, 并且在第一次会弹泡提醒, 它会覆盖掉之前两个隐藏bar的标记, 并且在bar出现的位置滑动可以呼出bar
     * <p/>
     * View.SYSTEM_UI_FLAG_IMMERSIVE_STIKY Level 19
     * 与上面唯一的区别是, 呼出隐藏的bar后会自动再隐藏掉
     * <p/>
     * <p/>
     * 综合上述属性, 总结4.x以上版本全屏模式使用的最佳Flag组合(不希望悬浮可去除相关Flag, 并且用默认标记重新还原状态)
     */
    public static void setDefaultUiStatus(Activity activity) {
        if (activity == null)
            return;
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    /**
     * 4.1–4.3
     * <p/>
     * 隐藏导航会产生上面所说的问题,而FULLSCREEN已经可以将导航变成透明的小点点,达到一定的效果了
     *
     * @param activity     activity
     * @param isFullScreen boolean
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setFullScreen4d1To4d3(Activity activity, boolean isFullScreen) {
        if (activity == null)
            return;
        if (isFullScreen)//Hide
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        else//Show
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**

     * <p/>
     * 4.4+ 沉浸式
     * <p/>
     * 是否显示系统相关UI;
     *
     * View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * ActionBar 都显示不了啦！
     *
     * Tips: 最好统一  statusBar navigationBar 状态；
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setSystemUiVisibility(Activity activity, boolean isVisible) {
        if (activity == null)
            return;
        if (isVisible) {
            /*  有沉浸式, 系统UI也可见，肯能遮挡程序UI！ */
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        } else {
            /*  有沉浸式, 系统UI不可见！ HTC 虚拟键盘都不能见。*/
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    /**
     * 是否设置status bar沉浸式 （两端）
     * <p/>
     * statusBar 沉浸式
     * <p/>
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Activity activity, boolean isTranslucent) {
        if (activity != null && isTranslucent)
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * Translucent navigation bar
     * <p/>
     * navigationBar 沉浸式
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentNavigation(Activity activity, boolean isTranslucent) {
        if (activity != null && isTranslucent)
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }


    /**
     *
     * 第三方jar 支持
     *
     * 沉浸式状态栏的颜色 ; 前提是有实现沉浸式;
     *
     * @param activity
     * @param resColor
     */
    public static void setTranslucentStatusColor(Activity activity, int resColor) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resColor);//通知栏所需颜色
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
}
