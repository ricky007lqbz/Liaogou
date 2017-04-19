package com.common.base.listener;

/**
 * Created by ricky on 2016/05/24.
 *
 * 下拉刷新与加载更多接口，用于presenter与view解耦
 */
public interface C_RefreshAndLoadMoreCallBack {
    /**
     * 下拉刷新
     */
    void refresh();

    /**
     * 加载更多
     * @param isPrePage  是否是加载上一页 ;默认加载下一页
     */
    void loadMore(boolean isPrePage);
}
