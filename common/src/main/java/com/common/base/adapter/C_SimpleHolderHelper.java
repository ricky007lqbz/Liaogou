package com.common.base.adapter;

import android.content.Context;

/**
 * Created by ricky on 2016/05/27.
 * <p/>
 * 用来处理recyclerView每项item的Holder帮助抽象类
 */
public class C_SimpleHolderHelper<T> extends C_BaseHolderHelper<T> {

    private int layoutId;

    public C_SimpleHolderHelper(Context context, int layoutId) {
        super(context);
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void convert(int position, C_BaseMultiRecyclerHolder holder, T item) {

    }
}
