package com.common.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.base.adapter.C_HeadAndFootMultiRecyclerAdapter;

/**
 * Created by ricky on 2016/08/21.
 * <p/>
 * recycleView头部view和底部view的管理工具类
 */
public class C_HeadAndFootViewUtils {

    /**
     * 添加头部view
     */
    public static void addHeadView(RecyclerView recyclerView, View view) {
        if (null == recyclerView || null == view) {
            return;
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof C_HeadAndFootMultiRecyclerAdapter) {
            C_HeadAndFootMultiRecyclerAdapter headAndFootMultiRecyclerAdapter = ((C_HeadAndFootMultiRecyclerAdapter) adapter);
            headAndFootMultiRecyclerAdapter.addHeaderView(view);
        }
    }

    /**
     * 移除头部View
     */
    public static void removeHeadView(RecyclerView recyclerView, View view) {
        if (null == recyclerView || null == view) {
            return;
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof C_HeadAndFootMultiRecyclerAdapter) {
            C_HeadAndFootMultiRecyclerAdapter headAndFootMultiRecyclerAdapter = ((C_HeadAndFootMultiRecyclerAdapter) adapter);
            headAndFootMultiRecyclerAdapter.removeHeaderView(view);
        }
    }

    /**
     * 添加底部view
     */
    public static void addFootView(RecyclerView recyclerView, View view) {
        if (null == recyclerView || null == view) {
            return;
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof C_HeadAndFootMultiRecyclerAdapter) {
            C_HeadAndFootMultiRecyclerAdapter headAndFootMultiRecyclerAdapter = ((C_HeadAndFootMultiRecyclerAdapter) adapter);
            headAndFootMultiRecyclerAdapter.addFooterView(view);
        }
    }

    /**
     * 移除底部View
     */
    public static void removeFootView(RecyclerView recyclerView, View view) {
        if (null == recyclerView || null == view) {
            return;
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof C_HeadAndFootMultiRecyclerAdapter) {
            C_HeadAndFootMultiRecyclerAdapter headAndFootMultiRecyclerAdapter = ((C_HeadAndFootMultiRecyclerAdapter) adapter);
            headAndFootMultiRecyclerAdapter.removeFooterView(view);
        }
    }
}
