package com.common.util.rxjava;

import android.text.TextUtils;

import com.common.Common;
import com.common.bean.C_DataWrapper;
import com.common.exception.C_NetServerException;
import com.common.net.C_OnNetRequestListener;
import com.common.util.C_LocalDataManager;
import com.common.util.C_ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by ricky on 2016/08/17.
 * <p/>
 * rxJava处理服务器返回结果的工具类
 */
public class C_RxResultHelper {
    //使用正则表达式从reponse的头中提取cookie内容的子串
    private static final Pattern pattern = Pattern.compile("Set-Cookie\\d*");

    /**
     * 把网络返回的封装的DataWrapper转换成自己所需要的数据
     */
    public static <T> Observable.Transformer<C_DataWrapper<JsonElement>, T> convertDataWrapper(final Type type) {
        return new Observable.Transformer<C_DataWrapper<JsonElement>, T>() {
            @Override
            public Observable<T> call(Observable<C_DataWrapper<JsonElement>> tObservable) {
                return tObservable.flatMap(
                        new Func1<C_DataWrapper<JsonElement>, Observable<T>>() {
                            @Override
                            public Observable<T> call(C_DataWrapper<JsonElement> result) {
                                switch (result.getCode()) {
                                    case Common.net.CODE_SUCCESS:
                                    case Common.net.CODE_CACHE:
                                        Gson gson = new Gson();
                                        T t = gson.fromJson(result.getData(), type);
                                        return createData(t);
                                    case Common.net.CODE_ERROR:
                                        return Observable.error(new C_NetServerException(result.getMessage()));
                                    default:
                                        return Observable.empty();
                                }
                            }
                        }
                );
            }
        };
    }

    /**
     * 把Response<JsonObject>转换成需要的数据bean
     */
    public static <T> Observable.Transformer<Response<JsonObject>, T> convertResponse(final Type type) {
        return new Observable.Transformer<Response<JsonObject>, T>() {
            @Override
            public Observable<T> call(Observable<Response<JsonObject>> tObservable) {
                return tObservable.flatMap(
                        new Func1<Response<JsonObject>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Response<JsonObject> result) {
                                return convertResponse(result, type);
                            }
                        }
                );
            }
        };
    }

    /**
     * 转换response为需要的类型
     */
    private static <T> Observable<T> convertResponse(Response<JsonObject> result, Type type) {
        if (result == null) {
            return Observable.empty();
        }
        int code = result.code();
        switch (code) {
            case 200:
            case 304:
                //只有登录成功才保存cookie
                if (result.raw().request().url().toString().contains(Common.API_LOGIN)) {
                    saveCookies(result);
                }
                Gson gson = new Gson();
                T data;
                if (type == null) {
                    data = gson.fromJson(result.body(), new TypeToken<C_DataWrapper<JsonElement>>() {
                    }.getType());
                } else {
                    if (type.toString().contains(C_DataWrapper.class.getName())) {
                        data = gson.fromJson(result.body(), type);
                    } else {
                        data = gson.fromJson(result.body(), type);
                    }
                }

                if (null != data) {
                    return C_RxResultHelper.createData(data);
                } else {
                    return Observable.empty();
                }
            default:
                //网络错误
                if (code >= 400) {
                    return Observable.error(new C_NetServerException());
                } else {
                    return Observable.empty();
                }
        }
    }

    /**
     * 保存cookie
     */
    private static void saveCookies(Response<JsonObject> response) {
        StringBuffer cookies = new StringBuffer();
        for (String key : response.headers().names()) {
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                String cookieFromResponse = response.headers().get(key);
                cookies.append(cookieFromResponse);
            }
        }
        if (!TextUtils.isEmpty(cookies)) {
            //cookies
            C_LocalDataManager.putCookie(cookies.toString());
        }
    }

    /**
     * 创建一个新的Observable
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 通过新的网络Observer
     */
    public static <T> Action0 createAction0(final int what,
                                            final C_OnNetRequestListener<T> listener) {
        return new Action0() {
            @Override
            public void call() {
                if (listener != null) {
                    listener.onRequestStart(what);
                }
            }
        };
    }

    /**
     * 通过新的网络Observer
     */
    public static <T> Observer<T> createObserver(final String url,
                                                 final int what,
                                                 final boolean isRefresh,
                                                 final C_OnNetRequestListener<T> listener) {
        return createObserver(url, false, what, isRefresh, listener);
    }

    /**
     * 通过新的网络Observer
     */
    public static <T> Observer<T> createObserver(final String url,
                                                 final boolean withToast,
                                                 final int what,
                                                 final boolean isRefresh,
                                                 final C_OnNetRequestListener<T> listener) {
        return createObserver(url, withToast, what, isRefresh, listener, null);
    }

    /**
     * 通过新的网络Observer
     */
    public static <T> Subscriber<T> createObserver(final String url,
                                                   final boolean withToast,
                                                   final int what,
                                                   final boolean isRefresh,
                                                   final C_OnNetRequestListener<T> listener,
                                                   final C_OnNetRequestListener secondListener) {
        return new Subscriber<T>() {

            @Override
            public void onStart() {
                if (secondListener != null) {
                    secondListener.onRequestStart(what);
                }
            }

            @Override
            public void onCompleted() {
                if (listener != null) {
                    listener.onRequestFinish(what);
                }
                if (secondListener != null) {
                    secondListener.onRequestFinish(what);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onRequestFailed(what, e, url);
                    listener.onRequestFinish(what);
                }
                if (secondListener != null) {
                    secondListener.onRequestFinish(what);
                }
            }

            @Override
            public void onNext(T data) {
                if (withToast) {
                    if (data instanceof C_DataWrapper) {
                        int code = ((C_DataWrapper) data).getCode();
                        if (code != Common.net.CODE_SUCCESS) {
                            C_ToastUtil.show(((C_DataWrapper) data).getMessage());
                        }
                    }
                }
                if (listener != null) {
                    listener.onRequestSuccess(what, data, isRefresh);
                }
            }
        };
    }
}
