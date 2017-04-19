package com.common.base.delegate;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.common.R;
import com.common.util.C_ResUtil;
import com.common.widget.C_ProgressLayout;
import com.common.widget.ptr.C_PtrRecycleViewFrameLayout;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.linear.EndOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.linear.StartOffsetItemDecoration;

/**
 * Created by Ziwu on 2016/5/25.
 * <p>
 * PtrFrameLayout  + RecycleView  实现 刷新/加载更多
 */
public class C_PtrRecycleViewDelegate extends C_BaseRecycleViewDelegate {

    protected C_PtrRecycleViewFrameLayout frameLayout;
    protected RecyclerView recyclerView;
    protected C_ProgressLayout progressLayout;
    protected LinearLayout outTopView, outBottomView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void initView() {
        frameLayout = (C_PtrRecycleViewFrameLayout) findViewById(R.id.ptr_frame_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        progressLayout = (C_ProgressLayout) findViewById(R.id.progress_layout);
        outTopView = (LinearLayout) findViewById(R.id.ll_top_out);
        outBottomView = (LinearLayout) findViewById(R.id.ll_bottom_out);
    }

    /**
     * item 之间的decoration  8dp
     */
    public void addItemDividerDecoration() {
        addItemDividerDecoration(R.drawable.c_divider_decoration);
    }

    /**
     * item 之间的decoration  8dp
     */
    public void addItemDividerDecoration(int drawableId) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            Drawable dividerDrawable = ContextCompat.getDrawable(context, drawableId);
            RecyclerView.ItemDecoration mDividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            rv.addItemDecoration(mDividerItemDecoration);
        }
    }

    /**
     * 顶部 decoration  8dp
     */
    public void addStartOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            RecyclerView.ItemDecoration mStartOffsetItemDecoration = new StartOffsetItemDecoration(C_ResUtil.getDimens(dimens));
            rv.addItemDecoration(mStartOffsetItemDecoration);
        }
    }


    /**
     * 底部 decoration  8dp
     */
    public void addEndOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
            RecyclerView.ItemDecoration mEndOffsetItemDecoration = new EndOffsetItemDecoration(C_ResUtil.getDimens(dimens));
            rv.addItemDecoration(mEndOffsetItemDecoration);
        }
    }


    public void addLeftOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
//            RecyclerView.ItemDecoration leftOffsetItemDecoration = new C_LeftOffsetItemDecoration(C_ResUtil.getDimens(dimens));
//            rv.addItemDecoration(leftOffsetItemDecoration);
            rv.setPadding(C_ResUtil.getDimens(dimens), rv.getPaddingTop(), rv.getPaddingRight(), rv.getPaddingBottom());
        }
    }

    public void addRigthOffsetItemDecoration(int dimens) {
        RecyclerView rv = getRecyclerView();
        if (rv != null) {
//            RecyclerView.ItemDecoration rightOffsetItemDecoration = new C_RightOffsetItemDecoration(C_ResUtil.getDimens(dimens));
//            rv.addItemDecoration(rightOffsetItemDecoration);
            rv.setPadding(rv.getPaddingLeft(), rv.getPaddingTop(), C_ResUtil.getDimens(dimens), rv.getPaddingBottom());
        }
    }


    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext());
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
    protected C_PtrRecycleViewFrameLayout getPrtFrameLayout() {
        return frameLayout;
    }

    @Override
    protected C_ProgressLayout getProgressLayout() {
        return progressLayout;
    }

    @Override
    public LinearLayout getOutBottomView() {
        return outBottomView;
    }

    @Override
    public LinearLayout getOutTopView() {
        return outTopView;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.c_layout_ptr_recycle_view;
    }
}
