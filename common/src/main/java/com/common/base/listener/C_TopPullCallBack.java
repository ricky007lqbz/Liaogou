package com.common.base.listener;

/**
 * Created by ricky on 2016/06/15.
 *
 * RecyclerView顶部下拉 和 上推 对应的回调事件
 */
public interface C_TopPullCallBack {
    //上推
    void onTopPullUp();
    //下拉
    void onTopPullDown();
    //完成上推
    void onTopPullUpComplete();
    //完成下拉
    void onTopPullDownComplete();
}
