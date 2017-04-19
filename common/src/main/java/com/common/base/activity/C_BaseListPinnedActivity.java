package com.common.base.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.base.adapter.C_BasePinnedHolderHelper;
import com.common.base.adapter.C_PinnedHeadAndFootMultiRecyclerAdapter;
import com.common.base.delegate.C_BaseRecycleViewDelegate;
import com.common.base.listener.C_StickyRecyclerHeadersCustomTouchListener;
import com.common.bean.C_BaseBean;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * Created by ricky on 2016/08/31.
 * * <p>
 * 装载了list的activity(自带ptr下拉刷新）
 * <p/>
 * T 原始数据(集合) C_Wrapper 包裹的数据
 * <p/>
 * P  UI 对应绑定的数据类型
 */
public abstract class C_BaseListPinnedActivity<T, P extends C_BaseBean, Q extends C_BaseRecycleViewDelegate>
        extends C_BaseListActivity<T, P, Q> {

    @Override
    public C_PinnedHeadAndFootMultiRecyclerAdapter<P> getRecycleAdapter() {
        return new C_PinnedHeadAndFootMultiRecyclerAdapter<>(this, mData);
    }

    @Override
    protected void initData() {
        super.initData();
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) mAdapter);
        mDelegateView.getRecyclerView().addItemDecoration(headersDecor);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
        C_StickyRecyclerHeadersCustomTouchListener touchListener =
                new C_StickyRecyclerHeadersCustomTouchListener(mDelegateView.getRecyclerView(), headersDecor);
        touchListener.setOnHeaderClickListener(new C_StickyRecyclerHeadersCustomTouchListener.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View header, int position, long headerId) {
                C_BasePinnedHolderHelper<P> pinnedHelper = ((C_PinnedHeadAndFootMultiRecyclerAdapter<P>) mAdapter).getPinnedHelper(headerId);
                if (pinnedHelper != null) {
                    pinnedHelper.onStickyHeadersOnclick(header, position, headerId, mData.get(position));
                }
            }
        });
        mDelegateView.getRecyclerView().addOnItemTouchListener(touchListener);
    }
}
