package com.common.base.listener;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * Created by ricky on 2016/09/13.
 * <p/>
 * 自定义的sticky touch监听事件
 */
public class C_StickyRecyclerHeadersCustomTouchListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetector mTapDetector;
    private final RecyclerView mRecyclerView;
    private final StickyRecyclerHeadersDecoration mDecor;
    private OnHeaderClickListener mOnHeaderClickListener;

    public interface OnHeaderClickListener {
        void onHeaderClick(View header, int position, long headerId);
    }

    public C_StickyRecyclerHeadersCustomTouchListener(final RecyclerView recyclerView,
                                                      final StickyRecyclerHeadersDecoration decor) {
        mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        mRecyclerView = recyclerView;
        mDecor = decor;
    }

    public StickyRecyclerHeadersAdapter getAdapter() {
        if (mRecyclerView.getAdapter() instanceof StickyRecyclerHeadersAdapter) {
            return (StickyRecyclerHeadersAdapter) mRecyclerView.getAdapter();
        } else {
            throw new IllegalStateException("A RecyclerView with " +
                    C_StickyRecyclerHeadersCustomTouchListener.class.getSimpleName() +
                    " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
        }
    }


    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        mOnHeaderClickListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        if (this.mOnHeaderClickListener != null) {
            boolean tapDetectorResponse = this.mTapDetector.onTouchEvent(e);
            if (tapDetectorResponse) {
                // Don't return false if a single tap is detected
                return true;
            }
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
                return position != -1;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent e) { /* do nothing? */ }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getHeaderView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
                boolean hasClick = false;
                if (headerView instanceof ViewGroup) {
                    ViewGroup viewGroup = ((ViewGroup) headerView);
                    int count = viewGroup.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View view = viewGroup.getChildAt(i);
                        int left = view.getLeft();
                        int right = view.getRight();
                        int top = view.getTop();
                        int bottom = view.getBottom();
                        if (e.getX() > left && e.getX() < right
                                && e.getY() > top && e.getY() < bottom) {
                            hasClick = true;
                            mOnHeaderClickListener.onHeaderClick(view, position, headerId);
                        }
                    }
                }
                if (!hasClick) {
                    mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
                }
                headerView.onTouchEvent(e);
                return true;
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}