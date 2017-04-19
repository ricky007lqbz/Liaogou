package com.common.base.adapter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.common.bean.C_BaseBean;
import com.common.util.C_ArrayUtil;
import com.common.widget.C_LoadMoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2016/08/18.
 * <p>
 * 基本的多种类型的RecycleViewAdapter,可以添加headView和FooterView
 */
public class C_HeadAndFootMultiRecyclerAdapter<T extends C_BaseBean> extends C_MultiRecycleAdapter<T> {
    //加载更多的索引
    private static final int VIEW_TYPE_LOAD_MORE = Integer.MIN_VALUE;
    //头部view的view_type(第一个索引）
    private static final int VIEW_TYPE_HEADER = Integer.MIN_VALUE + 1;
    //底部view的view_type
    private static final int VIEW_TYPE_FOOTER = Integer.MIN_VALUE + 100;//预留headView100个

    //保存头部view的list
    private ArrayList<View> mHeaderViews;
    //保存底部view的list
    private ArrayList<View> mFooterViews;
    //加载更多视图
    private C_LoadMoreView mLoadMoreView;

    public C_HeadAndFootMultiRecyclerAdapter(Context context) {
        super(context);
        initData();
    }

    public C_HeadAndFootMultiRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
        notifyItemRangeInserted(getHeaderViewsCount(), getNormalViewsCount());
    }

    /**
     * 添加headView
     */
    public void addHeaderView(View view) {
        if (view == null) {
            return;
        }
        mHeaderViews.remove(view);
        mHeaderViews.add(view);
        notifyItemRangeInserted(getHeaderViewsCount(), getItemCount());
    }

    /**
     * 移除对应的headView
     */
    public void removeHeaderView(View view) {
        if (view == null || mHeaderViews == null) {
            return;
        }
        int position = C_ArrayUtil.getPosition(mHeaderViews, view);
        if (position >= 0) {
            notifyItemRangeRemoved(position, getItemCount());
            mHeaderViews.remove(view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加footView
     */
    public void addFooterView(View view) {
        if (view == null) {
            return;
        }
        mFooterViews.remove(view);
        mFooterViews.add(view);
        notifyItemRangeInserted(getHeaderViewsCount() + getNormalViewsCount() + getFooterViewsCount(), getItemCount());
    }

    /**
     * 移除对应的子footView
     */
    public void removeFooterView(View view) {
        if (view == null || mFooterViews == null) {
            return;
        }
        int position = C_ArrayUtil.getPosition(mFooterViews, view);
        if (position >= 0) {
            notifyItemRangeRemoved(getHeaderViewsCount() + getNormalViewsCount() + position, getItemCount());
            mFooterViews.remove(view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加（设置）加载更多的view
     */
    public void setLoadMoreView(C_LoadMoreView view) {
        if (view == null) {
            return;
        }
        this.mLoadMoreView = view;
        this.notifyDataSetChanged();
    }

    /**
     * 获取加载更多的view
     */
    public C_LoadMoreView getLoadMoreView() {
        return mLoadMoreView;
    }

    /**
     * 判断是否有加载更多功能
     */
    public boolean isHasLoadMoreView() {
        return mLoadMoreView != null;
    }

    /**
     * 头部view的数量
     */
    public int getHeaderViewsCount() {
        return C_ArrayUtil.isEmpty(mHeaderViews) ? 0 : mHeaderViews.size();
    }

    /**
     * 底部view的数据
     */
    public int getFooterViewsCount() {
        return C_ArrayUtil.isEmpty(mFooterViews) ? 0 : mFooterViews.size();
    }

    /**
     * 正常view的数量
     */
    public int getNormalViewsCount() {
        return C_ArrayUtil.isEmpty(mData) ? 0 : mData.size();
    }

    @Override
    public int getItemCount() {
        int loadMoreCount = (mLoadMoreView == null ? 0 : 1);
        return getHeaderViewsCount() + getNormalViewsCount() + getFooterViewsCount() + loadMoreCount;
    }

    @Override
    public int getItemViewType(int position) {
        int normalCount = getNormalViewsCount();
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = getFooterViewsCount();
        if (position < headerViewsCount) {
            //头部view
            return VIEW_TYPE_HEADER + position;
        } else if (headerViewsCount <= position && position < headerViewsCount + normalCount) {
            //正常的view
            int normalViewType = super.getItemViewType(position - headerViewsCount);
            if (normalViewType >= Integer.MAX_VALUE / 2) {
                throw new IllegalStateException("this view type of C_HeadAndFootMultiRecyclerAdapter should < Integer.MAX_VALUE / 2");
            }
            return normalViewType + Integer.MAX_VALUE / 2;
        } else {
            //加载更多的索引
            if (position == headerViewsCount + normalCount + footerViewsCount) {
                return VIEW_TYPE_LOAD_MORE;
            } else {
                //底部view
                return VIEW_TYPE_FOOTER + position - headerViewsCount - normalCount;
            }
        }
    }

    @Override
    public C_BaseMultiRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewsCount = getHeaderViewsCount();
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            return C_BaseMultiRecyclerHolder.get(mLoadMoreView, viewType);
        } else if (viewType < VIEW_TYPE_HEADER + headerViewsCount) {
            View view = C_ArrayUtil.getItem(mHeaderViews, viewType - VIEW_TYPE_HEADER);
            if (view != null) {
                return C_BaseMultiRecyclerHolder.get(view, viewType);
            }
        } else if (viewType > VIEW_TYPE_FOOTER
                && viewType < VIEW_TYPE_FOOTER + getFooterViewsCount()) {
            View view = C_ArrayUtil.getItem(mFooterViews, viewType - VIEW_TYPE_FOOTER);
            if (view != null) {
                return C_BaseMultiRecyclerHolder.get(view, viewType);
            }
        } else {
            return super.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(C_BaseMultiRecyclerHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();
        int normalCount = getNormalViewsCount();
        if (position >= headerViewsCountCount && position < headerViewsCountCount + normalCount) {
            super.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.getConvertView().getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    /**
     * 根据位置片段是否是headView
     */
    public boolean isHeadView(int position) {
        return getHeaderViewsCount() > 0 && position < getHeaderViewsCount();
    }

    /**
     * 根据位置片段是否是footView
     */
    public boolean isFootView(int position) {
        return (getFooterViewsCount() > 0 || isHasLoadMoreView()) && position >= getHeaderViewsCount() + getNormalViewsCount();
    }

    public C_LoadMoreView onLoadMoreViewCreate() {
        return new C_LoadMoreView(mContext);
    }

    @Override
    public void onViewRecycled(C_BaseMultiRecyclerHolder holder) {
        super.onViewRecycled(holder);
    }
}
