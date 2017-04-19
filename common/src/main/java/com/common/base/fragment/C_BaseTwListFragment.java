package com.common.base.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.common.Common;
import com.common.base.delegate.C_PtrTwRecycleViewDelegate;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;
import com.common.util.C_ArrayUtil;
import com.common.util.C_LoadMoreViewUtils;
import com.common.widget.C_LoadMoreView;

import java.util.List;

/**
 * Created by Ziwu on 2016/8/29.
 * <p/>
 * 双向加载
 *
 * @param <RData>  server 原始数据类型
 * @param <UIData> client UI对应的数据类型
 */
public abstract class C_BaseTwListFragment<RData, UIData extends C_BaseBean>
        extends C_BaseListFragment<RData, UIData, C_PtrTwRecycleViewDelegate> {

    protected boolean isRefresh; //是否刷新（只限于双向刷新使用）

    @Override
    protected Class<C_PtrTwRecycleViewDelegate> getDelegateClass() {
        return C_PtrTwRecycleViewDelegate.class;
    }

    public void refreshWholeData() {
        if (mModel != null) {
            mModel.initData();
        }
        this.isRefresh = true;
        onRequest(true);
    }

    @Override
    public void refresh() {
        if (isFirstTimeRequest) {
            refreshWholeData();
        } else {
            loadPre();
        }
    }

    /**
     * 获取加载前一页需要跳过的头部项数目
     */
    protected int getLoadPreHeadCount() {
        return 0; //默认为0
    }

    public void loadPre() {
        if (mModel != null) {
            if (mData != null && mData.size() > getLoadPreHeadCount()) {
                mModel.onPrePage(mData.get(getLoadPreHeadCount()));
            } else {
                mModel.onPrePage(null);
            }
        }
        this.isRefresh = false;
        onRequest(true);
    }

    /**
     * 获取加载前一页需要跳过的头部项数目
     */
    protected int getLoadNextFootCount() {
        return 0; //默认为0
    }

    @Override
    public void loadMore(boolean isLoadPre) {
        if (mModel != null) {
            if (mData != null && mData.size() > getLoadNextFootCount()) {
                mModel.onNextPage(mData.get(mData.size() - 1 - getLoadNextFootCount()));
            } else {
                mModel.onNextPage(null);
            }
        }
        this.isRefresh = false;
        onRequest(false);
    }

    @Override
    public void onRequestSuccess(int what, C_DataWrapper<List<UIData>> data, boolean isPrePage) {
        mDelegateView.showContent();
        if (!C_ArrayUtil.isEmpty(data.getData())) {
            if (isRefresh) {
                mData.clear();
            }
            if (isPrePage) {
                //接上一页数据
                if (!C_ArrayUtil.isEmpty(mData)
                        && !isGroupData()
                        && C_ArrayUtil.getLastItem(data.getData()).get_position_type() != Common.position_type.BOTTOM) {
                    for (int i = 0; i < getLoadPreHeadCount(); i++) {
                        mData.remove(0);
                    }
                }
                mData.addAll(0, data.getData());
            } else {
                //把原来最底的位置设置中间
                if (mData.size() > 0 && !isGroupData()
                        && data.getData().get(0).get_position_type() != Common.position_type.TOP) {
                    mData.get(mData.size() - 1).set_position_type(Common.position_type.CENTER);
                }
                mData.addAll(data.getData());
            }
            /* 加载更多没有数据，就当作没有更多数据了吧 ！ */
            C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.LOADING);
            mAdapter.notifyDataSetChanged();
            if (isRefresh) {
                //自动加载前一页
                loadPre();
                scrollToPosition(0);
            } else if (isPrePage) {
                scrollToPosition(data.getData().size());
            }
        }
        autoLoadNextPage();
    }

    //自动加载下一页的测次数(超过3次将不再继续请求）
    private int autoLoadTime = 0;

    /**
     * 自动加载下一页
     */
    private void autoLoadNextPage() {
        //如果请求回来的数据不足一页，则自动请求下一页(需要延迟，否则获取不到最新的最后可见的position）
        mDelegateView.getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDelegateView.getLastVisiblePosition() >= mData.size()) {
                    autoLoadTime++;
                    if (autoLoadTime < 3) {
                        loadMore(false);
                    } else {
                        C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.END);
                    }
                }
            }
        }, 100);
    }

    /**
     * 往前翻页定位
     */
    private void scrollToPosition(int size) {
        if (mDelegateView != null) {
            LinearLayoutManager manager = (LinearLayoutManager) mDelegateView.getLayoutManager();
            manager.scrollToPositionWithOffset(size, 0);
        }
    }
}
