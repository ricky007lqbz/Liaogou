package com.common.net;

import com.common.Common;
import com.common.util.C_L;
import com.common.util.C_NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ricky on 2016/05/19.
 * <p/>
 * 网络请求管理类
 */
public class C_HttpManager {

    protected static final String X_LC_Id = ""; //
    protected static final String X_LC_Key = "";

    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            initOkHttpClient();
            mRetrofit = new Retrofit.Builder()
                    //请求的url头
                    .baseUrl(Common.net.HOST)
                    //配置转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //配置回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //自定义OkHttpClient，即每次发送请求时都会调用一次这个方法，然后把固定参数加在请求中发送
                    .client(mOkHttpClient)
                    .build();


        }
        return mRetrofit;
    }

    /**
     * 配置OkHttpClient
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            File cacheFile = new File(Common.net.HTTP_CATCH_PATH); //指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //指定缓存大小50M

            //统一添加请求头部用的拦截器
            Interceptor headInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request().newBuilder()
                            .addHeader("X-LC-Id", X_LC_Id)
                            .addHeader("X-LC-Key", X_LC_Key)
                            .addHeader("Content-Type", "application/json")
                            .build());
                }
            };

            //打印log用的拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(Common.IS_DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);

            mOkHttpClient = new OkHttpClient.Builder()
                    //添加本地缓存拦截器，用来拦截本地缓存
                    .addInterceptor(getNetworkInterceptor())
                    //添加网络拦截器，用来拦截网络数据
                    .addNetworkInterceptor(getNetworkInterceptor())
                    //输出log用的拦截器
                    .addInterceptor(loggingInterceptor)
                    //暂时不需要添加请求头
//                    .addInterceptor(headInterceptor)
                    .sslSocketFactory(C_SSLContextUtil.getDefaultSLLContext().getSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .cache(cache)
                    .connectTimeout(Common.net.TIME_OUT, TimeUnit.MILLISECONDS)
                    .build();
        }
    }

    /**
     * 网络缓存拦截器
     */
    private static Interceptor getNetworkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String cacheHeaderValue;
                if (!C_NetworkUtil.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    cacheHeaderValue = "public, only-if-cached, max-stale=" + CACHE_STALE_SEC;
                    C_L.d("OkHttp", "网络不可用请求拦截");
                } else {
                    cacheHeaderValue = "public, max-age=3";
                    C_L.d("OkHttp", "网络可用请求拦截");
                }
                Response originalResponse = chain.proceed(request);

                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
        };
    }

    public static <S> S onServiceCreate(Class<S> clazz) {
        return getInstance().create(clazz);
    }

    public static C_ApiService getDefaultService() {
        return onServiceCreate(C_ApiService.class);
    }
}
