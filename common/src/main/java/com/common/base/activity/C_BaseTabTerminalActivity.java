package com.common.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.common.base.adapter.C_FragmentAdapter;
import com.common.base.delegate.C_BaseTabTerminalDelegate;
import com.common.base.delegate.BaseViewDelegate;
import com.common.base.fragment.C_BaseListFragment2;
import com.common.base.listener.C_RefreshListener;
import com.common.util.C_ArrayUtil;

/**
 * Created by ricky on 2016/08/22.
 * <p/>
 * 带tab（viewPager)的终端基类activity
 */
public abstract class C_BaseTabTerminalActivity<T extends C_BaseTabTerminalDelegate> extends C_BaseSingleActivity<T> implements C_RefreshListener, C_BaseTabTerminalDelegate.IView {

    protected SparseArray<C_BaseListFragment2> fragments;
    protected C_FragmentAdapter<C_BaseListFragment2> adapter;


    @Override
    protected void initData() {
        if (getTopDelegate() != null) {
            mDelegateView.setTopViewContent(getTopDelegate().getRootView());
        }
        //注册头部banner交互
        mDelegateView.registerTopBannerListener(this);
        //注册交互接口
        mDelegateView.registerViewListener(this);
        //设置默认的标题
        mDelegateView.setOrigTitle(getOrigTitle());
        //设置改变后的标题
        mDelegateView.setChangeTitle(getChangeTitle());
        //设置右上角默认图片
        mDelegateView.setTopRightRes(getTopRightRes());

        //初始化子fragment
        initFragments();
    }

    @Override
    protected boolean isShowTopBanner() {
        //无需显示自带的banner
        return false;
    }

    protected void initFragments() {
        fragments = new SparseArray<>();
        if (!C_ArrayUtil.isEmpty(getClasses())) {
            int i = 0;
            for (String fClass : getClasses()) {
                C_BaseListFragment2 fragment = (C_BaseListFragment2) Fragment.instantiate(this, fClass, C_ArrayUtil.getItem(getBundles(), i));
                //注册刷新监听
                fragment.setRefreshListener(this);
                fragments.put(i, fragment);
                i++;
            }
        }
        //实例化fragmentAdapter
        adapter = new C_FragmentAdapter<>(this, getSupportFragmentManager(), fragments, getTitles());
        mDelegateView.initTabIndicator(adapter, getTitles());
    }

    protected Bundle[] getBundles() {
        return null;
    }

    protected abstract String[] getClasses();

    protected abstract String[] getTitles();

    protected abstract BaseViewDelegate getTopDelegate();

    protected abstract String getOrigTitle();

    protected abstract String getChangeTitle();

    protected abstract int getTopRightRes();

    @Override
    public void onRefresh(int position) {
        if (adapter != null && adapter.getItem(position) != null) {
            adapter.getItem(position).refresh();
        }
    }

    @Override
    public void onRefreshStart() {
        mDelegateView.onRefreshStart();
    }

    @Override
    public void onRefreshEnd() {
        mDelegateView.onRefreshEnd();
    }
}