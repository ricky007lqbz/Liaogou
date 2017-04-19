//package com.common.widget.pageindicator;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.HorizontalScrollView;
//import android.widget.LinearLayout;
//
//import com.common.R;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
//
///**
// * @author Administrator
// *
// *         pageIndicator 设置 background
// *
// *         重写覆盖 notifyDataSetChanged
// *
// *         重写覆盖 addTab
// *
// */
//public class C_ImageBackgroundPagerIndicator extends HorizontalScrollView
//		implements C_PageIndicator {
//
//	private final int intrinsicWidth = 80;
//	/** Title text used when no title is provided by the adapter. */
//	protected static final CharSequence EMPTY_TITLE = "";
//
//	private Runnable mTabSelector;
//
//	final IcsLinearLayout mTabLayout;
//
//	private ViewPager mViewPager;
//
//	private ViewPager.OnPageChangeListener mListener;
//
//	int mSelectedTabIndex;
//	int lastTabIndex;
//
//	private OnTabReselectedListener mTabReselectedListener;
//
//	private final int DEFAULT_TAB_WIDTH = 80;
//
//	private int width;
//	private Context context;
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
//	public ImageBackgroundPagerIndicator(Context context) {
//		this(context, null);
//	}
//
//	public ImageBackgroundPagerIndicator(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//		width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//				DEFAULT_TAB_WIDTH, getResources().getDisplayMetrics());
//		setHorizontalScrollBarEnabled(false);
//		mTabLayout = new IcsLinearLayout(context,
//				R.attr.vpiTabPageIndicatorStyle);
//		mTabLayout.setGravity(Gravity.CENTER);
//		addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
//				MATCH_PARENT));
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
//	public void notifyDataSetChanged() {
//		mTabLayout.removeAllViews();
//		PagerAdapter adapter = mViewPager.getAdapter();
//		C_BackgroundPagerAdapter iconAdapter = null;
//		if (adapter instanceof C_BackgroundPagerAdapter) {
//			iconAdapter = (C_BackgroundPagerAdapter) adapter;
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
//				iconResId = iconAdapter.getBackgoundRcsId(i);
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
//	private void addTab(int index, CharSequence text, int iconResId) {
//		final TabView tabView = new TabView(getContext());
//		if (iconResId != 0) {
//			tabView.setBackgroundResource(iconResId);
//		}
//		tabView.mIndex = index;
//		tabView.setFocusable(true);
//		tabView.setGravity(Gravity.CENTER);
//
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,
//				WRAP_CONTENT);
//		// lp.setMargins(width/10, 0, width/10, 0);
//		tabView.setOnClickListener(mTabClickListener);
//		tabView.setText(text);
//		mTabLayout.addView(tabView, lp);
//		if (lastTabIndex == index)
//			return;
//		// 添加一个view 会当作一个item ,导致选中项不对应
//		// View devide = new View(context);
//		// int color = getResources().getColor(R.color.app_mian);
//		// devide.setBackgroundColor(color);
//		// mTabLayout.addView(devide, new LinearLayout.LayoutParams(2,
//		// MATCH_PARENT));
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
//	@Override
//	public void setOnPageChangeListener(OnPageChangeListener listener) {
//		mListener = listener;
//	}
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
//	public class TabView extends TextLabel {
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
//}
