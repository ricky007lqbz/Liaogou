package com.common.net;

/**
 * Created by ricky on 2016/05/24.
 *
 * 通用的网络请求回调接口
 */
public interface C_OnNetRequestListener<T> {
    /**
     * 网络请求开始
     * @param what 第几个请求
     */
    void onRequestStart(int what);

    /**
     * 网络请求结束(不论失败还是成功都调用）
     * @param what 第几个请求
     */
    void onRequestFinish(int what);

    /**
     * 网络请求成功
     * @param what 第几个请求
     * @param data 返回的数据实体类信息 泛型定义
     * @param isRefresh 是否是刷新数据
     */
    void onRequestSuccess(int what, T data, boolean isRefresh);

    /**
     * 请求失败
     * @param what 第几个请求
     * @param t 异常
     */
    void onRequestFailed(int what, Throwable t, String url);
}
