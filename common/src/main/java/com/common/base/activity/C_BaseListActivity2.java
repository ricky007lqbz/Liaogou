package com.common.base.activity;

import android.util.SparseArray;
import android.view.View;

import com.common.Common;
import com.common.app.C_DefaultRes;
import com.common.base.adapter.C_BaseHolderHelper;
import com.common.base.adapter.C_HeadAndFootMultiRecyclerAdapter;
import com.common.base.adapter.C_OnItemClickListener;
import com.common.base.delegate.C_BaseRecycleViewDelegate;
import com.common.base.listener.C_RefreshAndLoadMoreCallBack;
import com.common.base.model.C_BaseListModel;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;
import com.common.net.C_OnNetRequestListener;
import com.common.util.C_ArrayUtil;
import com.common.util.C_LoadMoreViewUtils;
import com.common.widget.C_LoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ricky on 2016/05/24.
 * <p/>
 * 基于RecycleView和activity的presenter基类
 *
 * @param <T> 网络返回的列表项对应原始数据类型
 * @param <P> view所需要的数据类型
 * @param <Q> 需要显示的View (extends C_BaseRecycleViewDelegate)
 */
public abstract class C_BaseListActivity2<T, P extends C_BaseBean, Q extends C_BaseRecycleViewDelegate>
        extends C_BaseSingleActivity<Q> implements C_RefreshAndLoadMoreCallBack, C_OnNetRequestListener<C_DataWrapper<List<P>>> {

    protected C_BaseListModel<T, P> mModel;
    protected C_HeadAndFootMultiRecyclerAdapter<P> mAdapter;
    /*当前页*/
    protected int mPageNum;
    /*显示的数据*/
    protected List<P> mData;
    /*是否下拉刷新*/
    protected boolean mIsRefresh;
    /*是否添加pageSize和pageNo 默认添加*/
    protected boolean mIsAddPage = true;
    /*是否是首次请求数据*/
    protected boolean isFirstTimeRequest = true;

    /**
     * 获得基本的（带加载下一页）model
     *
     * @return C_BaseDataModel
     */
    protected abstract C_BaseListModel<T, P> getModel();

    /**
     * 给空的viewHolder控制类数组添加数据
     *
     * @param helpers viewHolder控制类数组
     */
    protected abstract void putHolderHelps(SparseArray<C_BaseHolderHelper<P>> helpers);

    /**
     * 设置recyclerView的item的点击事件
     *
     * @return 点击事件
     */
    protected C_OnItemClickListener getOnItemClickListener() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mModel = getModel();

        mAdapter = getRecycleAdapter();
        if (getRecycleAdapter() == null) {
            mAdapter = new C_HeadAndFootMultiRecyclerAdapter<>(this, mData);
        }
        //添加viewHolder的控制类数组
        SparseArray<C_BaseHolderHelper<P>> helpers = new SparseArray<>();
        putHolderHelps(helpers);
        mAdapter.setHolderHelpers(helpers);
        //设置监听事件
        mAdapter.setOnItemClickListener(getOnItemClickListener());

        mDelegateView.setListAdapter(mAdapter);
        //设置是否禁用下拉刷新功能（一般终端使用）
        mDelegateView.setIsForbidRefresh(isForbidPullDownRefresh());
        //设置是否禁用上拉加载更多功能（一般终端使用）
        mDelegateView.setIsForbidLoadMore(isForbidPullUpLoadMore());
        //注册下拉刷新和加载更多
        mDelegateView.registerRefreshAndLoadMoreCallBack(this, mAdapter, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMore(false);
            }
        });

        if (!isForbidFirstAutoRefresh() && !mDelegateView.isShowEmpty()) {
            refresh();
        }
    }

    /**
     * 是否禁用第一次自动刷新
     */
    protected boolean isForbidFirstAutoRefresh() {
        return false;
    }

    /**
     * 是否禁用下拉刷新
     */
    protected boolean isForbidPullDownRefresh() {
        return false;
    }

    /**
     * 是否禁用加载更多
     */
    protected boolean isForbidPullUpLoadMore() {
        return false;
    }

    /**
     * 指定扩展的adapter
     *
     * @return C_MultiRecycleAdapter adapter
     */
    public C_HeadAndFootMultiRecyclerAdapter<P> getRecycleAdapter() {
        return null;
    }

    /**
     * 获取的每页加载的数量，默认20
     */
    protected int getPageSize() {
        return Common.net.PAGE_SIZE;
    }

    /**
     * 网络请求主方法
     *
     * @param isRefresh 是否刷新
     */
    protected void onRequest(final boolean isRefresh) {
        this.mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 0;
        }
        List<String> listParams = getListParams();
        Map<String, String> mapParams = getMapParams();
        if (listParams != null) {
            if (mIsAddPage) {
                listParams.add(String.valueOf(mPageNum));
                listParams.add(String.valueOf(getPageSize()));
            }
        } else {
            if (mapParams == null) {
                mapParams = new HashMap<>();
            }
            if (mIsAddPage) {
                mapParams.put("pageNo", String.valueOf(mPageNum));
                mapParams.put("pageSize", String.valueOf(getPageSize()));
            }
        }
        if (mModel != null) {
            if (listParams == null) {
                mModel.requestData(mapParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            } else {
                mModel.requestData(listParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            }
        }
    }

    /**
     * v2对应的params（会会看其是否为空，然后再调用常规的map类型的params）
     */
    protected List<String> getListParams() {
        return null;
    }

    /**
     * 一般类型的params参数
     */
    protected Map<String, String> getMapParams() {
        return null;
    }

    /**
     * 空视图的图片资源
     *
     * @return R.drawable.xx
     */
    protected int getEmptyImgResId() {
        return C_DefaultRes.getInstance().getDfEmptyImgRes();
    }

    /**
     * 空视图的文字
     *
     * @return "xxx"
     */
    protected String getEmptyMsg() {
        return C_DefaultRes.getInstance().getDfEmptyMsg();
    }

    /**
     * 空视图底部按钮文字
     *
     * @return "xxx"
     */
    protected String getEmptyBtnText() {
        return null;
    }

    /**
     * 空视图的底部按钮点击事件
     *
     * @return "xxx"
     */
    protected View.OnClickListener getEmptyBtnClickListener() {
        return null;
    }

    /**
     * 空视图图片点击事件
     *
     * @return "xxx"
     */
    protected View.OnClickListener getEmptyIvClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequest(true);
            }
        };
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        //清空所有请求
//        C_HttpRequest.getInstance().clearAllRequestQueue();
//    }

    @Override
    public void refresh() {
        onRequest(true);
    }

    @Override
    public void loadMore(boolean mIsRefresh) {
        if (!isForbidPullUpLoadMore()) {
            onRequest(false);
        }
    }

    @Override
    public void onRequestStart(int what) {
        if (lifeCycleListener != null) {
            lifeCycleListener.onRequestStart(this, true, isFirstTimeRequest);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (lifeCycleListener != null) {
            lifeCycleListener.onRequestFinish(this, true, isFirstTimeRequest);
        }
        isFirstTimeRequest = false;
        mDelegateView.onRefreshComplete();
        if (mDelegateView.isShowContent() && C_ArrayUtil.isEmpty(mData)) {
            mDelegateView.showEmpty(getEmptyImgResId(),
                    getEmptyMsg(), getEmptyBtnText(),
                    getEmptyIvClickListener(), getEmptyBtnClickListener());
        }
    }

    //是否是按组排序的数据
    public boolean isGroupData() {
        return false;
    }

    @Override
    public void onRequestSuccess(int what, C_DataWrapper<List<P>> data, final boolean isRefresh) {
        if (data == null) {
            return;
        }
        mDelegateView.showContent();
        if (isRefresh) {
            //刷新重新打开加载更多
            C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.LOADING);
            mData.clear();
        }
        if (!C_ArrayUtil.isEmpty(data.getData())) {
            //添加下一页数据
            if (isAddNextPageDataToTop()) {
                mData.addAll(0, data.getData());
            } else {
                //把原来最底的位置设置中间
                if (mData.size() > 0 && !isGroupData()
                        && data.getData().get(0).get_position_type() != Common.position_type.TOP) {
                    mData.get(mData.size() - 1).set_position_type(Common.position_type.CENTER);
                }
                mData.addAll(data.getData());
            }
            //数据返回成功后才增加pageNo
            if (what == Common.net.SINGLE_QUEUE) {
                C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.LOADING);
                mPageNum++;
                autoLoadNextPage();
            }
        } else {
            if (what == Common.net.SINGLE_QUEUE) {
                C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.END);
            } else {
                C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.NORMAL);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //自动加载下一页的测次数(超过5次将不再继续请求）
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
                    if (autoLoadTime < 5) {
                        loadMore(false);
                    } else {
                        C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.END);
                    }
                }
            }
        }, 100);
    }

    /*是否添加下一页数据到头部*/
    protected boolean isAddNextPageDataToTop() {
        return false;
    }

    /**
     * 清空数据，并通知视图更新
     */
    protected void clearListDataAndNotify() {
        if (!C_ArrayUtil.isEmpty(mData)) {
            mData.clear();
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 错误视图的图片资源
     *
     * @return R.drawable.xx
     */
    protected int getErrorImgResId() {
        return C_DefaultRes.getInstance().getDfErrorImgRes();
    }

    /**
     * 错误视图的文字
     *
     * @return "xxx"
     */
    protected String getErrorMsg() {
        return C_DefaultRes.getInstance().getDfErrorMsg();
    }

    @Override
    public void onRequestFailed(int what, final Throwable t, String url) {
        mDelegateView.onRefreshComplete();
        if (!mIsRefresh) {
            C_LoadMoreViewUtils.setLoadMoreState(mAdapter, C_LoadMoreView.State.ERROR);
        } else {
            mDelegateView.showError(getErrorImgResId(), getErrorMsg(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRequest(true);
                }
            });
        }
    }
}
