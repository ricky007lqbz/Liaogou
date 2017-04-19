package com.common.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.common.Common;
import com.common.R;
import com.common.bean.C_BaseBean;
import com.common.util.C_ArrayUtil;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

/**
 * Created by ricky on 2016/09/11.
 * <p>
 * 带有挂定项的 头部和底部 加载 的adapter
 */
public class C_PinnedHeadAndFootMultiRecyclerAdapter<T extends C_BaseBean> extends C_HeadAndFootMultiRecyclerAdapter<T>
        implements StickyRecyclerHeadersAdapter {

    protected SparseArray<C_BasePinnedHolderHelper<T>> pinnedHHelpers = new SparseArray<>();
    protected SparseArray<C_BaseMultiRecyclerHolder> pinnedHolders = new SparseArray<>();

    public C_PinnedHeadAndFootMultiRecyclerAdapter(Context context) {
        super(context);
    }

    public C_PinnedHeadAndFootMultiRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public void setHolderHelpers(SparseArray<C_BaseHolderHelper<T>> helpers) {
        if (mHelpers == null) {
            mHelpers = new SparseArray<>();
        } else {
            mHelpers.clear();
        }
        int key;
        int size = helpers.size();
        for (int i = 0; i < size; i++) {
            key = helpers.keyAt(i);
            C_BaseHolderHelper<T> helper = helpers.get(key);
            if (helper instanceof C_BasePinnedHolderHelper) {
                pinnedHHelpers.put(key, (C_BasePinnedHolderHelper<T>) helper);
            } else {
                mHelpers.put(key, helper);
            }
        }
    }

    public C_BasePinnedHolderHelper<T> getPinnedHelper(long headId) {
        if (pinnedHHelpers != null) {
            return pinnedHHelpers.get((int) headId);
        }
        return null;
    }

    @Override
    public void convert(int position, C_BaseMultiRecyclerHolder holder, T item) {
        int viewType = item.get_view_type();
        if (viewType == Common.view_type.PINNED
                || mHelpers.get(viewType) == null
                || mHelpers.get(viewType).getLayoutId() <= 0) {
            holder.getConvertView().getLayoutParams().height = 0;
        } else {
            mHelpers.get(viewType).convert(position, holder, item);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == Common.view_type.PINNED
                || mHelpers.get(viewType) == null
                || mHelpers.get(viewType).getLayoutId() <= 0) {
            return R.layout.c_list_item_empty;
        }
        return mHelpers.get(viewType).getLayoutId();
    }

    /************************************** StickyRecyclerHeadersAdapter *************************************/
    /************************************** StickyRecyclerHeadersAdapter *************************************/
    /**
     * pinned header adapter
     * <p/>
     * StickyRecyclerHeadersAdapter 固定头部接口实现
     */
    /**
     * 为什么是0，因为默认值就是0,不赋值就符合条件了！
     */
    public static final int ILLEGAL_HEADER_ID = -1;

    @Override
    public long getHeaderId(int position) {
        int headId;
        if (position == -1) {
            headId = ILLEGAL_HEADER_ID;
        } else {
            T t = C_ArrayUtil.getItem(mData, position);
            if (t != null) {
                headId = t.getRelativeHeaderId();
            } else {
                headId = ILLEGAL_HEADER_ID;
            }
        }
        currentHeadId = headId;
        return headId;
    }

    private int currentHeadId = 0;

    /**
     * 固定头部ViewHolder;
     * <p>
     * 实际上用作 Item Decoration;
     *
     * @param parent the view to create a header view holder for
     */
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        if (pinnedHolders.get(currentHeadId) != null) {
            return pinnedHolders.get(currentHeadId);
        }
        C_BasePinnedHolderHelper<T> helper = pinnedHHelpers.get(currentHeadId);
        if (helper != null && helper.getLayoutId() > 0) {
            C_BaseMultiRecyclerHolder holder = C_BaseMultiRecyclerHolder.get(mContext, parent, helper.getLayoutId(), Common.view_type.PINNED);
            helper.initHolder(holder);
            pinnedHolders.put(currentHeadId, holder);
            return holder;
        }
        return null;
    }

    /**
     * 固定头部绑定数据
     *
     * @param holder   the view holder
     * @param position the adapter position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        T t = C_ArrayUtil.getItem(mData, position);
        if (t != null && t.get_view_type() == Common.view_type.PINNED) {
            C_BasePinnedHolderHelper<T> helper = pinnedHHelpers.get(currentHeadId);
            if (helper != null) {
                helper.convert(position, (C_BaseMultiRecyclerHolder) holder, t);
            }
        }
    }

    /************************************** StickyRecyclerHeadersAdapter *************************************/
    /************************************** StickyRecyclerHeadersAdapter *************************************/
}
