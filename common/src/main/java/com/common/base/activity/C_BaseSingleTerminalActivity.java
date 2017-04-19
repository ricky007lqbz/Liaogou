package com.common.base.activity;

import com.common.base.delegate.C_BaseSingleTerminalDelegate;
import com.common.base.delegate.BaseViewDelegate;
import com.common.base.fragment.C_BaseListFragment;
import com.common.base.listener.C_RefreshListener;

/**
 * Created by ricky on 2016/08/26.
 * <p>
 * 单个列表的终端基类 activity
 */
public abstract class C_BaseSingleTerminalActivity extends C_BaseSingleActivity<C_BaseSingleTerminalDelegate> implements C_RefreshListener, C_BaseSingleTerminalDelegate.IView {

    protected C_BaseListFragment fragment;

    @Override
    protected Class<C_BaseSingleTerminalDelegate> getDelegateClass() {
        return C_BaseSingleTerminalDelegate.class;
    }

    @Override
    protected void initData() {
        if (getTopDelegate() != null) {
            mDelegateView.setTopViewContent(getTopDelegate().getRootView());
        }
        //注册交互接口
        mDelegateView.registerViewListener(this);

        fragment = getFragment();
        if (fragment != null) {
            mDelegateView.replaceFragment(getSupportFragmentManager(), fragment);
            //注册刷新监听
            fragment.setRefreshListener(this);
        }
        //添加topBanner监听事件
        mDelegateView.registerTopBanner(this);
        //设置默认的标题
        mDelegateView.setOrigTitle(getOrigTitle());
        //设置改变后的标题
        mDelegateView.setChangeTitle(getOrigTitle());
        //设置右上角默认图片
        mDelegateView.setIvTopRightRes(getTopRightRes());
    }

    protected abstract BaseViewDelegate getTopDelegate();

    protected abstract C_BaseListFragment getFragment();

    protected abstract String getOrigTitle();

    protected abstract int getTopRightRes();

    @Override
    protected boolean isShowTopBanner() {
        return false;
    }

    @Override
    public void onRefresh() {
        if (fragment != null) {
            fragment.refresh();
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
