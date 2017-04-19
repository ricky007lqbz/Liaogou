package com.common.base.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ricky on 2016/09/19.
 *
 * 移动或删除 recycleView item项的监听器
 */
public interface C_ItemMoveOrDeleteListener {

    void onItemMove(RecyclerView.ViewHolder holder, int from, int to);

    void onItemDismiss(RecyclerView.ViewHolder holder, int position);
}
