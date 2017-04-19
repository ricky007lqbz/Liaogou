package com.common.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.common.bean.C_BaseBean;
import com.common.util.C_ArrayUtil;

import java.util.List;

/**
 * Created by ricky on 2016/05/27.
 * <p/>
 * 专门用来处理带RecyclerView的fragment或activity的adapter
 * 可以直接传入holderHelpers来控制每个item的显示
 */
public class C_MultiDragSortAdapter<T extends C_BaseBean> extends C_BaseDragSortAdapter<T> {

    protected SparseArray<C_BaseHHelper<T, C_BaseDragSortHolder>> mHelpers;

    public C_MultiDragSortAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public C_MultiDragSortAdapter(RecyclerView recyclerView, List<T> data) {
        super(recyclerView, data);
    }

    public C_MultiDragSortAdapter(RecyclerView recyclerView, List<T> data, boolean isCanSipe) {
        super(recyclerView, data, isCanSipe);
    }

    public void setHolderHelpers(SparseArray<C_BaseHHelper<T, C_BaseDragSortHolder>> helpers) {
        this.mHelpers = helpers;
    }

    @Override
    public C_BaseDragSortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        C_BaseDragSortHolder holder = super.onCreateViewHolder(parent, viewType);
        if (mHelpers != null && mHelpers.get(viewType) != null) {
            mHelpers.get(viewType).initHolder(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(C_BaseDragSortHolder holder, int position) {
        if (holder == null) {
            return;
        }
        T item = C_ArrayUtil.getItem(mData, position);
        if (item != null && mHelpers != null && mHelpers.get(item.get_view_type()) != null) {
            mHelpers.get(item.get_view_type()).refreshData(holder, item);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void convert(int position, C_BaseDragSortHolder holder, T item) {
        if (mHelpers != null && item != null && mHelpers.get(item.get_view_type()) != null) {
            mHelpers.get(item.get_view_type()).convert(position, holder, item);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return mHelpers.get(viewType).getLayoutId();
    }

    @Override
    public int getItemViewType(int position, T item) {
        return item.get_view_type();
    }

    @Override
    public C_OnItemClickListener getOnItemClickListener() {
        return null;
    }
}
