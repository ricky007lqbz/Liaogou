package com.common.base.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by ricky on 2016/05/24.
 * <p/>
 * 基本加载视图用的接口
 */
public interface C_BaseLoadingView {

    /**
     * 显示加载中的视图
     */
    void showLoading();

    /**
     * 显示加载完成的内容
     */
    void showContent();

    /**
     * 显示加载错误的视图
     *
     * @param imgResId 错误提示内容图片id
     * @param msg 错误提示内容
     * @param listener  点击事件
     */
    void showError(int imgResId, String msg, View.OnClickListener listener);

    /**
     * 显示空视图
     * @param imgResId 图片资源
     */
    void showEmpty(int imgResId, @NonNull String content,
                   @NonNull String btnContent,
                   @NonNull View.OnClickListener onIvClickListener,
                   @NonNull View.OnClickListener onBtnClickListener);

    /**
     * 显示其他空view
     */
    void showOtherEmptyView(View view);

    /**
     * 活动当前view的Context
     *
     * @return Context
     */
    Context getContext();
}
