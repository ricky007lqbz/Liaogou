package com.common.base.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.common.base.delegate.C_PtrTwRecycleViewDelegate;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;
import com.common.util.C_LoadMoreViewUtils;
import com.common.widget.C_LoadMoreView;

import java.util.List;

/**
 * Created by Ziwu on 2016/9/19.
 *
 * @param <RData>  server 原始数据类型
 * @param <UIData> client UI对应的数据类型
 * @param <RV>     UI 抽象代理
 */
public abstract class C_BaseTwListActivity<RData, UIData extends C_BaseBean, RV extends C_PtrTwRecycleViewDelegate>
        extends C_BaseListActivity<RData, UIData, RV> {

    private int prePosition = 0;

    @Override
    protected Class getDelegateClass() {
        return C_PtrTwRecycleViewDelegate.class;
    }

    @Override
    public void loadMore(boolean isLoadPre) {
        if (mModel != null)
            if (isLoadPre) {
                if (mData != null && mData.size() > 1)
                    mModel.onPrePage(mData.get(1));
            } else {
                if (mData != null && mData.size() > 0)
                    mModel.onNextPage(mData.get(mData.size() - 1));
            }
        onRequest(isLoadPre);
    }


    @Override
    public void onRequestSuccess(int what, C_DataWrapper<List<UIData>> data, boolean isPrePage) {
        mDelegateView.showContent();
        if (isPrePage) {
            mData.addAll(0, data.getData());
            prePosition = data.getData().size();
        } else {
            mData.addAll(data.getData());
        }
        /* 加载更多没有数据，就当作没有更多数据了吧 ！ */
        if (data.getData().size() == 0) {
            C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.END);
        } else {
            C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.LOADING);
        }
        mAdapter.notifyDataSetChanged();
        if (isPrePage) {
            scrollToPosition();
        }
    }

    /**
     * 往前翻页定位
     */
    private void scrollToPosition() {
        if (mDelegateView instanceof C_PtrTwRecycleViewDelegate) {
            LinearLayoutManager manager = (LinearLayoutManager) mDelegateView.getLayoutManager();
            int first = manager.findFirstVisibleItemPosition();
            int last = manager.findLastVisibleItemPosition();
            manager.scrollToPosition(prePosition + last - first - 1);
        }
    }

}
