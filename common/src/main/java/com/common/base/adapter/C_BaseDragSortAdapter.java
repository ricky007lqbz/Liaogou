package com.common.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.common.R;
import com.common.base.listener.C_ItemMoveOrDeleteListener;
import com.common.base.listener.C_SimpleItemTouchHelperCallback;
import com.common.bean.C_BaseBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ricky on 2016/09/19.
 * <p/>
 * 发帖 可以拖动的adapter
 */
public abstract class C_BaseDragSortAdapter<T extends C_BaseBean> extends C_BaseRecyclerAdapter<T, C_BaseDragSortHolder>
        implements C_ItemMoveOrDeleteListener {

    private C_ItemMoveOrDeleteListener C_ItemMoveOrDeleteListener;
    protected ItemTouchHelper touchHelper;
    private Animation rightOutAnim;

    public C_BaseDragSortAdapter(RecyclerView recyclerView) {
        this(recyclerView, new ArrayList<T>());
    }

    public C_BaseDragSortAdapter(RecyclerView recyclerView, List<T> data) {
        this(recyclerView, data, true);
    }

    public C_BaseDragSortAdapter(RecyclerView recyclerView, List<T> data, boolean isCanSipe) {
        super(recyclerView.getContext(), data);
        ItemTouchHelper.Callback callback = new C_SimpleItemTouchHelperCallback(this, isCanSipe);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public C_BaseDragSortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final C_BaseDragSortHolder holder;
        holder = C_BaseDragSortHolder.get(mContext, parent, getLayoutId(viewType), viewType);
        if (mListener != null) {
            holder.setOnItemClickListener(mListener);
        } else {
            holder.setOnItemClickListener(getOnItemClickListener());
        }
        View dragView = holder.getView(R.id.id_drag_handle);
        if (holder.isCanDrag() && dragView != null) {
            dragView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (view.getId() == R.id.id_drag_handle) {
                        touchHelper.startDrag(holder);
                        return true;
                    }
                    return false;
                }
            });
        }
        View removeView = holder.getView(R.id.id_click_remove);
        if (holder.isCanRemove() && removeView != null) {
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickDismiss(holder);
                }
            }, holder.getView(R.id.id_click_remove));
        }
        return holder;
    }

    /**
     * 点击 删除item事件
     */
    private void onItemClickDismiss(final C_BaseDragSortHolder holder) {
        if (rightOutAnim == null) {
            rightOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.c_right_out);
        }
        rightOutAnim.cancel();
        rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onItemDismiss(holder, holder.getAdapterPosition());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        holder.getConvertView().startAnimation(rightOutAnim);
    }

    public void setC_ItemMoveOrDeleteListener(C_ItemMoveOrDeleteListener c_ItemMoveOrDeleteListener) {
        this.C_ItemMoveOrDeleteListener = c_ItemMoveOrDeleteListener;
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder holder, int from, int to) {
        if (C_ItemMoveOrDeleteListener != null) {
            C_ItemMoveOrDeleteListener.onItemMove(holder, from, to);
        }
        if (holder instanceof C_BaseDragSortHolder) {
            if (((C_BaseDragSortHolder) holder).isCanDrag()) {
                Collections.swap(mData, from, to);
                notifyItemMoved(from, to);
            }
        } else {
            Collections.swap(mData, from, to);
            notifyItemMoved(from, to);
        }
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder holder, int position) {
        if (C_ItemMoveOrDeleteListener != null) {
            C_ItemMoveOrDeleteListener.onItemDismiss(holder, position);
        }
        if (holder instanceof C_BaseDragSortHolder) {
            if (((C_BaseDragSortHolder) holder).isCanRemove()) {
                onItemRemove(position);
            }
        } else {
            onItemRemove(position);
        }
    }
}