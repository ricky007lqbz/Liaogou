package com.common.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ricky on 2016/05/27.
 * <p>
 * 用来处理recyclerView每项item的Holder帮助抽象类
 */
public abstract class C_BaseHolderHelper<T> extends C_BaseHHelper<T, C_BaseMultiRecyclerHolder> {

    public C_BaseHolderHelper(Context context) {
        super(context);
    }

    /**
     * 通过viewGroup 添加itemView
     */
    public void addItemViewToViewGroup(T data, ViewGroup content) {
        View itemView = LayoutInflater.from(context).inflate(this.getLayoutId(), content, true);
        C_BaseMultiRecyclerHolder<T> recyclerHolder = new C_BaseMultiRecyclerHolder<>(itemView, 0);
        recyclerHolder.setItem(data);
        recyclerHolder.registerOnViewListener(this);
        this.convert(0, recyclerHolder, data);
    }

    /**
     * 通过viewGroup 刷新itemView
     */
    public void refreshItemView(T data, View itemView) {
        C_BaseMultiRecyclerHolder<T> recyclerHolder = new C_BaseMultiRecyclerHolder<>(itemView, 0);
        recyclerHolder.setItem(data);
        recyclerHolder.registerOnViewListener(this);
        this.convert(0, recyclerHolder, data);
    }
}
