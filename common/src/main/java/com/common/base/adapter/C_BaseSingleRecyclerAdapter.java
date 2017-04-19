package com.common.base.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by ricky on 2016/05/17.
 * <p/>
 * 基本的单种类型的RecycleViewAdapter
 * <p/>
 * <p/>
 * StickyRecyclerHeadersAdapter 固定头部接口实现
 */
public abstract class C_BaseSingleRecyclerAdapter<T> extends C_BaseMultiRecyclerAdapter<T> {

    public C_BaseSingleRecyclerAdapter(Context context) {
        super(context);
    }

    public C_BaseSingleRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position, T item) {
        return 0;
    }

    @Override
    public C_OnItemClickListener getOnItemClickListener() {
        return null;
    }
}
