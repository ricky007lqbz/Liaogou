package com.common.base.delegate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.R;
import com.common.base.adapter.C_BaseMultiRecyclerAdapter;
import com.common.base.adapter.C_HeadAndFootMultiRecyclerAdapter;
import com.common.base.decoration.C_DividerGridItemDecoration;
import com.common.util.C_ResUtil;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridBottomOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridTopOffsetItemDecoration;

/**
 * Created by Ziwu on 2016/5/25.
 * <p>
 * PtrFrameLayout  + RecycleView  实现 刷新/加载更多
 */
public class C_PtrGridRecycleViewDelegate extends C_PtrRecycleViewDelegate {

    private int columns = 2;

    private GridLayoutManager layoutManager;

    protected int getSpanCount() {
        if (layoutManager != null) {
            return layoutManager.getSpanCount();
        }
        return 0;
    }

    public void addItemDividerDecoration(C_HeadAndFootMultiRecyclerAdapter adapter, boolean isShowLastBottomDivider) {
        addItemDividerDecoration(R.drawable.c_divider_decoration, adapter, isShowLastBottomDivider);
    }

    public void addItemDividerDecoration(C_HeadAndFootMultiRecyclerAdapter adapter, int dividerId, boolean isShowLastBottomDivider) {
        addItemDividerDecoration(dividerId, adapter, isShowLastBottomDivider);
    }

    /**
     * item 之间的decoration  8dp
     */
    public void addItemDividerDecoration(int drawableId, C_HeadAndFootMultiRecyclerAdapter adapter, boolean isShowLastBottomDivider) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            rv.addItemDecoration(
                    new C_DividerGridItemDecoration(
                            getContext(),
                            drawableId,
                            adapter.getHeaderViewsCount(),
                            adapter.getFooterViewsCount(),
                            isShowLastBottomDivider));
        }
    }

    /**
     * 顶部 decoration  8dp
     */
    @Override
    public void addStartOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            RecyclerView.ItemDecoration mStartOffsetItemDecoration = new GridTopOffsetItemDecoration(
                    C_ResUtil.getDimens(dimens),
                    getSpanCount());
            rv.addItemDecoration(mStartOffsetItemDecoration);
        }
    }

    /**
     * 底部 decoration  8dp
     */
    @Override
    public void addEndOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            RecyclerView.ItemDecoration mEndOffsetItemDecoration = new GridBottomOffsetItemDecoration(
                    C_ResUtil.getDimens(dimens),
                    getSpanCount());
            rv.addItemDecoration(mEndOffsetItemDecoration);
        }
    }

    public void setSpanCount(int spanCount) {
        if (layoutManager != null) {
            layoutManager.setSpanCount(spanCount);
        }
    }

    @Override
    protected void initData() {
        layoutManager = new GridLayoutManager(getContext(), columns); //默认2列
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new GridLayoutManager(getContext(), columns);
        }
        return layoutManager;
    }

    @Override
    public int getLastVisiblePosition() {
        return layoutManager == null ? 0 : layoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getFirstVisiblePosition() {
        return layoutManager == null ? 0 : layoutManager.findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public <T> void setListAdapter(C_BaseMultiRecyclerAdapter<T> adapter) {
        super.setListAdapter(adapter);
        if (layoutManager != null) {
            layoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((C_HeadAndFootMultiRecyclerAdapter) adapter, layoutManager.getSpanCount()));
        }
    }

    /**
     * RecyclerView为GridLayoutManager时，设置了HeaderView，就会用到这个SpanSizeLookup
     */
    public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private C_HeadAndFootMultiRecyclerAdapter adapter;
        private int mSpanSize = 1;

        public HeaderSpanSizeLookup(C_HeadAndFootMultiRecyclerAdapter adapter, int spanSize) {
            this.adapter = adapter;
            this.mSpanSize = spanSize;
        }

        @Override
        public int getSpanSize(int position) {
            boolean isHeaderOrFooter = adapter.isHeadView(position) || adapter.isFootView(position);
            return isHeaderOrFooter ? mSpanSize : 1;
        }
    }
}
