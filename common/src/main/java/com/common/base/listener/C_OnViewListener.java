package com.common.base.listener;

import android.view.MotionEvent;
import android.view.View;

import com.common.base.adapter.C_BaseMultiRecyclerHolder;

/**
 * Created by ricky on 2016/9/21.
 *
 * 自定义的视图监听事件（点击/触摸等）
 */

public interface C_OnViewListener<T> {

    //点击事件
    void onClick(View v, int position, C_BaseMultiRecyclerHolder holder, T item);

    //触摸事件
    boolean onTouch(View v, MotionEvent event, int position, C_BaseMultiRecyclerHolder holder, T item);

    //文本改变事件
    void onTextChange(CharSequence s, int position, C_BaseMultiRecyclerHolder holder, T item);
}
