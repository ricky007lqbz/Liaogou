package com.common.widget.stickylayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.common.R;
import com.common.animation.C_HeightChangeAnimation;
import com.common.util.C_DisplayUtil;
import com.common.util.C_L;
import com.common.util.C_ResUtil;

/**
 * Created by ricky on 2016/08/22.
 * <p/>
 * 头部view可以根据手势收缩和打开的layout，底部装载viewPager
 */
public class C_StickySingleLayout extends LinearLayout implements NestedScrollingParent {

    private View mTopView; //顶部视图
    private View mFrameLayout; //用来装载底部fragment

    private int mTopBannerHeight; //外部传进来的topBanner高度
    private int mTopHeight; //除去banner部分，topView的实施高度

    private C_StickyFlingListener stickyFlingListener; //滑动，下拉监听事件

    private int NestedPreDy; //传递给NestedScrolling监听的y方向的滑动偏移量
    private int maxPullHeight; //每次下拉的最大高度
    private boolean isShowTop = true; //是否处于显示topView的状态(默认处于显示状态)
    private float mLastY; //保存action down的位置
    private boolean isCanDragging; //是否可以下拉
    private boolean isDragging; //是否处在下拉状态
    private boolean isRefreshing; //是否处于刷新状态
    private boolean isFirstMove = true; //每次action的第一次移动的标记

    private C_StickyTopBgView mTopBgView; //背景视图（做动画用）

    private OverScroller mScroller;

    private C_HeightChangeAnimation heightAnimation; //改变高度的动画

    public C_StickySingleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);

        mScroller = new OverScroller(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StickyLayout);
        this.mTopBannerHeight = (int) ta.getDimension(R.styleable.StickyLayout_top_tab_height, 0)
                + C_DisplayUtil.getStatusBarHeight(context);
        ta.recycle();
    }

    public void setStickyFlingListener(C_StickyFlingListener listener) {
        this.stickyFlingListener = listener;
    }

    public void setTopBgView(C_StickyTopBgView view) {
        this.mTopBgView = view;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_stick_top_view);
        mFrameLayout = findViewById(R.id.id_stick_single_fragment);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mFrameLayout.getLayoutParams();
        params.height = getMeasuredHeight() - mTopBannerHeight;
        setMeasuredDimension(getMeasuredWidth(), mTopView.getMeasuredHeight() + mFrameLayout.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopHeight = mTopView.getMeasuredHeight() - mTopBannerHeight;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopHeight) {
            y = mTopHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        C_L.d("onStartNestedScroll");
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        C_L.d("onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(View target) {
        C_L.d("onStopNestedScroll");
        isShowTop = getScrollY() == 0 && !ViewCompat.canScrollVertically(target, -1);
        if (stickyFlingListener != null) {
            stickyFlingListener.onStopNestedScroll(mTopHeight, getScrollY(), NestedPreDy);
        }
    }

    /**
     * 自动收回头部
     */
    private void autoPullBackTopView() {
        if (mTopView == null) {
            return;
        }
        int paddingTop = mTopView.getPaddingTop();
        int paddingBottom = mTopView.getPaddingBottom();

        if (paddingTop == 0 && paddingBottom == 0) {
            return;
        }
        if (heightAnimation == null) {
            heightAnimation = new C_HeightChangeAnimation(mTopView, mTopBgView);
            heightAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            heightAnimation.setDuration(500);
            heightAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isDragging = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (maxPullHeight >= C_ResUtil.getDimens(R.dimen._100dp)) {
                        if (stickyFlingListener != null) {
                            stickyFlingListener.onRefresh();
                        }
                    }
                    isDragging = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        heightAnimation.setPadding(paddingTop, 0, paddingBottom, 0);
        startAnimation(heightAnimation);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        C_L.d("onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        this.NestedPreDy = dy;
        C_L.d("onNestedPreScroll");
        if (stickyFlingListener != null) {
            stickyFlingListener.onNestedPreScroll(mTopHeight, getScrollY(), dy);
        }
        boolean isHiddenTop = dy > 0 && getScrollY() < mTopHeight;
        boolean isShowTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (isHiddenTop || isShowTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        C_L.d("onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        C_L.d("onNestedPreFling");
        //down - //up+
        if (getScrollY() >= mTopHeight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                isCanDragging = !isRefreshing && isShowTop;
                isFirstMove = true;
                isDragging = false;
                maxPullHeight = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetY = y - mLastY;
                if (isFirstMove && offsetY < 0) {
                    isCanDragging = false;
                }
                if (isCanDragging && offsetY > 0 && mTopView != null) {
                    //记录每次下拉的最大高度
                    if (offsetY > maxPullHeight) {
                        maxPullHeight = (int) offsetY;
                    }
                    //头部view的下拉动画
                    mTopView.setPadding(0, (int) offsetY / 2, 0, (int) offsetY / 2);
                    //传入的头部背景view下拉动画
                    if (mTopBgView != null) {
                        mTopBgView.setPadding(0, (int) offsetY / 2, 0, (int) offsetY / 2);
                    }
                    isDragging = true;
                }
                isFirstMove = false;
                break;
            case MotionEvent.ACTION_UP:
                if (isDragging) {
                    autoPullBackTopView();
                }
                break;
        }
        return isDragging || isRefreshing || super.dispatchTouchEvent(ev);
    }

    public void onRefreshStart() {
        isRefreshing = true;
    }

    public void onRefreshEnd() {
        isRefreshing = false;
    }
}
