package com.common.base.activity;

import android.os.Bundle;

import com.common.base.delegate.C_BaseBottomTabIndicatorDelegate;

/**
 * Created by Ziwu on 2016/9/20.
 * <p/>
 * Indicator 在底部
 */
public abstract class C_BaseBottomTabIndicatorActivity extends C_BaseSingleActivity<C_BaseBottomTabIndicatorDelegate> {

    @Override
    protected Class<C_BaseBottomTabIndicatorDelegate> getDelegateClass() {
        return C_BaseBottomTabIndicatorDelegate.class;
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
