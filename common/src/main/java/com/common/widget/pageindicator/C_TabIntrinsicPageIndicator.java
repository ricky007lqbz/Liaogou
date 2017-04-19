//package com.common.widget.pageindicator;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.text.TextUtils.TruncateAt;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.HorizontalScrollView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.common.R;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
//
///**
// * @author Administrator
// *
// *         固有宽度的 PagerIndicator
// */
//public class C_TabIntrinsicPageIndicator extends HorizontalScrollView implements
//		C_PageIndicator {
//
//	private final int intrinsicWidth = 100;
//	private int width;
//	/** Title text used when no title is provided by the adapter. */
//	protected static final CharSequence EMPTY_TITLE = "";
//
//	private Runnable mTabSelector;
//	private Context context;
//
//	final IcsLinearLayout mTabLayout;
//
//	ViewPager mViewPager;
//
//	private ViewPager.OnPageChangeListener mListener;
//
//	int mSelectedTabIndex;
//	int lastTabIndex;
//
//	private OnTabReselectedListener mTabReselectedListener;
//
//	/**
//	 * Interface for a callback when the selected tab has been reselected.
//	 */
//	public interface OnTabReselectedListener {
//		/**
//		 * Callback when the selected tab has been reselected.
//		 *
//		 * @param pagetIndex
//		 *            Position of the current center item.
//		 */
//		void onTabReselected(int position);
//
//		void onTabItemClicked(int position);
//	}
//
//	public TabIntrinsicPageIndicator(Context context) {
//		this(context, null);
//		this.context = context;
//		width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//				intrinsicWidth, getResources().getDisplayMetrics());
//	}
//
//	public TabIntrinsicPageIndicator(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//		width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//				intrinsicWidth, getResources().getDisplayMetrics());
//		setHorizontalScrollBarEnabled(false);
//		mTabLayout = new IcsLinearLayout(context,
//				R.attr.vpiTabPageIndicatorStyle);
//		addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
//				MATCH_PARENT));
//
//	}
//
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
//		setFillViewport(lockedExpanded);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		final int oldWidth = getMeasuredWidth();
//		final int newWidth = getMeasuredWidth();
//		if (lockedExpanded && oldWidth != newWidth) {
//			setCurrentItem(mSelectedTabIndex);
//		}
//	}
//
//	private void animateToTab(final int position) {
//		final View tabView = mTabLayout.getChildAt(position);
//		if (mTabSelector != null) {
//			removeCallbacks(mTabSelector);
//		}
//		mTabSelector = new Runnable() {
//			@Override
//			public void run() {
//				final int scrollPos = tabView.getLeft()
//						- (getWidth() - tabView.getWidth()) / 2;
//				smoothScrollTo(scrollPos, 0);
//				mTabSelector = null;
//			}
//		};
//		post(mTabSelector);
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
//	private void addTab(int index, CharSequence text, int iconResId) {
//		final TabView tabView = new TabView(getContext());
//		tabView.mIndex = index;
//		tabView.setFocusable(true);
//		tabView.setOnClickListener(mTabClickListener);
//		tabView.setText(text);
//		if (iconResId != 0) {
//			tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
//		}
//		tabView.setSingleLine();
//		tabView.setEllipsize(TruncateAt.MIDDLE);
//		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(width,
//				WRAP_CONTENT));
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
//		mViewPager.setCurrentItem(item, true);
//		final int tabCount = mTabLayout.getChildCount();
//		for (int i = 0; i < tabCount; i++) {
//			final View child = mTabLayout.getChildAt(i);
//			final boolean isSelected = (i == item);
//			child.setSelected(isSelected);
//			if (isSelected) {
//				animateToTab(item);
//			}
//		}
//	}
//
//	@Override
//	public void setOnPageChangeListener(OnPageChangeListener listener) {
//		mListener = listener;
//	}
//
//	public class TabView extends TextView {
//		int mIndex;
//
//		public TabView(Context context) {
//			super(context, null, R.attr.vpiTabPageIndicatorStyle);
//		}
//
//		@Override
//		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//			setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
//		}
//
//		public int getIndex() {
//			return mIndex;
//		}
//	}
//
//	final OnClickListener mTabClickListener = new OnClickListener() {
//		@Override
//		public void onClick(View view) {
//			TabView tabView = (TabView) view;
//			final int oldSelected = mViewPager.getCurrentItem();
//			final int newSelected = tabView.getIndex();
//			mViewPager.setCurrentItem(newSelected, true);
//			if (oldSelected == newSelected && mTabReselectedListener != null) {
//				mTabReselectedListener.onTabReselected(newSelected);
//			}
//			if (oldSelected != newSelected && mTabReselectedListener != null) {
//				mTabReselectedListener.onTabItemClicked(newSelected);
//			}
//		}
//	};
//
//	public IcsLinearLayout getmTabLayout() {
//		return mTabLayout;
//	}
//
//	public void setOnTabReselectedListener(OnTabReselectedListener listener) {
//		mTabReselectedListener = listener;
//	}
//
//	public void setWidth(int width) {
//		this.width = width;
//	}
//
//}