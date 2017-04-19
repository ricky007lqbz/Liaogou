package com.common.base.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.common.R;
import com.common.app.C_AppManager;
import com.common.base.listener.C_LifeCycleListener;
import com.common.base.listener.C_OnSoftInputChangeListener;
import com.common.net.C_HttpRequest;
import com.common.util.C_AndroidAdjustResizeBugFix;
import com.common.util.C_DisplayUtil;
import com.common.util.C_L;
import com.common.util.C_ResUtil;
import com.common.util.C_SystemUIUtil;
import com.common.util.C_ViewSetUtils;
import com.common.widget.C_TopBanner;
import com.common.widget.C_TranslucentNavigationBar;
import com.common.widget.C_TranslucentStatusBar;

import java.util.Map;

/**
 * Created by ricky on 2016/05/16.
 * <p/>
 * 自定义的基类Activity
 * <p/>
 * UI 基础通用结构
 * <p/>
 * 逻辑的通用结构 （处理推送/有推送的页面都创建新的activity）
 * <p/>
 * <p/>
 * 沉浸式处理 ：
 * <p/>
 * 有状态栏沉浸式，在头部控件高度增加状态栏高度即可；
 * <p/>
 * 有导航栏沉浸式可分不同机型， 有物理键的隐藏系统导航实现全屏 ； 没有物理键的保留系统导航不实现全屏/底部沉浸式；
 */
public abstract class C_BaseAppCompatActivity extends AppCompatActivity {

    // 一旦退出后台将标记，onResume的时候知道该生命周期是否由回到前台触发
    protected boolean isFromForeground = false;
    /*最底层的rootView*/
    protected View rootView;

    /* 上层虚拟沉浸式*/
    private C_TranslucentStatusBar translucentStatusBar;
    /*topBanner*/
    protected C_TopBanner topBanner;
    /* 中间层*/
    protected LinearLayout layoutContainer;
    /*顶层*/
    protected LinearLayout layoutTop;
    /*底层*/
    protected LinearLayout layoutBottom;
    /*背景覆盖的黑层图片*/
    protected ImageView ivCover;
    /*右下角图片按钮*/
    protected ImageView ivRightBottom;
    /* 下层虚拟沉浸式*/
    private C_TranslucentNavigationBar translucentNavigationBar;
    /*在Theme中有默认配置沉浸式， 在java代码中可以动态配置;*/
    public boolean isSetTranslucentStatusBar;
    /* 是否设置有底部导航沉浸式*/
    public boolean isSetTranslucentNavigationBar;

    protected C_LifeCycleListener lifeCycleListener;

    protected C_LifeCycleListener getLifeCycleListener() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.lifeCycleListener = getLifeCycleListener();
        super.onCreate(savedInstanceState);
        if (lifeCycleListener != null) {
            lifeCycleListener.onCreate(this, true, savedInstanceState);
        }
        C_L.d("activity", "[onCreate]" + this);
        // 添加Activity到堆栈
        C_AppManager.getInstance().addActivity(this);
        // 修改状态栏颜色，4.4+生效
        C_SystemUIUtil.setTranslucentStatusColor(this, android.R.color.transparent);
        setWindowFullScreen();
        setContentView(R.layout.c_activity_base);
        rootView = findViewById(R.id.base_root_view);
        topBanner = (C_TopBanner) findViewById(R.id.top_banner);
        layoutContainer = (LinearLayout) findViewById(R.id.container);
        layoutTop = (LinearLayout) findViewById(R.id.top);
        layoutBottom = (LinearLayout) findViewById(R.id.bottom);
        ivCover = (ImageView) findViewById(R.id.iv_cove);
        ivRightBottom = (ImageView) findViewById(R.id.iv_btn_right_bottom);
        /**
         * Tips : 这里有两个错误！
         * 1. 这里的 inflate 和 delegateView 中的inflate 重复; 这样做了两次解析来创建View;
         *
         * 2. 就太坑了，两个解析来创建View,创建的是不同的两个View对象吧！
         */
//        inflaterContainer(layoutContainer);
//        if (getContainerLayoutId() > 0)
//            layoutContainer.addView(View.inflate(this, getContainerLayoutId(), null));

        /** 这里直接将layout 添加到 layoutContainer 咯!? UI不可见 。。。*/
//        View.inflate(this,getContainerLayoutId(),layoutContainer);

        translucentNavigationBar = (C_TranslucentNavigationBar) findViewById(R.id.translucent_navigation_bar);
        translucentStatusBar = (C_TranslucentStatusBar) findViewById(R.id.translucent_status_bar);
        setStatusBar();
        configTranslucent();

        //软键盘监听
        C_AndroidAdjustResizeBugFix.assistActivity(this);
    }

    /**
     * 设置状态栏
     */
    public void setStatusBar() {
        /** 没有以下代码,一片白哦*/
        if (C_DisplayUtil.isTranslucentStatus(this)) {
            setTranslucentStatusBarHeight();
        }
        if (C_DisplayUtil.isTranslucentNavigation(this)) {
            setTranslucentNavigationBarHeight();
        }
        initNavigationBarHeight();
    }

    public void setWindowFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    /**
     * 清除状态栏
     */
    public void clearStatusBar() {
        if (translucentStatusBar != null) {
            translucentStatusBar.getLayoutParams().height = 0;
            translucentStatusBar.postInvalidate();
        }
    }

    protected void setRootBackgroundColor(int color) {
        if (rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }

    protected void setStatusBarColor(int color) {
        if (translucentStatusBar != null) {
            translucentStatusBar.setBackgroundColor(color);
        }
    }

    /**
     * 配置沉浸式。当然app Theme 默认配置，这里就不必要了。
     */
    protected void configTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            C_SystemUIUtil.setTranslucentStatus(this, true);
            C_SystemUIUtil.setTranslucentNavigation(this, true);
        }
    }

    /**
     * 指定中间UI层
     *
     * @param container
     */
//    public abstract void inflaterContainer(LinearLayout container);

    /**
     * 虚拟状态栏高度(顶部有沉浸式时调用）
     */
    public void setTranslucentStatusBarHeight() {
        //不通过虚拟状态栏设置沉浸式
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) translucentStatusBar.getLayoutParams();
        rl.height = 0;
        translucentStatusBar.setLayoutParams(rl);
        translucentStatusBar.postInvalidate();
    }

    /**
     * 虚拟导航栏的高度 （底部有沉浸式时配置）
     */
    public void setTranslucentNavigationBarHeight() {
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) translucentNavigationBar.getLayoutParams();
        /**
         * HTC 没有物理键 ;  隐藏 Navigation Bar 导致不能返回，不能 Home（ 当然，程序可以设置关闭app，退出程序）
         *
         * TranslucentNavigationBar 占位顶上去
         */
        if (C_DisplayUtil.isHTC()) {
            C_SystemUIUtil.setSystemUiVisibility(this, false);
        } else {
            C_SystemUIUtil.setSystemUiVisibility(this, true);
        }
        /**底部导航高度： 有沉浸式又有导航栏时 ，translucentNavigationBar 填补系统NavigationBar 高度*/
        if (C_DisplayUtil.isMeizu()) {
            rl.height = C_DisplayUtil.getSmartBarHeight(this);
        } else {
            rl.height = C_DisplayUtil.getNavigationBarHeight(this);
        }
        translucentNavigationBar.setLayoutParams(rl);
        translucentNavigationBar.postInvalidate();
    }

    protected int navigationBarHeight;

    private void initNavigationBarHeight() {
        /**底部导航高度： 有沉浸式又有导航栏时 ，translucentNavigationBar 填补系统NavigationBar 高度*/
        if (C_DisplayUtil.isMeizu()) {
            navigationBarHeight = C_DisplayUtil.getSmartBarHeight(this);
        } else {
            navigationBarHeight = C_DisplayUtil.getNavigationBarHeight(this);
        }
    }

    public int getNavigationBarHeight() {
        return navigationBarHeight;
    }

    @Override
    protected void onStart() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onStart(this, true, isFromForeground, getPageName());
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onResume(this, true, isFromForeground, getPageName());
        }
        super.onResume();
        C_L.i("activity", "[Activity::onResume] this = " + this);

    }

    @Override
    protected void onPause() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onPause(this, true, isFromForeground, getPageName());
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onStop(this, true, isFromForeground, getPageName());
        }
        super.onStop();
        //清空所有请求队列
        C_HttpRequest.getInstance().clearAllRequestQueue();
        if (C_AppManager.getInstance().isAppOnForeground(this)) {
            //app 进入后台
            //全局变量isActive = false 记录当前已经进入后台
            // 为什么不用Activity的呢，因为fragment的生命周期比Activity慢
            isFromForeground = false;
        }
    }

    @Override
    protected void onDestroy() {
        removeOnGlobalLayoutListener();
//        super.onDestroy();
        if (lifeCycleListener != null) {
            lifeCycleListener.onDestroy(this, true, isFromForeground, getPageName());
        }
        super.onDestroy();
        // 结束Activity从堆栈中移除
        C_AppManager.getInstance().finishActivity(this);
    }

    /**
     * 用来给map put值的方法
     *
     * @param params map
     * @param key    键
     * @param value  值
     */
    public void putParams(Map<String, String> params, String key, String value) {
        if (params == null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }
        params.put(key, value);
    }

    public C_TopBanner getTopBanner() {
        return topBanner;
    }

    public void setTopBanner(C_TopBanner topBanner) {
        this.topBanner = topBanner;
    }

    /**
     * 设置背景覆盖层资源
     */
    public void setIvCover(int resId) {
        if (ivCover != null) {
            ivCover.setVisibility(View.VISIBLE);
            ivCover.setImageResource(resId);
        }
    }

    public void showIvCover(boolean visible) {
        if (ivCover != null) {
            ivCover.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置背景覆盖层资源
     */
    public void setIvRightBottom(int resId,
                                 int widthDimens,
                                 int heightDimens,
                                 int marginRightDimens,
                                 int marginBottomDimens,
                                 View.OnClickListener listener) {
        if (ivRightBottom != null) {
            ivRightBottom.setVisibility(View.VISIBLE);
            ivRightBottom.setImageResource(resId);
            if (widthDimens > 0 && heightDimens > 0) {
                ivRightBottom.getLayoutParams().width = C_ResUtil.getDimens(widthDimens);
                ivRightBottom.getLayoutParams().height = C_ResUtil.getDimens(heightDimens);
            }
            C_ViewSetUtils.setMarginRight(ivRightBottom, C_ResUtil.getDimens(marginRightDimens));
            C_ViewSetUtils.setMarginBottom(ivRightBottom, C_ResUtil.getDimens(marginBottomDimens));
            ivRightBottom.setOnClickListener(listener);
        }
    }

    public void showIvRightBottom(boolean visible) {
        if (ivRightBottom != null) {
            if (visible) {
                ivRightBottom.setVisibility(View.VISIBLE);
            } else {
                ivRightBottom.setVisibility(View.GONE);
            }
        }
    }

    // 添加Fragment（View层）
    protected void replaceFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }

    // 添加Fragment（View层）
    protected void addFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }

    // 显示Fragment（View层）
    protected void showFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }

    // 隐藏Fragment（View层）
    protected void hideFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .hide(fragment).commitAllowingStateLoss();
    }

    protected CharSequence getPageName() {
        return null;
    }

    private int rootBottom = Integer.MIN_VALUE;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private boolean isSoftInputShow;

    /**
     * 添加布局改变监听（主要用于软键盘弹出）
     */
    public void addOnSoftInputChangeListener(final C_OnSoftInputChangeListener changeListener) {
        if (rootView != null) {
            mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    rootView.getGlobalVisibleRect(r);
                    // 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
                    if (rootBottom == Integer.MIN_VALUE) {
                        rootBottom = r.bottom;
                        return;
                    }
                    // adjustResize，软键盘弹出后高度会变小
                    if (r.bottom < rootBottom) {
                        if (!isSoftInputShow) {
                            isSoftInputShow = true;
                            if (changeListener != null) {
                                changeListener.onSoftInputOpen();
                            }
                        }
                    } else {
                        if (isSoftInputShow) {
                            isSoftInputShow = false;
                            if (changeListener != null) {
                                changeListener.onSoftInputClose();
                            }
                        }
                    }
                }
            };
            removeOnGlobalLayoutListener();
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private void removeOnGlobalLayoutListener() {
        if (rootView != null) {
            if (mOnGlobalLayoutListener != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
                } else {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
                }
            }
        }
    }

    //==================6.0运行时获取权限==========================//

    private onActivityResultListener activityResultListener;

    public void setActivityResultListener(onActivityResultListener activityResultListener) {
        this.activityResultListener = activityResultListener;
    }

    public boolean onPermissionsCheck(String permission) {
        boolean has_permission = true;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                has_permission = false;
            }
        }
        return has_permission;
    }


    public boolean onPermissionsCheckAndRequest(String[] permissions, int requestCode) {
        boolean has_permission = onPermissionsCheck(permissions[0]);
        if (!has_permission) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
        return has_permission;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultListener != null) {
            activityResultListener.onRequestPermissionsResult(requestCode, resultCode, data);
        }
    }

    public interface onActivityResultListener {

        void onRequestPermissionsResult(int requestCode, int resultCode, Intent data);
    }
}

