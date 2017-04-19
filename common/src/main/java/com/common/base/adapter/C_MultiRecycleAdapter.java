package com.common.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.common.R;
import com.common.bean.C_BaseBean;
import com.common.util.C_ArrayUtil;

import java.util.List;

/**
 * Created by ricky on 2016/05/27.
 * <p/>
 * 专门用来处理带RecyclerView的fragment或activity的adapter
 * 可以直接传入holderHelpers来控制每个item的显示
 */
public class C_MultiRecycleAdapter<T extends C_BaseBean> extends C_BaseMultiRecyclerAdapter<T> {
    /**
     * 当前选中项, 用作单选。
     */
    public int _checked_position;

    protected SparseArray<C_BaseHolderHelper<T>> mHelpers;

    public C_MultiRecycleAdapter(Context context) {
        super(context);
    }

    public C_MultiRecycleAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public C_BaseMultiRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        C_BaseMultiRecyclerHolder holder = super.onCreateViewHolder(parent, viewType);
        if (mHelpers != null && mHelpers.get(viewType) != null) {
            mHelpers.get(viewType).initHolder(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(C_BaseMultiRecyclerHolder holder, int position) {
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
    public void convert(int position, C_BaseMultiRecyclerHolder holder, T item) {
        if (mHelpers != null && item != null && mHelpers.get(item.get_view_type()) != null) {
            mHelpers.get(item.get_view_type()).convert(position, holder, item);
        }
    }

    public void setHolderHelpers(SparseArray<C_BaseHolderHelper<T>> helpers) {
        this.mHelpers = helpers;
    }

    @Override
    public int getLayoutId(int viewType) {
        if (mHelpers.get(viewType) != null) {
            return mHelpers.get(viewType).getLayoutId();
        } else {
            return R.layout.c_list_item_empty;
        }
    }

    @Override
    public int getItemViewType(int position, T item) {
        return item.get_view_type();
    }

    public SparseArray<C_BaseHolderHelper<T>> getmHelpers() {
        return mHelpers;
    }

    @Override
    public C_OnItemClickListener getOnItemClickListener() {
        return null;
    }

    public int get_checked_position() {
        return _checked_position;
    }

    public void set_checked_position(int _checked_position) {
        this._checked_position = _checked_position;
    }
}
