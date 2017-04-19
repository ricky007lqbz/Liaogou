package com.common.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ricky on 2016/09/19.
 * <p/>
 * 可以拖动 的发帖列表 holder
 */
public class C_BaseDragSortHolder<T> extends C_BaseMultiRecyclerHolder<T> {

    protected boolean isCanDrag = true;
    protected boolean isCanRemove = true;

    public C_BaseDragSortHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

    public static C_BaseDragSortHolder get(Context context, ViewGroup parent, int layoutId, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new C_BaseDragSortHolder(itemView, viewType);
    }

    public static C_BaseDragSortHolder get(View view, int viewType) {
        return new C_BaseDragSortHolder(view, viewType);
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }

    public void setCanRemove(boolean canRemove) {
        isCanRemove = canRemove;
    }

    /**
     * 是否能够拖动
     */
    public boolean isCanDrag() {
        return isCanDrag;
    }

    /**
     * 是否能够移除
     */
    public boolean isCanRemove() {
        return isCanRemove;
    }
}
