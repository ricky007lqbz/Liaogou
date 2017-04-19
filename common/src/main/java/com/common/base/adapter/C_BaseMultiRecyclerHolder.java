package com.common.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.common.base.listener.C_OnViewListener;

/**
 * Created by ricky on 2016/05/17.
 * <p>
 * 基本的多类型的RecyclerViewHolder
 */
public class C_BaseMultiRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private int mViewType;
    private T mItem;

    private View.OnClickListener mOnClickListener; //点击事件（包括convertView）
    private View.OnTouchListener mOnTouchListener; //触摸事件
    private TextWatcher mTextWhatcher; //editText文字改变监听事件

    private C_OnViewListener<T> mOnViewListener; //外部回掉的视图时间

    public C_BaseMultiRecyclerHolder(View itemView, int viewType) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
        this.mViewType = viewType;
    }

    public static C_BaseMultiRecyclerHolder get(Context context, ViewGroup parent, int layoutId, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new C_BaseMultiRecyclerHolder(itemView, viewType);
    }

    public static C_BaseMultiRecyclerHolder get(View view, int viewType) {
        return new C_BaseMultiRecyclerHolder(view, viewType);
    }

    public void registerOnViewListener(C_OnViewListener<T> onViewListener) {
        this.mOnViewListener = onViewListener;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setItem(T item) {
        this.mItem = item;
    }

    public void setOnItemClickListener(final C_OnItemClickListener C_OnItemClickListener) {
        if (C_OnItemClickListener == null) {
            return;
        }
        mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_OnItemClickListener.onItemClick(
                        v, C_BaseMultiRecyclerHolder.this.getAdapterPosition(), mViewType);
            }
        });
    }

    /**
     * 设置点击监听事件
     */
    public void setOnClickListener(View... views) {
        if (views != null && views.length > 0) {
            if (mOnClickListener == null) {
                mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnViewListener != null) {
                            mOnViewListener.onClick(
                                    v, C_BaseMultiRecyclerHolder.this.getAdapterPosition(),
                                    C_BaseMultiRecyclerHolder.this, mItem);
                        }
                    }
                };
            }
            for (View view : views) {
                view.setOnClickListener(mOnClickListener);
            }
        }
    }

    /**
     * 设置触摸监听事件
     */
    public void setOnTouchListener(View... views) {
        if (views != null && views.length > 0) {
            if (mOnTouchListener == null) {
                mOnTouchListener = new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mOnViewListener != null) {
                            return mOnViewListener.onTouch(v, event,
                                    C_BaseMultiRecyclerHolder.this.getAdapterPosition(),
                                    C_BaseMultiRecyclerHolder.this, mItem);
                        }
                        return false;
                    }
                };
            }
            for (View view : views) {
                view.setOnTouchListener(mOnTouchListener);
            }
        }
    }

    /**
     * 添加文本改变事件
     */
    public void addOnTextChangeListener(EditText... editTexts) {
        if (editTexts != null && editTexts.length > 0) {
            if (mTextWhatcher == null) {
                mTextWhatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (mOnViewListener != null) {
                            mOnViewListener.onTextChange(
                                    s, C_BaseMultiRecyclerHolder.this.getAdapterPosition(),
                                    C_BaseMultiRecyclerHolder.this, mItem);
                        }
                    }
                };
            }
            for (EditText editText : editTexts) {
                editText.removeTextChangedListener(mTextWhatcher);
                editText.addTextChangedListener(mTextWhatcher);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener listener, View... views) {
        if (listener != null && views != null && views.length > 0) {
            for (View view : views) {
                view.setOnClickListener(listener);
            }
        }
    }

    /**
     * 通过holder的数组保存View,并通过viewId来找View
     *
     * @param viewId view控件id
     * @return view控件
     */
    public View getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    /**
     * 通过viewId给TextView设置内容
     *
     * @param viewId view控件Id
     * @param text   内容
     */
    public void setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
    }
}
