package com.common.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.listener.C_BaseIDelegateView;

/**
 * Created by ricky on 2016/08/22.
 * <p>
 * 装载的不是list的fragment
 */
public abstract class C_BaseSingleFragment<T extends C_BaseIDelegateView> extends C_BaseFragment {

    protected T mDelegateView;

    protected abstract Class<T> getDelegateClass();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (mDelegateView != null) {
            mDelegateView.onCreate(getContext(), getLayoutInflater(savedInstanceState), rootContainer, savedInstanceState);
            rootContainer.addView(mDelegateView.getRootView());
        }
        initOtherDelegate(getLayoutInflater(savedInstanceState), rootContainer, savedInstanceState);
        //添加头部其他布局
        if (getOutTopView() != null) {
            rootTop.addView(getOutTopView());
        }
        //添加中间内容
        //添加底部其他布局
        if (getOutBottomView() != null) {
            rootBottom.addView(getOutBottomView());
        }
        initData();
        addHeadAndFootView();
        bindListener2Event();
    }

    /**
     * 替换根view
     */
    public void replaceOtherRootView(View view) {
        if (rootContainer != null && view != null) {
            rootContainer.addView(view);
            mDelegateView.getRootView().setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 恢复默认的视图
     */
    public void recoverDefaultRootView() {
        if (rootContainer != null && mDelegateView != null) {
            rootContainer.removeAllViews();
            rootContainer.addView(mDelegateView.getRootView());
            mDelegateView.getRootView().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getDelegateClass() != null) {
            try {
                mDelegateView = getDelegateClass().newInstance();
            } catch (InstantiationException | java.lang.InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
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
    protected void addHeadAndFootView() {

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
    public void onDestroy() {
        super.onDestroy();
        mDelegateView.onDestroy();
    }
}