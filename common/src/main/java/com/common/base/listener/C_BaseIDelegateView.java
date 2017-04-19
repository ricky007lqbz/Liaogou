package com.common.base.listener;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ricky on 2016/05/23.
 *
 * 视图层代理的基本接口
 */
public interface C_BaseIDelegateView {

    void onCreate(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getRootView();

    /**
     * 初始化代理视图配置
     */
    void initWidget();

    void onDestroy();
}
