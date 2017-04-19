package com.common.base.activity;

import android.os.Bundle;

import com.common.base.delegate.C_TabViewpagerDelegate;

/**
 * Created by ricky on 2016/08/31.
 * <p/>
 * 由tab indicator 和viewpager组成的fragment
 */
public abstract class C_BaseTabViewpagerActivity extends C_BaseSingleActivity<C_TabViewpagerDelegate> {

    @Override
    protected Class<C_TabViewpagerDelegate> getDelegateClass() {
        return C_TabViewpagerDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mDelegateView.initTabAndViewpager(getBundle(), getSupportFragmentManager(), getClasses(), getTitles());
        mDelegateView.showTabIndicator(isShowTabIndicator());
    }

    protected Bundle[] getBundle() {
        return null;
    }

    protected abstract String[] getTitles();

    protected abstract String[] getClasses();

    protected boolean isShowTabIndicator() {
        return true;
    }
}
