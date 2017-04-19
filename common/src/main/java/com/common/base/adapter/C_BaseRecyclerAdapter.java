package com.common.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.common.base.listener.C_OnItemCountChangeListener;
import com.common.util.C_ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2016/05/17.
 * <p/>
 * 基本的多种类型的RecycleViewAdapter
 * <p/>
 * <p/>
 * StickyRecyclerHeadersAdapter 固定头部接口实现
 */
public abstract class C_BaseRecyclerAdapter<T, P extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<P> {

    protected C_OnItemClickListener mListener;
    protected C_OnItemCountChangeListener mItemCountChangeListener;

    protected Context mContext;
    protected List<T> mData;

    public C_BaseRecyclerAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public C_BaseRecyclerAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    /**
     * 绑定数据
     *
     * @param position 位置
     * @param holder   holder
     * @param item     数据
     */
    public abstract void convert(int position, P holder, T item);

    /**
     * 根据viewType返回不同的layoutId
     *
     * @param viewType item类型
     * @return layoutId
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 根据position和数据返回不同的viewType
     *
     * @param position 位置
     * @param item     数据
     * @return viewType
     */
    public abstract int getItemViewType(int position, T item);

    /**
     * 获取item的点击事件
     *
     * @return C_OnItemClickListener
     */
    public abstract C_OnItemClickListener getOnItemClickListener();

    public void setData(List<T> data) {
        if (this.mData == null) {
            this.mData = data;
        } else {
            this.mData.clear();
            this.mData.addAll(data);
        }
    }

    public void setOnItemCountChangeListener(C_OnItemCountChangeListener itemCountChangeListener) {
        this.mItemCountChangeListener = itemCountChangeListener;
    }

    /**
     * 在position位置插入item
     */
    public void onItemInsert(int position, T item) {
        if (position >= 0 && position < getItemCount()) {
            mData.add(position, item);
            notifyItemInserted(position);
            if (mItemCountChangeListener != null) {
                mItemCountChangeListener.onItemCountChanged(getItemCount());
            }
        }
    }

    /**
     * 在position位置重置item
     */
    public void onItemSet(int position, T item) {
        if (position >= 0 && position < getItemCount()) {
            mData.set(position, item);
            notifyItemChanged(position);
        }
    }

    /**
     * 在列表的 尾部添加 item
     */
    public void onItemAdd(T item) {
        mData.add(item);
        notifyDataSetChanged();
        if (mItemCountChangeListener != null) {
            mItemCountChangeListener.onItemCountChanged(getItemCount());
        }
    }

    /**
     * 移除列表中的某一项
     */
    public void onItemRemove(int position) {
        if (position >=0 && position < getItemCount()) {
            mData.remove(position);
            notifyDataSetChanged();
            if (mItemCountChangeListener != null) {
                mItemCountChangeListener.onItemCountChanged(getItemCount());
            }
        }
    }

    public List<T> getData() {
        return mData;
    }

    public T getItemData(int position) {
        return C_ArrayUtil.getItem(mData, position);
    }

    //如果recycleView有divider,会多一个divider;
    @Override
    public void onBindViewHolder(P holder, int position) {
        convert(position, holder, mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return C_ArrayUtil.isEmpty(mData) ? 0 : mData.size();
    }

    public void setOnItemClickListener(C_OnItemClickListener listener) {
        this.mListener = listener;
    }
}
