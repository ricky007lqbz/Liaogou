package com.common.base.model;

import com.common.bean.C_Wrapper;
import com.common.net.C_HttpRequest;
import com.common.net.C_OnNetRequestListener;
import com.common.util.rxjava.C_RxResultHelper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ricky on 2016/05/30.
 * <p/>
 * 通过网络请求获取原始数据，并返回View所需要的数据（domain）的model基类
 *
 * @param <T> 网络请求返回的原始数据
 * @param <P> View所需要的数据
 */
public abstract class C_BaseDomainModel<T, P> {

    /* 多种item之一的默认item标志；推荐该标志用于可翻页数据item */
    public static final int DEFAULT_HOLDER = 0; // 默认 item 类型

    /**
     * 获取当前界面对应的url
     */
    protected abstract String getUrl();

    /**
     * 获取网络返回数据的type类型
     */
    protected abstract Type getDataType();

    /**
     * 把data转换成domain的核心方法
     */
    protected abstract P convert(T data, boolean isRefresh);

    /**
     * map格式的请求参数（当内部调用的时候使用）
     */
    protected Map<String, String> getMapParams(Map<String, String> params) {
        return params;
    }

    /**
     * list格式的请求参数（当内部调用的时候使用）
     */
    protected List<String> getListParams(List<String> params) {
        return params;
    }

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(int what,
                            boolean isRefresh,
                            C_OnNetRequestListener<P> listener) {
        C_HttpRequest.getInstance()
                .setToken(getToken())
                .requestGetDomainWrapper(getUrl(), what, isRefresh, getDataType(), getTransformer(isRefresh), listener);
    }

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(List<String> params,
                            int what,
                            boolean isRefresh,
                            C_OnNetRequestListener<P> listener) {
        C_HttpRequest.getInstance()
                .setToken(getToken())
                .requestGetDomainWrapper(getUrl(), getListParams(params), what, isRefresh, getDataType(), getTransformer(isRefresh), listener);
    }

    /**
     * 外部调用的请求数据的方法
     */
    public void requestData(Map<String, String> params,
                            int what,
                            boolean isRefresh,
                            C_OnNetRequestListener<P> listener) {
        C_HttpRequest.getInstance()
                .setToken(getToken())
                .requestGetDomainWrapper(getUrl(), getMapParams(params), what, isRefresh, getDataType(), getTransformer(isRefresh), listener);
    }

    /**
     * 新建转换data成domain的rxJava的transformer
     */
    protected Observable.Transformer<T, P> getTransformer(final boolean isRefresh) {
        return new Observable.Transformer<T, P>() {
            @Override
            public Observable<P> call(Observable<T> observable) {
                return observable.flatMap(new Func1<T, Observable<P>>() {
                    @Override
                    public Observable<P> call(T t) {
                        return C_RxResultHelper.createData(convert(t, isRefresh));
                    }
                });
            }
        };
    }

    protected String getToken() {
        return "";
    }

    /**
     * 把原始数据的基本信息copy给view所需要的基本信息
     *
     * @param domain view需要的数据
     * @param data   网络数据
     */
    protected void convertWrapper(C_Wrapper data, C_Wrapper domain) {
        domain.setCode(data.getCode());
        domain.setMessage(data.getMessage());
        domain.setPageNo(data.getPageNo());
        domain.setPageSize(data.getPageSize());
        domain.setTimestamp(data.getTimestamp());
        domain.setTotalNo(data.getTotalNo());
        domain.setTotals(data.getTotals());
        domain.setExp(data.getExp());
    }
}
