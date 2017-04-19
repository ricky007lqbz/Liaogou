package com.common.net;

/**
 * Created by ricky on 2016/09/13.
 *
 * 单独的网络请求成功监听事件
 */
public interface C_OnDataRequestSuccessListener<T> {

    void onDataRequestSuccess(T data, boolean isRefresh);
}
