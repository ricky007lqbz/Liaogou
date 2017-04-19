package com.common.base.model;

import com.common.net.C_HttpRequest;
import com.common.net.C_OnNetRequestListener;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by ricky on 2016/05/30.
 * <p/>
 * 通过网络请求获取原始数据，并返回原始数据=的model基类
 *
 * @param <T> 网络请求返回的原始数据
 */
public abstract class C_BaseDataModel<T> {

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(String url,
                            int what,
                            boolean isRefresh,
                            Type type,
                            C_OnNetRequestListener<T> listener) {
        C_HttpRequest.getInstance().requestGetDataWrapper(url, what, isRefresh, type, listener);
    }

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(String url,
                            List<String> params,
                            int what,
                            boolean isRefresh,
                            Type type,
                            C_OnNetRequestListener<T> listener) {
        C_HttpRequest.getInstance().requestGetDataWrapper(url, params, what, isRefresh, type, listener);
    }

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(String url,
                            Map<String, String> params,
                            int what,
                            boolean isRefresh,
                            Type type,
                            C_OnNetRequestListener<T> listener) {
        C_HttpRequest.getInstance().requestGetDataWrapper(url, params, what, isRefresh, type, listener);
    }
}
