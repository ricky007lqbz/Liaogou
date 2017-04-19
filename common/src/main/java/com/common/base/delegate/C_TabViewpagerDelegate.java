package com.common.base.delegate;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.common.R;
import com.common.base.adapter.C_FragmentAdapter;
import com.common.base.fragment.C_BaseFragment;
import com.common.util.C_ArrayUtil;
import com.common.widget.C_ProgressLayout;
import com.common.widget.C_TabIndicator;


/**
 * Created by ricky on 2016/08/22.
 * <p/>
 * 顶部有tab,底部装载viewpager的delegate
 */
public class C_TabViewpagerDelegate extends BaseViewDelegate {

    C_TabIndicator tabIndicator;
    View tabDivider;
    ViewPager viewpager;
    LinearLayout llTopOut;
    LinearLayout llBottomOut;
    C_FragmentAdapter adapter;

    C_ProgressLayout progressLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_tab_viewpager;
    }

    @Override
    public void initWidget() {
        tabIndicator = (C_TabIndicator) findViewById(R.id.tab_indicator);
        tabDivider = findViewById(R.id.tab_divider);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        llTopOut = (LinearLayout) findViewById(R.id.ll_top_out);
        llBottomOut = (LinearLayout) findViewById(R.id.ll_bottom_out);
        progressLayout  = (C_ProgressLayout) findViewById(R.id.progress_layout);
    }

    public void initTabAndViewpager(Bundle[] bundles, FragmentManager fm, String[] classes, final String[] titles) {
        if (C_ArrayUtil.isEmpty(classes) || C_ArrayUtil.isEmpty(titles)) {
            return;
        }
        adapter = new C_FragmentAdapter(getContext(), bundles, fm, classes, titles);
        viewpager.setAdapter(adapter);
        tabIndicator.setViewPager(viewpager).setTabs(titles);
    }

    /**
     * 设置viewPager显示的位置
     */
    public void setCurrentItem(int position) {
        if (viewpager != null && adapter != null) {
            if (position >= 0 && position < adapter.getCount()) {
                viewpager.setCurrentItem(position);
            }
        }
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
     * 设置红点
     */
    public void setTabIndicatorRedPoint(boolean isShow, int... positions) {
        if (tabIndicator != null) {
            for (int position : positions) {
                tabIndicator.setRedPointMark(position, isShow);
            }
        }
    }

    /**
     * 设置红点(带数字）
     */
    public void setTabIndicatorRedCount(boolean isShow, int position, int count) {
        if (tabIndicator != null) {
            tabIndicator.setRedCountMark(position, count, isShow);
        }
    }

    /**
     * 是否显示tabIndicator
     */
    public void showTabIndicator(boolean visible) {
        if (tabIndicator != null && tabDivider != null) {
            tabIndicator.setVisibility(visible ? View.VISIBLE : View.GONE);
//            tabDivider.setVisibility(visible ? View.VISIBLE : View.GONE);
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
    public C_BaseFragment getCurrentItem() {
        if (adapter != null && viewpager != null) {
            return adapter.getItem(viewpager.getCurrentItem());
        }
        throw new IllegalStateException("the adapter of C_TabViewpagerDelegate is not init");
    }

    /**
     * 根据位置获取对应的fragment item
     */
    public C_BaseFragment getFragmentItem(int position) {
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

    public C_TabIndicator getTabIndicator() {
        return tabIndicator;
    }

    public void setTabIndicator(C_TabIndicator tabIndicator) {
        this.tabIndicator = tabIndicator;
    }

    public C_ProgressLayout getProgressLayout() {
        return progressLayout;
    }

    public void setProgressLayout(C_ProgressLayout progressLayout) {
        this.progressLayout = progressLayout;
    }
}
