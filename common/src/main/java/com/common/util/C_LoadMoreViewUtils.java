package com.common.util;

import android.view.View;

import com.common.base.adapter.C_HeadAndFootMultiRecyclerAdapter;
import com.common.widget.C_LoadMoreView;

/**
 * Created by ricky on 2016/08/19.
 * <p>
 * 分页展示数据时，RecyclerView的LoadMoreView State 操作工具类
 * <p>
 * LoadMoreView一共有几种State：NORMAL/LOADING/Error/END
 */
public class C_LoadMoreViewUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param isForbid      是否禁用加载更多
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void initLoadMoreView(C_HeadAndFootMultiRecyclerAdapter adapter, boolean isForbid, C_LoadMoreView.State state, View.OnClickListener errorListener) {
        if (adapter == null || isForbid) {
            return;
        }
        C_LoadMoreView footerView;
        //已经有footerView了
        if (adapter.isHasLoadMoreView()) {
            footerView = adapter.getLoadMoreView();
            footerView.setState(state);
            footerView.registerErrorLisenter(errorListener);
        } else {
            footerView = adapter.onLoadMoreViewCreate();
            footerView.setState(state);
            footerView.registerErrorLisenter(errorListener);
            adapter.setLoadMoreView(footerView);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     */
    public static C_LoadMoreView.State getLoadMoreState(C_HeadAndFootMultiRecyclerAdapter adapter) {
        if (adapter != null) {
            if (adapter.isHasLoadMoreView()) {
                C_LoadMoreView footerView = adapter.getLoadMoreView();
                return footerView.getState();
            }
        }
        return C_LoadMoreView.State.NORMAL;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     */
    public static void setLoadMoreState(C_HeadAndFootMultiRecyclerAdapter adapter, C_LoadMoreView.State state) {
        if (adapter == null) {
            return;
        }
        if (adapter.isHasLoadMoreView()) {
            C_LoadMoreView footerView = adapter.getLoadMoreView();
            footerView.setState(state);
        }
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     */
    public static void setLoadMoreEndString(C_HeadAndFootMultiRecyclerAdapter adapter, String endString) {
        if (adapter == null) {
            return;
        }
        if (adapter.isHasLoadMoreView()) {
            C_LoadMoreView footerView = adapter.getLoadMoreView();
            footerView.setEndString(endString);
        }
    }
}
