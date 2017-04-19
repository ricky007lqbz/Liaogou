package com.common.base.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by ricky on 2016/09/19.
 * <p/>
 * 简化的recycle View item touch 工具类的 回调类
 */
public class C_SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final C_ItemMoveOrDeleteListener mAdapter;
    private boolean isCanSwipe;

    public C_SimpleItemTouchHelperCallback(C_ItemMoveOrDeleteListener mAdapter, boolean isCanSwipe) {
        this.mAdapter = mAdapter;
        this.isCanSwipe = isCanSwipe;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = 0;
        if (isCanSwipe) {
            swipeFlags = ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder, viewHolder.getAdapterPosition());
    }
}
