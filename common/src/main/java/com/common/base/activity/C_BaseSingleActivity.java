package com.common.base.activity;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.app.C_AppManager;
import com.common.base.listener.C_BaseIDelegateView;
import com.common.widget.C_TopBanner;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by ricky on 2016/05/23.
 * <p>
 * 基于左侧右滑返回的activity的presenter层的实现基类
 *
 * @param <T> view的代理类
 */
public abstract class C_BaseSingleActivity<T extends C_BaseIDelegateView> extends C_BaseSwipeBackActivity implements C_TopBanner.IView {

    protected T mDelegateView;

    protected abstract Class<T> getDelegateClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //头部banner
        if (isShowTopBanner()) {
            initTopBanner();
        } else {
            topBanner.setVisibility(View.GONE);
        }
        if (getDelegateClass() != null)
            try {
                mDelegateView = getDelegateClass().newInstance(); //  避免了类型层强制转换！
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        if (mDelegateView != null) {
            mDelegateView.onCreate(this, getLayoutInflater(), layoutContainer, savedInstanceState);
            //添加中间内容
            layoutContainer.addView(mDelegateView.getRootView());
        }
        initOtherDelegate(getLayoutInflater(), layoutContainer, savedInstanceState);
        //添加头部其他布局
        if (getOutTopView() != null) {
            layoutTop.addView(getOutTopView());
        }
        //添加底部其他布局
        if (getOutBottomView() != null) {
            layoutBottom.addView(getOutBottomView());
        }
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        initData();
        addListHeadAndFootView();
        bindListener2Event();
    }

    protected boolean isShowTopBanner() {
        return true;
    }

    protected void initTopBanner() {
        topBanner.setVisibility(View.VISIBLE);
        topBanner.registerViewListener(this);
        topBanner.getIvLeft().setVisibility(isShowTopIvLeft() ? View.VISIBLE : View.GONE);
        topBanner.setTvCenter(getTitleName());
        if (getIvRightDrawable() != null) {
            topBanner.getTvRight().setVisibility(View.GONE);
            topBanner.getIvRight().setVisibility(View.VISIBLE);
            topBanner.getIvRight().setImageDrawable(getIvRightDrawable());
        } else if (!TextUtils.isEmpty(getTvRightString())) {
            topBanner.getTvRight().setVisibility(View.VISIBLE);
            topBanner.getIvRight().setVisibility(View.GONE);
            topBanner.getTvRight().setText(getTvRightString());
            if (getTvRightTextColor() != null) {
                topBanner.getTvRight().setTextColor(getTvRightTextColor());
            }
        }
    }

    protected String getTitleName() {
        return "";
    }

    protected Drawable getIvRightDrawable() {
        return null;
    }

    protected String getTvRightString() {
        return "";
    }

    protected ColorStateList getTvRightTextColor() {
        return null;
    }

    protected void setTvRightEnable(boolean enable) {
        if (topBanner != null && topBanner.getTvRight() != null) {
            topBanner.getTvRight().setEnabled(enable);
        }
    }

    public void setTvRightString(String name) {
        if (topBanner != null) {
            topBanner.getTvRight().setVisibility(View.VISIBLE);
            topBanner.getTvRight().setText(name);
        }
    }

    protected void setTvRightListener(View.OnClickListener listener) {
        if (topBanner != null) {
            topBanner.getTvRight().setOnClickListener(listener);
        }
    }

    protected void setTvRightDrawable(Drawable left, Drawable right) {
        if (topBanner != null) {
            topBanner.getTvRight().setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
        }
    }

    protected void setIvRightEnable(boolean enable) {
        if (topBanner != null && topBanner.getIvRight() != null) {
            topBanner.getIvRight().setEnabled(enable);
        }
    }

    public void setTitleName(String name) {
        if (topBanner != null) {
            topBanner.getTvCenter().setVisibility(View.VISIBLE);
            topBanner.getTvCenter().setText(name);
        }
    }

    /**
     * 获取需要添加头部的布局
     */
    protected View getOutTopView() {
        return null;
    }

    /**
     * 获取需要添加底部的布局
     */
    protected View getOutBottomView() {
        return null;
    }

    /**
     * 初始化其他的view代理类
     */
    protected void initOtherDelegate(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

    }

    /**
     * 用以初始化添加到列表的头部view和底部view
     */
    protected void addListHeadAndFootView() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 编辑其他的view
     */


    /**
     * 绑定事件
     */
    protected void bindListener2Event() {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mDelegateView == null) {
            try {
                mDelegateView = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mDelegateView != null)
            mDelegateView.onDestroy();
        super.onDestroy();
    }

    protected boolean isShowTopIvLeft() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (C_AppManager.getInstance().isExistInStack(this)) {
            C_AppManager.getInstance().finishActivity(this);
        }
    }

    @Override
    public void onTvCenterPress() {

    }

    @Override
    public void onIvLeftPress() {
        onBackPressed();
    }

    @Override
    public void onTvLefPress() {

    }

    @Override
    public void onIvRightPress() {

    }

    @Override
    public void onTvRightPress() {

    }

    @Override
    public void onIvRightSecondPress() {

    }
}
