package com.common.base.delegate;

import android.widget.TextView;

import com.common.widget.C_TabIndicator;
import com.common.widget.stickylayout.C_StickyFlingListener;

/**
 * Created by ricky on 2016/9/27.
 * <p>
 * 头部banner为透明的终端delegate
 */

public class C_TransparentTabTerminalDelegate extends C_BaseTabTerminalDelegate {

    protected C_StickyFlingListener flingListener;
    protected TextView tvCenter;

    @Override
    public void initWidget() {
        super.initWidget();
        if (getTopBanner() != null) {
            tvCenter = getTopBanner().getTvCenter();
        }
        setTabIndicator(C_TabIndicator.TYPE_BLACK20_ALPHA);
    }

    @Override
    public void onNestedPreScroll(int topHeight, int scrollY, int dy) {
        float f = ((float) scrollY) / topHeight;
        setTitleByAnim(f);
        setTopViewAnim(f);
        if (flingListener != null)
            flingListener.onNestedPreScroll(topHeight, scrollY, dy);
    }

    @Override
    public void onStopNestedScroll(int topHeight, int scrollY, int dy) {
        float f = ((float) scrollY) / topHeight;
        setTitleByAnim(f);
        setTopViewAnim(f);
        if (flingListener != null)
            flingListener.onStopNestedScroll(topHeight, scrollY, dy);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (flingListener != null)
            flingListener.onRefresh();
    }

    public void setFlingListener(C_StickyFlingListener flingListener) {
        this.flingListener = flingListener;
    }
}
