package com.common.net;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ricky on 2016/05/20.
 * <p/>
 * 网络请求 API服务类接口类
 * <p/>
 * 通过注解 声明 URL （接口） +  Params （参数）
 */
public interface C_ApiService {

    /**
     * post请求
     */
    @POST()
    Observable<Response<JsonObject>> onPostRequest(@Url String url, @Header("Cookie") String cookie);

    /**
     * get请求
     */
    @GET()
    Observable<Response<JsonObject>> onGetRequest(@Url String url, @Header("Cookie") String cookie);

    /**
     * 下载图片
     */
    @GET
    Call<ResponseBody> downloadPicFromNet(@Url String ulr);

    /**
     * 下载文件
     */
    @GET
    Call<ResponseBody> downloadFileFromNet(@Url String ulr);
}
