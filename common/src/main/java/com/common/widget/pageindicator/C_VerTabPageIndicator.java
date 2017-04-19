//package com.common.widget.pageindicator;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//
//import com.common.R;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//
////import com.common.R;
//
///**
// *
// * @author Administrator
// *
// *         垂直的 ViewPager Indicator
// *
// */
//public class C_VerTabPageIndicator extends ScrollView implements C_PageIndicator {
//	/** Title text used when no title is provided by the adapter. */
//	private static final CharSequence EMPTY_TITLE = "";
//
//	/**
//	 * Interface for a callback when the selected tab has been reselected.
//	 */
//	public interface OnTabReselectedListener {
//		/**
//		 * Callback when the selected tab has been reselected.
//		 *
//		 * @param position
//		 *            Position of the current center item.
//		 */
//		void onTabReselected(int position);
//
//		void onTabItemClicked(int position);
//	}
//
//	private Runnable mTabSelector;
//
//	private final OnClickListener mTabClickListener = new OnClickListener() {
//		@Override
//		public void onClick(View view) {
//			VerticalTabView tabView = (VerticalTabView) view;
//			final int oldSelected = mViewPager.getCurrentItem();
//			final int newSelected = tabView.getIndex();
//			mViewPager.setCurrentItem(newSelected, true);
//			// mViewPager.
//			if (oldSelected == newSelected && mTabReselectedListener != null) {
//				mTabReselectedListener.onTabReselected(newSelected);
//			}
//			if (oldSelected != newSelected && mTabReselectedListener != null) {
//				mTabReselectedListener.onTabItemClicked(newSelected);
//			}
//		}
//	};
//
//	private final VerIcsLinearLayout mTabLayout;
//
//	public VerIcsLinearLayout getmTabLayout() {
//		return mTabLayout;
//	}
//
//	private ViewPager mViewPager;
//	private ViewPager.OnPageChangeListener mListener;
//
//	private int mMaxTabWidth;
//
//	private int mMaxTabHeight;
//	private int mSelectedTabIndex;
//	private int lastTabIndex;
//
//	private OnTabReselectedListener mTabReselectedListener;
//	private Context context;
//
//	private int defaultHeight;
//
//	public VerTabPageIndicator(Context context) {
//		this(context, null);
//		this.context = context;
//	}
//
//	public VerTabPageIndicator(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//		defaultHeight = (int) TypedValue.applyDimension(
//				TypedValue.COMPLEX_UNIT_DIP, 55.0f, getResources()
//						.getDisplayMetrics());
//		setHorizontalScrollBarEnabled(false);
//		setVerticalScrollBarEnabled(false);
//		setScrollbarFadingEnabled(true);
//		mTabLayout = new VerIcsLinearLayout(context,
//				R.attr.vpiTabPageIndicatorStyle);
//		addView(mTabLayout, new ViewGroup.LayoutParams(MATCH_PARENT,
//				MATCH_PARENT));
//	}
//
//	public void setOnTabReselectedListener(OnTabReselectedListener listener) {
//		mTabReselectedListener = listener;
//	}
//
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
//		setFillViewport(lockedExpanded);
//
//		final int childCount = mTabLayout.getChildCount();
//
//		if (childCount > 1
//				&& (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
//			if (childCount > 2) {
//				// mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) *
//				// 0.4f);
//
//				mMaxTabHeight = 80;
//				// (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.08f);
//
//			} else {
//
//				// mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
//				mMaxTabHeight = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.1f);
//			}
//		} else {
//			mMaxTabWidth = -1;
//		}
//
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		final int oldWidth = getMeasuredWidth();
//		final int newWidth = getMeasuredWidth();
//
//		if (lockedExpanded && oldWidth != newWidth) {
//			// Recenter the tab display if we're at a new (scrollable) size.
//			setCurrentItem(mSelectedTabIndex);
//		}
//	}
//
//	private void animateToVerTab(int position) {
//		final View tabView = mTabLayout.getChildAt(position);
//		if (mTabSelector != null) {
//			removeCallbacks(mTabSelector);
//		}
//		mTabSelector = new Runnable() {
//			@Override
//			public void run() {
//				final int scrollPos = tabView.getTop()
//						- (getHeight() - tabView.getHeight()) / 2;
//				smoothScrollTo(0, scrollPos);
//				mTabSelector = null;
//			}
//		};
//		post(mTabSelector);
//
//	}
//
//	@Override
//	public void onAttachedToWindow() {
//		super.onAttachedToWindow();
//		if (mTabSelector != null) {
//			// Re-post the selector we saved
//			post(mTabSelector);
//		}
//	}
//
//	@Override
//	public void onDetachedFromWindow() {
//		super.onDetachedFromWindow();
//		if (mTabSelector != null) {
//			removeCallbacks(mTabSelector);
//		}
//	}
//
//	@SuppressLint("NewApi")
//	private void addTab(int index, CharSequence text, int iconResId) {
//		final VerticalTabView tabView = new VerticalTabView(getContext());
//		tabView.mIndex = index;
//		tabView.setFocusable(true);
//		tabView.setOnClickListener(mTabClickListener);
//		tabView.setText(text);
//		if (iconResId != 0) {
//			tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
//		}
//		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(MATCH_PARENT,
//				defaultHeight, 0));
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int arg0) {
//		if (mListener != null) {
//			mListener.onPageScrollStateChanged(arg0);
//		}
//	}
//
//	@Override
//	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		if (mListener != null) {
//			mListener.onPageScrolled(arg0, arg1, arg2);
//		}
//	}
//
//	@Override
//	public void onPageSelected(int arg0) {
//		setCurrentItem(arg0);
//		if (mListener != null) {
//			mListener.onPageSelected(arg0);
//		}
//	}
//
//	@Override
//	public void setViewPager(ViewPager view) {
//		if (mViewPager == view) {
//			return;
//		}
//		if (mViewPager != null) {
//			mViewPager.setOnPageChangeListener(null);
//		}
//		final PagerAdapter adapter = view.getAdapter();
//		if (adapter == null) {
//			throw new IllegalStateException(
//					"ViewPager does not have adapter instance.");
//		}
//		mViewPager = view;
//		view.setOnPageChangeListener(this);
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public void notifyDataSetChanged() {
//		mTabLayout.removeAllViews();
//		PagerAdapter adapter = mViewPager.getAdapter();
//		IconPagerAdapter iconAdapter = null;
//		if (adapter instanceof IconPagerAdapter) {
//			iconAdapter = (IconPagerAdapter) adapter;
//		}
//		final int count = adapter.getCount();
//		lastTabIndex = count - 1;
//		for (int i = 0; i < count; i++) {
//			CharSequence title = adapter.getPageTitle(i);
//			if (title == null) {
//				title = EMPTY_TITLE;
//			}
//			int iconResId = 0;
//			if (iconAdapter != null) {
//				iconResId = iconAdapter.getIconResId(i);
//			}
//			addTab(i, title, iconResId);
//		}
//		if (mSelectedTabIndex > count) {
//			mSelectedTabIndex = count - 1;
//		}
//		setCurrentItem(mSelectedTabIndex);
//		requestLayout();
//	}
//
//	@Override
//	public void setViewPager(ViewPager view, int initialPosition) {
//		setViewPager(view);
//		setCurrentItem(initialPosition);
//	}
//
//	@Override
//	public void setCurrentItem(int item) {
//		if (mViewPager == null) {
//			throw new IllegalStateException("ViewPager has not been bound.");
//		}
//		mSelectedTabIndex = item;
//
//		mViewPager.setCurrentItem(item, true);
//
//		final int tabCount = mTabLayout.getChildCount();
//		for (int i = 0; i < tabCount; i++) {
//			final View child = mTabLayout.getChildAt(i);
//			final boolean isSelected = (i == item);
//			child.setSelected(isSelected);
//			if (isSelected) {
//				animateToVerTab(item);
//			}
//		}
//	}
//
//	@Override
//	public void setOnPageChangeListener(OnPageChangeListener listener) {
//		mListener = listener;
//	}
//
//	public class VerticalTabView extends TextLabel {
//		private int mIndex;
//
//		public VerticalTabView(Context context) {
//			super(context, null, R.attr.vpiTabPageIndicatorStyle);
//		}
//
//		@Override
//		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//			// Re-measure if we went beyond our maximum size.
//			// int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//			// int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
//			// int modelWidth = MeasureSpec.getMode(widthMeasureSpec);
//			// int modelHeight = MeasureSpec.getMode(heightMeasureSpec);
//			// //LogUtils.i("TabpageIndicator.TabView", sizeHeight + ":" +
//			// sizeWidth
//			// + ":" + modelWidth + ":" + modelHeight);
//
//			// if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
//			// // 为了解决字符宽度比较大的，将测量出来的值直接付给最大宽度
//			// mMaxTabWidth = getMeasuredWidth();
//			// super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
//			// MeasureSpec.EXACTLY), heightMeasureSpec);
//			// }
//
//			if (mMaxTabHeight > 0 && getMeasuredHeight() > mMaxTabHeight) {
//				mMaxTabHeight = getMeasuredHeight();
//				super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
//						mMaxTabHeight, MeasureSpec.EXACTLY));
//			}
//		}
//
//		public int getIndex() {
//			return mIndex;
//		}
//	}
//
//}
