package com.common.widget.stickylayout;

/**
 * Created by ricky on 2016/08/24.
 * <p/>
 * 嵌套滑动的滚动监听事件
 */
public interface C_StickyFlingListener {

    void onNestedPreScroll(int topHeight, int scrollY, int dy);

    void onStopNestedScroll(int topHeight, int scrollY, int dy);

    //通知可以刷新了
    void onRefresh();
}
