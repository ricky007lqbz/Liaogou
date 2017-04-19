package com.common.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ziwu on 2016/5/23.
 * <p>
 * 下拉刷新容器 ：Container(AbsListView子类/RecycleView+自定义footer) + 自定义Header
 * <p>
 * <p>
 * 引用一个数据加载的model, 事件回调也头部交互相关！
 */
public class C_PtrRecycleViewFrameLayout extends PtrFrameLayout {

    public C_PtrRecycleViewFrameLayout(Context context) {
        super(context);
        initView();
    }

    public C_PtrRecycleViewFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public C_PtrRecycleViewFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setConfig();
        C_PtrRecycleViewHeaderView ptrHeaderView = new C_PtrRecycleViewHeaderView(getContext());
        setHeaderView(ptrHeaderView);
        addPtrUIHandler(ptrHeaderView);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 通用配置
     */
    public void setConfig() {
        // the following are default settings
        setResistance(2.0f);
        setRatioOfHeaderHeightToRefresh(1.1f);
        setDurationToClose(200);
        setDurationToCloseHeader(1000);
        // default is false
        setPullToRefresh(false);
        // default is true
        setKeepHeaderWhenRefresh(true);
    }
}
