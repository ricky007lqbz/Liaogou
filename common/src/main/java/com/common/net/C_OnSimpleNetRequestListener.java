package com.common.net;

import android.text.TextUtils;

import com.common.Common;
import com.common.util.C_L;
import com.common.util.C_ToastUtil;

/**
 * Created by ricky on 2016/06/30.
 * <p/>
 * 简单的只返回网络请求成功和失败 回调的网络请求 回调接口
 *
 * @param <Ds> DataWrapper
 */
public abstract class C_OnSimpleNetRequestListener<Ds> implements C_OnNetRequestListener<Ds> {

    @Override
    public void onRequestStart(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }

    @Override
    public void onRequestFailed(int what, Throwable t, String url) {
        C_L.e("onRequestFailed", url);
        if (t != null && !TextUtils.isEmpty(t.getMessage())) {
            C_L.e(t.getMessage());
        }
        if (Common.IS_DEBUG) {
            C_ToastUtil.showSingle("请求错误");
        }
    }
}
