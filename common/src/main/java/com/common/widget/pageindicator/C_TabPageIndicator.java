package com.common.widget.pageindicator;

/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_DisplayUtil;
import com.common.util.C_ResUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class C_TabPageIndicator extends HorizontalScrollView implements
        C_PageIndicator {

    private Context context;
    /**
     * Title text used when no title is provided by the adapter.
     */
    protected static final CharSequence EMPTY_TITLE = "";

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         */
        void onTabReselected(int position);

        void onTabItemClicked(int position);
    }

    private Runnable mTabSelector;

    final OnClickListener mTabClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected, true);
            // mViewPager.
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
            if (oldSelected != newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabItemClicked(newSelected);
            }
        }
    };

    final C_IcsLinearLayout mTabLayout;

    public C_IcsLinearLayout getmTabLayout() {
        return mTabLayout;
    }

    ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mMaxTabWidth;
    int mSelectedTabIndex;
    int lastTabIndex;

    private OnTabReselectedListener mTabReselectedListener;

    private boolean deviceWeight; // tag 权重相同

    public C_TabPageIndicator(Context context) {
        this(context, null);
        this.context = context;
    }

    public C_TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setHorizontalScrollBarEnabled(false);
        mTabLayout = new C_IcsLinearLayout(context,
                R.attr.vpiTabPageIndicatorStyle);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
                MATCH_PARENT));

    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();

        if (childCount > 1
                && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int oldWidth = getMeasuredWidth();
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            @Override
            public void run() {
                final int scrollPos = tabView.getLeft()
                        - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    @SuppressLint("NewApi")
    // private void addTab(int index, CharSequence text, int iconResId) {
    // final TabView tabView = new TabView(getContext());
    // tabView.mIndex = index;
    // tabView.setFocusable(true);
    // tabView.setOnClickListener(mTabClickListener);
    // tabView.setText(text);
    // if (iconResId != 0) {
    // // tabView.setCompoundDrawablePadding(
    // // (int) getResources().getDimension(R.dimen.margin1));
    // tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
    // // setTabIcon(tabView, iconResId);
    // }
    // if (deviceWeight) {
    // mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
    // MATCH_PARENT, 1));
    // } else {
    // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
    // WRAP_CONTENT, MATCH_PARENT);
    // lp.leftMargin = 10;
    // lp.rightMargin = 10;
    // mTabLayout.addView(tabView, lp);
    // }
    // }
    private void addTab(int index, CharSequence text, int iconResId) {
        if (deviceWeight) { // tab 平分 外层再用 Layout装载。
            LinearLayout layout = new LinearLayout(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    WRAP_CONTENT, MATCH_PARENT, 1);
            //要设背景颜色，不设魅族GG了
            layout.setBackgroundColor(C_ResUtil.getSrcColor(R.color.full_transparent));
            layout.setGravity(Gravity.CENTER);
            // TextView
            final TabView tabView = new TabView(getContext());
            tabView.mIndex = index;
            // 不能和layoutLayout重复触发
            tabView.setFocusable(false);
            tabView.setClickable(false);
            tabView.setOnClickListener(mTabClickListener);
            tabView.setText(text);
            if (iconResId != 0) {
//				 tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId,
//				 0, 0);
//                tabView.setPaddingRelative(10, 10, 10, 10);
//                tabView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
//                        iconResId, 0);
                tabView.setBackgroundResource(iconResId);
            }
            tabView.setPadding(C_ResUtil.getDimens(R.dimen._10dp), 0, C_ResUtil.getDimens(R.dimen._10dp), 0);
            LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(
                    WRAP_CONTENT, C_ResUtil.getDimens(R.dimen._22dp));
            tvlp.rightMargin = C_ResUtil.getDimens(R.dimen._2dp);
            tvlp.leftMargin = C_ResUtil.getDimens(R.dimen._2dp);
            layout.addView(tabView, tvlp);
            layout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    tabView.performClick();
                }
            });
            // TextView
            mTabLayout.addView(layout, lp);
        } else {
            final TabView tabView = new TabView(getContext());
            tabView.mIndex = index;
            tabView.setFocusable(true);
            tabView.setOnClickListener(mTabClickListener);
            tabView.setText(text);
            if (iconResId != 0) {
                // tabView.setCompoundDrawablePadding(
                // (int) getResources().getDimension(R.dimen.margin1));
                // tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId,
                // 0, 0);
                tabView.setPaddingRelative(10, 10, 10, 10);
                tabView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                        iconResId, 0);
                // setTabIcon(tabView, iconResId);
            }
            tabView.setPadding(C_ResUtil.getDimens(R.dimen._10dp), 0, C_ResUtil.getDimens(R.dimen._10dp), 0);
            // if (deviceWeight) {
            // mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
            // MATCH_PARENT, 1));
            // } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    WRAP_CONTENT, C_ResUtil.getDimens(R.dimen._22dp));
            lp.leftMargin = C_ResUtil.getDimens(R.dimen._2dp);
            lp.rightMargin = C_ResUtil.getDimens(R.dimen._2dp);
//			mTabLayout.addView(tabView, lp);
            // }
        }
    }

    /**
     * Icon剪裁 正方形
     *
     * @param view
     * @param iconResId
     */
    private void setTabIcon(TextView view, int iconResId) {
        Drawable drawable = context.getResources().getDrawable(iconResId);
        int h = drawable.getIntrinsicHeight(); // 固有高宽
        int hs = drawable.getMinimumHeight(); // 建议高宽
        // int crop = h - hs; // 裁剪部分
        int crop = h / 5;
        Rect cRect = new Rect(crop, crop, h - crop, h - crop);// 裁切
        // drawable.setBounds(crop, crop, h - crop, h - crop); // 显示范围
        // view.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0);
        // view.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end,
        view.setCompoundDrawables(null, drawable, null, null);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        C_IconPagerAdapter iconAdapter = null;
        if (adapter instanceof C_IconPagerAdapter) {
            iconAdapter = (C_IconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        lastTabIndex = count - 1;
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;

        mViewPager.setCurrentItem(item, true);

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }


    // set this before setViewPager();
    public void setDeviceWeight(boolean deviceWeight) {
        this.deviceWeight = deviceWeight;
    }

    private float size;

    public void setTextSize(float size) {
        this.size = size;
    }

    public class TabView extends TextView {
        int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
            if (size > 0) {
                setTextSize(C_DisplayUtil.dp2px(context, (int) size));
            }
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                // 为了解决字符宽度比较大的，将测量出来的值直接付给最大宽度
                mMaxTabWidth = getMeasuredWidth();
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
                        MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right,
                                int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }

        public int getIndex() {
            return mIndex;
        }
    }

}
