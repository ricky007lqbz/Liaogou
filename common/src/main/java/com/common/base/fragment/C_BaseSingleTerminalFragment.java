package com.common.base.fragment;

import com.common.base.delegate.C_BaseSingleTerminalDelegate;
import com.common.base.delegate.BaseViewDelegate;
import com.common.base.listener.C_RefreshListener;
import com.common.widget.C_TopBanner;

/**
 * Created by ricky on 2016/08/24.
 * <p/>
 * 单个列表的终端基类 fragment
 */
public abstract class C_BaseSingleTerminalFragment extends C_BaseSingleFragment<C_BaseSingleTerminalDelegate>
        implements C_RefreshListener, C_BaseSingleTerminalDelegate.IView, C_TopBanner.IView {

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
        //注册delegate的topBanner接口
        mDelegateView.registerTopBanner(this);

        fragment = getFragment();
        if (fragment != null) {
            mDelegateView.replaceFragment(getChildFragmentManager(), fragment);
            //注册刷新监听
            fragment.setRefreshListener(this);
        }

        //设置默认的标题
        mDelegateView.setOrigTitle(getOrigTitle());
        //设置改变后的标题
        mDelegateView.setChangeTitle(getOrigTitle());
        //设置右上角默认图片
        mDelegateView.setIvTopRightRes(getTopRightRes());
        //隐藏返回按钮
        mDelegateView.hideTopIvBack();
    }

    protected abstract BaseViewDelegate getTopDelegate();

    protected abstract C_BaseListFragment getFragment();

    protected abstract String getOrigTitle();

    protected abstract int getTopRightRes();

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

    @Override
    public void onTvCenterPress() {

    }

    @Override
    public void onIvLeftPress() {

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
