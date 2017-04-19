package com.common.net;

import android.text.TextUtils;

import com.common.Common;
import com.common.bean.C_DataWrapper;
import com.common.util.C_ArrayUtil;
import com.common.util.C_LocalDataManager;
import com.common.util.rxjava.C_RxResultHelper;
import com.common.util.rxjava.C_RxSchedulersHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscription;

/**
 * Created by ricky on 2016/08/17.
 * <p/>
 * 实际做网络请求的类
 */
public class C_HttpRequest {

    private Map<String, Subscription> mRequestQueueMap;

    private static C_HttpRequest instance;

    private C_HttpRequest() {
        mRequestQueueMap = new HashMap<>();
    }

    public static C_HttpRequest getInstance() {
        if (instance == null) {
            instance = new C_HttpRequest();
        }
        instance.token = "";
        instance.dialogListener = null;
        return instance;
    }

    private void addRequestQueue(String url, Subscription subscription) {
        if (C_ArrayUtil.isEmpty(mRequestQueueMap)) {
            mRequestQueueMap.put(url, subscription);
        }
    }

    /**
     * 清空请求队列
     */
    private void removeRequestQueue(String url) {
        if (null != mRequestQueueMap.get(url)) {
            mRequestQueueMap.get(url).unsubscribe();
        }
    }

    /**
     * 清空所有的请求队列
     */
    public void clearAllRequestQueue() {
        if (!C_ArrayUtil.isEmpty(mRequestQueueMap)) {
            mRequestQueueMap.clear();
        }
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        requestDataWrapper(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          Map<String, String> params,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDataWrapper(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          List<String> params,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestDataWrapper(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          int what,
                                          boolean isRefresh,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        requestDataWrapper(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          Map<String, String> params,
                                          int what,
                                          boolean isRefresh,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDataWrapper(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestGetDataWrapper(String url,
                                          List<String> params,
                                          int what,
                                          boolean isRefresh,
                                          Type type,
                                          C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestDataWrapper(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     * @param <P>       需要的封装的数据(DomainWrapper<>)
     */
    public <T, P> void requestGetDomainWrapper(String url,
                                               int what,
                                               boolean isRefresh,
                                               Type type,
                                               Observable.Transformer<T, P> transformer,
                                               C_OnNetRequestListener<P> listener) {
        requestDomainWrapper(getGetObservable(url), url, what, isRefresh, false, type, transformer, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     * @param <P>       需要的封装的数据(DomainWrapper<>)
     */
    public <T, P> void requestGetDomainWrapper(String url,
                                               Map<String, String> params,
                                               int what,
                                               boolean isRefresh,
                                               Type type,
                                               Observable.Transformer<T, P> transformer,
                                               C_OnNetRequestListener<P> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDomainWrapper(getGetObservable(url), url, what, isRefresh, false, type, transformer, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的封装的数据(C_DataWrapper<>)
     * @param <P>       需要的封装的数据(DomainWrapper<>)
     */
    public <T, P> void requestGetDomainWrapper(String url,
                                               List<String> params,
                                               int what,
                                               boolean isRefresh,
                                               Type type,
                                               Observable.Transformer<T, P> transformer,
                                               C_OnNetRequestListener<P> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestDomainWrapper(getGetObservable(url), url, what, isRefresh, false, type, transformer, listener);
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        requestData(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   Map<String, String> params,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestData(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   List<String> params,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestData(getGetObservable(url), url, Common.net.SINGLE_QUEUE, true, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   int what,
                                   boolean isRefresh,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        requestData(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   Map<String, String> params,
                                   int what,
                                   boolean isRefresh,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestData(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     */
    public <T> void requestGetData(String url,
                                   List<String> params,
                                   int what,
                                   boolean isRefresh,
                                   Type type,
                                   C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestData(getGetObservable(url), url, what, isRefresh, false, type, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     * @param <P>       需要的数据类型(P)
     */
    public <T, P> void requestGetDomain(String url,
                                        int what,
                                        boolean isRefresh,
                                        boolean withToast,
                                        Type type,
                                        Observable.Transformer<T, P> transformer,
                                        C_OnNetRequestListener<P> listener) {
        requestDomain(getGetObservable(url), url, what, isRefresh, withToast, type, transformer, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     * @param <P>       需要的数据类型(P)
     */
    public <T, P> void requestGetDomain(String url,
                                        Map<String, String> params,
                                        int what,
                                        boolean isRefresh,
                                        boolean withToast,
                                        Type type,
                                        Observable.Transformer<T, P> transformer,
                                        C_OnNetRequestListener<P> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDomain(getGetObservable(url), url, what, isRefresh, withToast, type, transformer, listener);
    }

    /**
     * get请求
     *
     * @param url       域名
     * @param what      请求队列序号
     * @param isRefresh 是否刷新
     * @param listener  网络监听回调
     * @param <T>       需要的数据类型(T)
     * @param <P>       需要的数据类型(P)
     */
    public <T, P> void requestGetDomain(String url,
                                        List<String> params,
                                        int what,
                                        boolean isRefresh,
                                        boolean withToast,
                                        Type type,
                                        Observable.Transformer<T, P> transformer,
                                        C_OnNetRequestListener<P> listener) {
        //把参数放入url中
        url = listToParamString(url, params);
        requestDomain(getGetObservable(url), url, what, isRefresh, withToast, type, transformer, listener);
    }

    /**
     * post请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestPostWrapperData(String url,
                                           Type type,
                                           C_OnNetRequestListener<T> listener) {
        requestDataWrapper(getPostObservable(url), url, Common.net.SINGLE_QUEUE, false, true, type, listener);
    }

    /**
     * post请求
     *
     * @param url      域名
     * @param what     请求队列序号
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestPostWrapperData(String url,
                                           int what,
                                           Type type,
                                           C_OnNetRequestListener<T> listener) {
        requestDataWrapper(getPostObservable(url), url, what, false, true, type, listener);
    }

    /**
     * post请求
     *
     * @param url      域名
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestPostWrapperData(String url,
                                           Map<String, String> params,
                                           Type type,
                                           C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDataWrapper(getPostObservable(url), url, Common.net.SINGLE_QUEUE, false, true, type, listener);
    }

    /**
     * post请求
     *
     * @param url      域名
     * @param what     请求队列序号
     * @param listener 网络监听回调
     * @param <T>      需要的封装的数据(C_DataWrapper<>)
     */
    public <T> void requestPostWrapperData(String url,
                                           Map<String, String> params,
                                           int what,
                                           Type type,
                                           C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestDataWrapper(getPostObservable(url), url, what, false, true, type, listener);
    }

    /**
     * post请求
     *
     * @param url      域名
     * @param what     请求队列序号
     * @param listener 网络监听回调
     * @param <T>      需要的数据类型(T)
     */
    public <T> void requestPost(String url,
                                Map<String, String> params,
                                int what,
                                Type type,
                                C_OnNetRequestListener<T> listener) {
        //把参数放入url中
        url = mapToParamString(url, params);
        requestData(getPostObservable(url), url, what, false, true, type, listener);
    }

    /**
     * 网络请求（参数格式常规key=value&key=value)
     *
     * @param observable 观察者
     * @param url        域名
     * @param what       同一个界面，请求队列序号
     * @param isRefresh  是否刷新
     * @param listener   网络监听回调
     * @param <T>        需要的数据类型(C_DataWrapper<>)
     *                   <p/>
     *                   observable的url需和传进来的url一致，否则肯引起内存溢出
     */
    public <T> void requestDataWrapper(Observable<Response<JsonObject>> observable,
                                       String url,
                                       int what,
                                       boolean isRefresh,
                                       boolean withToast,
                                       Type type,
                                       C_OnNetRequestListener<T> listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //如果队列里有这个请求，则取消该请求
        removeRequestQueue(url);
        Subscription subscription = observable
                //io 主线程切换
                .compose(C_RxSchedulersHelper.<Response<JsonObject>>io_main())
                //添加开始的监听
                .doOnSubscribe(C_RxResultHelper.createAction0(what, listener))
                //把网络返回的原始数据，转换成需要的数据
                .compose(C_RxResultHelper.<T>convertResponse(type))
                //添加订阅，回调传给listener
                .subscribe(C_RxResultHelper.createObserver(url, withToast, what, isRefresh, listener, dialogListener));
        //把请求添加到队列里
        addRequestQueue(url, subscription);
    }

    /**
     * 网络请求（参数格式常规key=value&key=value)
     *
     * @param observable  观察者
     * @param url         域名
     * @param what        同一个界面，请求队列序号
     * @param isRefresh   是否刷新
     * @param type        网络返回数据的类型
     * @param listener    网络监听回调
     * @param <T>         网络返回的数据类型(C_DataWrapper<>)
     * @param <P>         需要的数据类型(DomainWrapper<>)
     * @param transformer 用来转换data和domain的转换器
     *                    <p/>
     *                    observable的url需和传进来的url一致，否则肯引起内存溢出
     */
    public <T, P> void requestDomainWrapper(Observable<Response<JsonObject>> observable,
                                            String url,
                                            int what,
                                            boolean isRefresh,
                                            boolean withToast,
                                            Type type,
                                            Observable.Transformer<T, P> transformer,
                                            C_OnNetRequestListener<P> listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //如果队列里有这个请求，则取消该请求
        removeRequestQueue(url);
        Subscription subscription = observable
                //io 主线程切换
                .compose(C_RxSchedulersHelper.<Response<JsonObject>>io_main())
                //添加开始的监听
                .doOnSubscribe(C_RxResultHelper.createAction0(what, listener))
                //把网络返回的原始数据，转换成需要的数据
                .compose(C_RxResultHelper.<T>convertResponse(type))
                //网络返回的数据转换成自己所需要的domain
                .compose(transformer)
                //添加订阅，回调传给listener
                .subscribe(C_RxResultHelper.createObserver(url, withToast, what, isRefresh, listener, dialogListener));
        //把请求添加到队列里
        addRequestQueue(url, subscription);
    }

    /**
     * 网络请求（参数格式常规key=value&key=value)
     *
     * @param observable 观察者
     * @param url        域名
     * @param what       同一个界面，请求队列序号
     * @param isRefresh  是否刷新
     * @param listener   网络监听回调
     * @param <T>        需要的数据类型(T、List<T>...)
     */
    public <T> void requestData(Observable<Response<JsonObject>> observable,
                                String url,
                                int what,
                                boolean isRefresh,
                                boolean withToast,
                                Type type,
                                C_OnNetRequestListener<T> listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //如果队列里有这个请求，则取消该请求
        removeRequestQueue(url);
        Subscription subscription = observable
                //io 主线程切换
                .compose(C_RxSchedulersHelper.<Response<JsonObject>>io_main())
                //添加开始的监听
                .doOnSubscribe(C_RxResultHelper.createAction0(what, listener))
                //把网络返回的原始数据，转换成需要的数据
                .compose(C_RxResultHelper.<C_DataWrapper<JsonElement>>convertResponse(type))
                //把封装的网络数据转换成所需要的数据Bean
                .compose(C_RxResultHelper.<T>convertDataWrapper(type))
                //添加订阅，回调传给listener
                .subscribe(C_RxResultHelper.createObserver(url, withToast, what, isRefresh, listener, dialogListener));
        //把请求添加到队列里
        addRequestQueue(url, subscription);
    }

    /**
     * 网络请求（参数格式常规key=value&key=value)
     *
     * @param observable  观察者
     * @param url         域名
     * @param what        同一个界面，请求队列序号
     * @param isRefresh   是否刷新
     * @param type        网络返回数据的类型
     * @param listener    网络监听回调
     * @param <T>         网络返回的数据类型(T、List<T>...)
     * @param <P>         需要的数据类型(P、List<P>...)
     * @param transformer 用来转换data和domain的转换器
     *                    <p/>
     *                    observable的url需和传进来的url一致，否则肯引起内存溢出
     */
    public <T, P> void requestDomain(Observable<Response<JsonObject>> observable,
                                     String url,
                                     int what,
                                     boolean isRefresh,
                                     boolean withToast,
                                     Type type,
                                     Observable.Transformer<T, P> transformer,
                                     C_OnNetRequestListener<P> listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //如果队列里有这个请求，则取消该请求
        removeRequestQueue(url);
        Subscription subscription = observable
                //io 主线程切换
                .compose(C_RxSchedulersHelper.<Response<JsonObject>>io_main())
                //添加开始的监听
                .doOnSubscribe(C_RxResultHelper.createAction0(what, listener))
                //把网络返回的原始数据，转换成需要的数据
                .compose(C_RxResultHelper.<C_DataWrapper<JsonElement>>convertResponse(type))
                //把封装的网络数据转换成所需要的数据Bean
                .compose(C_RxResultHelper.<T>convertDataWrapper(type))
                //转换data成domain
                .compose(transformer)
                //添加订阅，回调传给listener
                .subscribe(C_RxResultHelper.createObserver(url, withToast, what, isRefresh, listener, dialogListener));
        //把请求添加到队列里
        addRequestQueue(url, subscription);
    }

    private String token;

    public C_HttpRequest setToken(String token) {
        this.token = token;
        return this;
    }

    private C_OnNetRequestListener dialogListener;

    public C_HttpRequest setDialogListener(C_OnNetRequestListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public String addTokenToUrl(String url) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(token)) {
            if (url.contains("?")) {
                url += "&token=" + token;
            } else {
                url += "?token=" + token;
            }
        }
        return url;
    }

    /**
     * 获取get方法的网络请求observable
     */
    public Observable<Response<JsonObject>> getGetObservable(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return C_HttpManager.getDefaultService().onGetRequest(
                addTokenToUrl(url), C_LocalDataManager.getCookies());
    }

    /**
     * 获取post方法的网络请求observable
     */
    public Observable<Response<JsonObject>> getPostObservable(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return C_HttpManager.getDefaultService().onPostRequest(
                addTokenToUrl(url), C_LocalDataManager.getCookies());
    }

    /**
     * map转请求参数字符串
     *
     * @param params 参数
     * @return 字符串
     */
    public String mapToParamString(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (C_ArrayUtil.isEmpty(params)) {
            return url;
        }
        String paramString = "";
        for (String key : params.keySet()) {
            if (null == params.get(key)) {
                continue;
            }

            String value = null;
            try {
                value = URLEncoder.encode("" + params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (0 < paramString.length()) {
                paramString = paramString + "&" + key + "=" + value;
            } else {
                paramString = key + "=" + value;
            }
        }
        return url + "?" + paramString;
    }

    /**
     * map转请求参数字符串
     *
     * @param params 参数
     * @return 字符串
     */
    private String listToParamString(String url, List<String> params) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (C_ArrayUtil.isEmpty(params)) {
            return url;
        }
        StringBuilder paramString = new StringBuilder("");
        for (String param : params) {
            paramString.append(param).append("-");
        }
        int length = paramString.length();
        return url + "/" + paramString.substring(0, length - 1);
    }
}
