package com.common.base.adapter;

import android.content.Context;
import android.view.View;

/**
 * Created by ricky on 2016/09/13.
 *
 * 挂顶项
 */
public abstract class C_BasePinnedHolderHelper<T> extends C_BaseHolderHelper<T> {

    public C_BasePinnedHolderHelper(Context context) {
        super(context);
    }

    /**
     * 头部挂顶部分的点击事件
     */
    public abstract void onStickyHeadersOnclick(View view, int position, long headId, T item);
}
