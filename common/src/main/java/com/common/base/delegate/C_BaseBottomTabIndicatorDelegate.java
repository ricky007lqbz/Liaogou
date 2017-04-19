package com.common.base.delegate;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.common.R;
import com.common.base.adapter.C_FragmentAdapter;
import com.common.base.fragment.C_BaseFragment;
import com.common.widget.C_ProgressLayout;
import com.common.widget.pageindicator.C_TabPageIndicator;


/**
 * Created by Ziwu on 2016/9/20.
 *
 *
 */
public class C_BaseBottomTabIndicatorDelegate extends BaseViewDelegate implements ViewPager.OnPageChangeListener {

    C_TabPageIndicator tabIndicator;
    View tabDivider;
    ViewPager viewpager;
    LinearLayout llTopOut;
    LinearLayout llBottomOut;
    C_FragmentAdapter adapter;

    C_ProgressLayout progressLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_bottom_tab_viewpager;
    }

    @Override
    public void initWidget() {
        tabIndicator = (C_TabPageIndicator) findViewById(R.id.tab_indicator);
        tabDivider = findViewById(R.id.tab_divider);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        llTopOut = (LinearLayout) findViewById(R.id.ll_top_out);
        llBottomOut = (LinearLayout) findViewById(R.id.ll_bottom_out);
        progressLayout  = (C_ProgressLayout) findViewById(R.id.progress_layout);
    }

    public void initTabAndViewpager(Bundle[] bundles, FragmentManager fm, String[] classes, final String[] titles) {
        adapter = new C_FragmentAdapter(getContext(), bundles, fm, classes, titles);
        viewpager.setAdapter(adapter);
        tabIndicator.setDeviceWeight(true);
        tabIndicator.setViewPager(viewpager);
        tabIndicator.setOnPageChangeListener(this);
        adapter.notifyDataSetChanged();
        tabIndicator.notifyDataSetChanged();
    }

    /**
     * 添加头部视图（只能添加一个）
     */
    public void addTopView(View view) {
        if (llTopOut != null) {
            llTopOut.removeAllViews();
            llTopOut.addView(view);
        }
    }

    /**
     * 添加底部视图（只能添加一个）
     */
    public void addBottomView(View view) {
        if (llBottomOut != null) {
            llBottomOut.removeAllViews();
            llBottomOut.addView(view);
        }
    }

    /**
     * 是否显示tabIndicator
     */
    public void showTabIndicator(boolean visible) {
        if (tabIndicator != null && tabDivider != null) {
            tabIndicator.setVisibility(visible ? View.VISIBLE : View.GONE);
            tabDivider.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置对应位置的显示
     */
    public void setCurrentItem(int position, boolean smoothScroll) {
        if (viewpager != null) {
            viewpager.setCurrentItem(position, smoothScroll);
        }
    }

    /**
     * 根据位置获取对应的fragment item
     */
    public C_BaseFragment getCurrentItem(int position) {
        if (adapter != null) {
            int count = adapter.getCount();
            if (position >= 0 && position < count) {
                return adapter.getItem(position);
            }
        }
        throw new IllegalStateException("the adapter of C_TabViewpagerDelegate is not init");
    }

    /**
     * 添加左右滑动监听事件
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (viewpager != null) {
            viewpager.addOnPageChangeListener(listener);
        }
    }

    /**
     * 获取viewPager
     */
    public ViewPager getViewpager() {
        return viewpager;
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public C_ProgressLayout getProgressLayout() {
        return progressLayout;
    }

    public void setProgressLayout(C_ProgressLayout progressLayout) {
        this.progressLayout = progressLayout;
    }
}
