package com.common.base.delegate;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.common.base.adapter.C_BaseMultiRecyclerAdapter;
import com.common.base.adapter.C_HeadAndFootMultiRecyclerAdapter;
import com.common.base.listener.C_BaseLoadingView;
import com.common.base.listener.C_PullTouchListener;
import com.common.base.listener.C_RefreshAndLoadMoreCallBack;
import com.common.base.listener.C_TopPullCallBack;
import com.common.util.C_HeadAndFootViewUtils;
import com.common.util.C_L;
import com.common.util.C_LoadMoreViewUtils;
import com.common.widget.C_LoadMoreView;
import com.common.widget.C_ProgressLayout;
import com.common.widget.ptr.C_PtrRecycleViewFrameLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ricky on 2016/05/24.
 * <p>
 * 基于recycleView的基本代理类
 * <p>
 * 只用于 RecycleView 相关的presenter ;
 */
public abstract class C_BaseRecycleViewDelegate extends BaseViewDelegate implements C_BaseLoadingView {

    protected boolean isForbidRefresh = false; //关闭下拉刷新
    protected boolean isForbidLoadMore = false; //禁用上拉加载更多
    protected boolean isLoadMoreEnable = true; //是否可以上拉加载更多

    private OnScrollStateChangeListener stateChangeListener;

    @Override
    public void initWidget() {
        initView();
        initData();
        super.initWidget();
        getRecyclerView().setLayoutManager(getLayoutManager());
    }

    /**
     * 设置触摸监听事件
     */
    public void setPullCallBack(int duration, C_TopPullCallBack pullCallBack) {
        if (getRecyclerView() == null) {
            return;
        }
        getRecyclerView().setOnTouchListener(new C_PullTouchListener(duration, pullCallBack));
    }

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //获取recycleView
    public abstract RecyclerView getRecyclerView();

    //获取recycleView所需要的layoutManager
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    //获取最后可见的item的position
    public abstract int getLastVisiblePosition();

    //获取最开始可见的item的position
    public abstract int getFirstVisiblePosition();

    //获取Ptr刷新layout(默认用ptr，可以替换，自己重写相应方法）
    protected abstract C_PtrRecycleViewFrameLayout getPrtFrameLayout();

    //获取装载加载错误等视图的layout
    protected abstract C_ProgressLayout getProgressLayout();

    //获取装载recycleView外部头部的layout
    public abstract LinearLayout getOutBottomView();

    //获取装载recycleView外部底部的layout
    public abstract LinearLayout getOutTopView();

    //设置是否禁用下拉刷新功能（需要在注册刷新之前调用）
    public void setIsForbidRefresh(boolean isForbidRefresh) {
        this.isForbidRefresh = isForbidRefresh;
    }

    //设置是否禁用下拉刷新功能（需要在注册刷新之前调用）
    public void setIsForbidLoadMore(boolean isForbidLoadMore) {
        this.isForbidLoadMore = isForbidLoadMore;
    }

    //设置是否可以上拉加载跟多功能
    public void setLoadMoreEnable(boolean enable) {
        this.isLoadMoreEnable = enable;
    }

    /**
     * 设置下拉刷新和加载更多接口
     */
    public <T> void registerRefreshAndLoadMoreCallBack(C_RefreshAndLoadMoreCallBack callBack,
                                                       C_BaseMultiRecyclerAdapter<T> adapter,
                                                       View.OnClickListener errorListener) {
        registerRefresh(callBack);
        registerLoadMore(callBack, adapter, errorListener);
    }

    /**
     * 注册下拉刷新
     */
    protected void registerRefresh(final C_RefreshAndLoadMoreCallBack callBack) {
        if (getPrtFrameLayout() != null) {
            getPrtFrameLayout().setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return getFirstVisiblePosition() == 0 && !isForbidRefresh;
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    if (callBack != null)
                        callBack.refresh();
                }
            });
        }
    }

    public void setOnScrollStateChangeListener(OnScrollStateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    /**
     * 注册加载更多
     */
    protected <T> void registerLoadMore(final C_RefreshAndLoadMoreCallBack callBack,
                                        final C_BaseMultiRecyclerAdapter<T> adapter,
                                        View.OnClickListener errorListener) {
        //初始化加载更多功能
        if (adapter instanceof C_HeadAndFootMultiRecyclerAdapter) {
            //初始化加载更多的view
            C_LoadMoreViewUtils.initLoadMoreView((C_HeadAndFootMultiRecyclerAdapter) adapter, isForbidLoadMore, C_LoadMoreView.State.NORMAL, errorListener);
            //添加监听事件
            getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                private int lastVisibleItem;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItem = getLastVisiblePosition();
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (stateChangeListener != null) {
                        stateChangeListener.onScrollStateChanged(recyclerView, newState);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem + 1 == adapter.getItemCount()
                            //判断加载更多是否处在loading状态
                            && C_LoadMoreViewUtils.getLoadMoreState((C_HeadAndFootMultiRecyclerAdapter) adapter) == C_LoadMoreView.State.LOADING) {
                        if (isLoadMoreEnable) {
                            callBack.loadMore(false);
                        } else {
                            C_LoadMoreViewUtils.setLoadMoreState((C_HeadAndFootMultiRecyclerAdapter) adapter, C_LoadMoreView.State.NORMAL);
                        }
                    }
                }
            });
        } else {
            C_L.e("the adapter of C_PtrRecycleViewDelegate should be C_HeadAndFootMultiRecyclerAdapter");
        }
    }

    public <T> void setListAdapter(C_BaseMultiRecyclerAdapter<T> adapter) {
        if (getRecyclerView() != null) {
            getRecyclerView().setAdapter(adapter);
        }
    }

    public void addListHeadView(View view) {
        C_HeadAndFootViewUtils.addHeadView(getRecyclerView(), view);
    }

    public void removeListHeadView(View view) {
        C_HeadAndFootViewUtils.removeHeadView(getRecyclerView(), view);
    }

    public void addListFootView(View view) {
        C_HeadAndFootViewUtils.addFootView(getRecyclerView(), view);
    }

    public void removeListFootView(View view) {
        C_HeadAndFootViewUtils.removeFootView(getRecyclerView(), view);
    }

    /**
     * 添加子View到RecycleView顶部（固定）,只提供添加一个View
     *
     * @param view 子View
     */
    public void addOutTopView(View view) {
        if (getOutTopView() != null) {
            getOutTopView().removeAllViews();
            getOutTopView().addView(view);
        }
    }

    /**
     * 添加子View到RecycleView底部（固定）,只提供添加一个View
     *
     * @param view 子View
     */
    public void addOutBottomView(View view) {
        if (getOutBottomView() != null) {
            getOutBottomView().removeAllViews();
            getOutBottomView().addView(view);
        }
    }

    /**
     * 刷新完成
     */
    public void onRefreshComplete() {
        if (getPrtFrameLayout() != null) {
            getPrtFrameLayout().refreshComplete();
        }
    }

    @Override
    public void showLoading() {
        getProgressLayout().showLoading();
    }

    @Override
    public void showContent() {
        if (getProgressLayout() != null) {
            getProgressLayout().showContent();
        }
    }

    @Override
    public void showError(int imgResId, String msg, View.OnClickListener listener) {
        if (getProgressLayout() != null) {
            getProgressLayout().showError(imgResId, msg, listener);
        }
    }

    @Override
    public void showEmpty(int imgResId, @Nullable String content,
                          @Nullable String btnContent,
                          @Nullable View.OnClickListener onIvClickListener,
                          @Nullable View.OnClickListener onBtnClickListener) {
        if (getProgressLayout() != null) {
            getProgressLayout().showEmpty(imgResId, content, btnContent, onIvClickListener, onBtnClickListener);
        }
    }

    @Override
    public void showOtherEmptyView(View view) {
        if (getProgressLayout() != null) {
            getProgressLayout().showOtherEmpty(view);
        }
    }

    /**
     * 是否正在显示空视图
     */
    public boolean isShowEmpty() {
        return getProgressLayout().isEmpty();
    }

    /**
     * 是否正在显示内容
     */
    public boolean isShowContent() {
        return getProgressLayout().isContent();
    }

    /**
     * 是否正在显示错误视图
     */
    public boolean isShowError() {
        return getProgressLayout().isError();
    }

    /**
     * 滑动状态监听事件
     */
    public interface OnScrollStateChangeListener {

        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }
}
