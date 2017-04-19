package com.common.net;

/**
 * Created by ricky on 2016/09/13.
 *
 * 单独的网络请求成功监听事件(区分OnDataRequestSuccessListener, 两者本质一样，只是为了能够同时继承而已)
 */
public interface C_OnDomainRequestSuccessListener<T> {

    void onDomainRequestSuccess(T data, boolean isRefresh);
}
