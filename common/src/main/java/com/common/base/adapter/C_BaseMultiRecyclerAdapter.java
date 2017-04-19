package com.common.base.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ricky on 2016/05/17.
 * <p/>
 * 基本的多种类型的RecycleViewAdapter
 * <p/>
 * <p/>
 * StickyRecyclerHeadersAdapter 固定头部接口实现
 */
public abstract class C_BaseMultiRecyclerAdapter<T> extends C_BaseRecyclerAdapter<T, C_BaseMultiRecyclerHolder> {

    public C_BaseMultiRecyclerAdapter(Context context) {
        super(context);
    }

    public C_BaseMultiRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public C_BaseMultiRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        C_BaseMultiRecyclerHolder holder;
        holder = C_BaseMultiRecyclerHolder.get(mContext, parent, getLayoutId(viewType), viewType);
        if (mListener != null) {
            holder.setOnItemClickListener(mListener);
        } else {
            holder.setOnItemClickListener(getOnItemClickListener());
        }
        return holder;
    }
}
