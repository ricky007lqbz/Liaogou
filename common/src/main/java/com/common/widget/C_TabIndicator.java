package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.Common;
import com.common.R;
import com.common.util.C_AnimationHelper;
import com.common.util.C_DevUtil;
import com.common.util.C_DisplayUtil;
import com.common.util.C_L;
import com.common.util.C_ResUtil;

/**
 * Created by 谢治娴 on 2016/4/1.
 * <p/>
 * 通用的带三角指示器的indicator
 */
public class C_TabIndicator extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private final static boolean isDebug = true;

    private int drawableId; //  indicator  图标
    private Drawable drawable;

    private int textColorSelected;// 字体颜色
    private int textSize;// 字体大小

    private int textColorNomal; // 选中的颜色
    private int textSizeSelected; // 选中的字体大小

    private String[] tabs;
    private int selectedIndex = -1;
    private final static int animationTime = 100;

    private ViewPager viewPager;
    private ImageView iv;
    private boolean hasMark;
    int x_start = 0;

    private HorizontalScrollView scrollView;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams ll_lp;

    private RelativeLayout.LayoutParams rl_lp;

    private int _10dp = C_ResUtil.getDimens(R.dimen._10dp);

    public C_TabIndicator(Context context) {
        this(context, null);
    }

    public C_TabIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public C_TabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rl_lp = new RelativeLayout.LayoutParams(C_DisplayUtil.dp2px(context, 12), C_DisplayUtil.dp2px(context, 12));
        ll_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ll_lp.weight = 1;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.C_TabIndicator);
        this.hasMark = ta.getBoolean(R.styleable.C_TabIndicator_hasMark, false);
//        this.drawableId = ta.getResourceId(R.styleable.TabIndicator_tab_indicator_drawable,R.drawable.c_icon_tab_triangle_blue);
        drawable = ta.getDrawable(R.styleable.C_TabIndicator_tab_indicator_drawable);
        if (drawable == null)
            drawable = C_ResUtil.getDrawable(R.drawable.c_icon_tab_rectangle_blue);
        this.textColorSelected = ta.getColor(R.styleable.C_TabIndicator_tab_indicator_text_color, C_ResUtil.getSrcColor(R.color.text_main));
        this.textColorNomal = ta.getColor(R.styleable.C_TabIndicator_tab_indicator_text_color_selected, C_ResUtil.getSrcColor(R.color.text_main));
        this.textSize = (int) ta.getDimension(R.styleable.C_TabIndicator_tab_indicator_text_size, C_ResUtil.getDimens(R.dimen.text_14));
        this.textSizeSelected = (int) ta.getDimension(R.styleable.C_TabIndicator_tab_indicator_text_size_selected, C_ResUtil.getDimens(R.dimen.text_14));
        initTabIndicator();
        ta.recycle();
    }

    /**
     * 设置底部指针背景
     *
     * @param resource drawable资源
     */
    public void setDrawableId(int resource) {
        this.drawable = C_ResUtil.getDrawable(resource);
        iv.setImageDrawable(drawable);
    }

    /**
     * 设置文字颜色
     *
     * @param textRes     基本颜色
     * @param selectedRes 选中颜色
     */
    public void setTextColor(int textRes, int selectedRes) {
        this.textColorNomal = textRes;
        this.textColorSelected = selectedRes;
    }

    private void initTabIndicator() {
        scrollView = new HorizontalScrollView(getContext());
        scrollView.setHorizontalScrollBarEnabled(false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(scrollView, params);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.addView(linearLayout, layoutParams);

        iv = new ImageView(getContext());
        iv.setImageDrawable(drawable);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(C_ResUtil.getDimens(R.dimen._30dp), ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(C_ResUtil.getDimens(R.dimen._10dp), ViewGroup.LayoutParams.WRAP_CONTENT);
        lParams.bottomMargin = C_ResUtil.getDimens(R.dimen._3dp);
        lParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.addView(iv, lParams);
    }

    public void setTabs(String[] tabs) {
        this.tabs = tabs;
        selectedIndex = -1; // 初始化
        refreshTabsText();
        setTextSelected(0);
    }


    private SparseArray<C_RoundTextView> roundMarks;
    private SparseArray<ImageView> redMarks;

    /**
     * 设置红点标记
     */
    public void setRedPointMark(int i, boolean isShow) {
        if (i < 0 && i >= tabs.length) {
            return;
        }
        if (redMarks == null) {
            redMarks = new SparseArray<>();
        }
        ImageView redPointImg = redMarks.get(i);
        if (redPointImg == null && !isShow) {
            return;
        }
        if (redPointImg == null) {
            int width = C_DevUtil.getScreenWidth(getContext());
            int _5dp = C_ResUtil.getDimens(R.dimen._5dp);
            redPointImg = new ImageView(getContext());
            redPointImg.setLayoutParams(new RelativeLayout.LayoutParams(_5dp, _5dp));
            redPointImg.setImageResource(R.drawable.c_point_red);
            int x = (int) (width / tabs.length * (i + 0.5f))
                    + getTextWidth(tabs[i], textSize) / 2
                    + C_DisplayUtil.dp2px(getContext(), 3);
            redMarks.put(i, redPointImg);
            ViewCompat.setX(redPointImg, x);
            ViewCompat.setY(redPointImg, C_DisplayUtil.dp2px(getContext(), 7));
            addView(redPointImg);
        }
        if (isShow) {
            setRedCountMark(i, 0, false);
        }
        redPointImg.setVisibility((isShow && i != selectedIndex) ? VISIBLE : GONE);
    }

    /**
     * 设置红点标记
     */
    public void setRedCountMark(int i, int count, boolean isShow) {
        if (i < 0 && i >= tabs.length) {
            return;
        }
        if (roundMarks == null) {
            roundMarks = new SparseArray<>();
        }
        C_RoundTextView roundTextView = roundMarks.get(i);
        if (roundTextView == null && !isShow) {
            return;
        }
        if (roundTextView == null) {
            int width = C_DevUtil.getScreenWidth(getContext());
            roundTextView = new C_RoundTextView(getContext());
            roundTextView.setLayoutParams(rl_lp);
            int x = (int) (width / tabs.length * (i + 0.5f))
                    + getTextWidth(tabs[i], textSize) / 2
                    + C_DisplayUtil.dp2px(getContext(), 3);
            roundMarks.put(i, roundTextView);
            roundTextView.setText(String.valueOf(count));
            roundTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_7));
            roundTextView.setTextColor(C_ResUtil.getSrcColor(R.color.text_white));
            ViewCompat.setX(roundTextView, x);
            ViewCompat.setY(roundTextView, C_DisplayUtil.dp2px(getContext(), 7));
            roundTextView.setBackGroundColor(C_ResUtil.getSrcColor(R.color.color_forth));
            addView(roundTextView);
        }
        if (isShow) {
            setRedPointMark(i, false);
        }
        roundTextView.setVisibility(isShow ? VISIBLE : GONE);
    }

    private Paint mTextPaint;

    private int getTextWidth(String text, int textSize) {
        if (mTextPaint == null) {
            mTextPaint = new TextPaint();
            mTextPaint.setTextSize(textSize);
            mTextPaint.setAntiAlias(true);
        }
        return (int) mTextPaint.measureText(text);
    }


    /**
     * 刷新indicator的文字
     */
    private void refreshTabsText() {
        if (tabs == null) {
            return;
        }
        linearLayout.removeAllViews();
        int count = tabs.length;
        int widthLength = 0;
        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(tabs[i]);
            tv.setGravity(Gravity.CENTER);
            int tempTextSize;
            if (i == selectedIndex) {
                tempTextSize = textSizeSelected;
                tv.setTextColor(textColorSelected);
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeSelected);
            } else {
                tempTextSize = textSize;
                tv.setTextColor(textColorNomal);
                tv.setTypeface(Typeface.DEFAULT);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            tv.setSingleLine();
            tv.setPadding(_10dp, 0, _10dp, 0);
            //动态设置文字长度
            int tvWidth = getTextWidth(tabs[i], tempTextSize) + 2 * _10dp;
            if (i == 0) {
                firstTabCenterX = tvWidth / 2;
            }
            widthLength += tvWidth;

            linearLayout.addView(tv, ll_lp);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(finalI, true);
                    }
                }
            });
        }
        linearLayout.setPadding(_10dp, 0, _10dp, 0);
        widthLength += 2 * _10dp;
        firstTabCenterX += _10dp;

        if (widthLength < Common.SCREEN_WIDTH) {
            linearLayout.getLayoutParams().width = Common.SCREEN_WIDTH;
            linearLayout.setPadding(0, 0, 0, 0);
            for (int k = 0; k < count; k++) {
                TextView tv = (TextView) linearLayout.getChildAt(k);
                tv.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams tempParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                tempParams.width = Common.SCREEN_WIDTH / count;
            }
            firstTabCenterX = Common.SCREEN_WIDTH / (2 * count);
        }
    }

    //第一节tab所计算出的中心x坐标
    private int firstTabCenterX = 0;

    public C_TabIndicator setViewPager(ViewPager vp) {
        this.viewPager = vp;
        this.viewPager.addOnPageChangeListener(this);
        return this;
    }


    /**
     * indicator 切换动画  状态变化
     *
     * @param index tab 索引
     */
    public void setTextSelected(int index) {
        if (selectedIndex == index) return; // lazy
        if (linearLayout.getChildCount() <= index) {
            C_L.e("[PullToRefreshView::setTextSelected]: the text count =", linearLayout.getChildCount(), ",  invlid index = ", index);
            return;
        }
        /* 选中之后会取消红点 */
        setRedCountMark(index, 0, false);
        setRedPointMark(index, false);
        TextView tv = (TextView) linearLayout.getChildAt(index);
        tv.setTextColor(textColorSelected);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        //设置选中状态底部标识的宽度
//        int ivWidth = getTextWidth(tv.getText().toString(), textSizeSelected) + 2 * C_ResUtil.getDimens(R.dimen._8dp);
        int ivWidth = C_ResUtil.getDimens(R.dimen._10dp);
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = ivWidth;
        iv.setLayoutParams(layoutParams);
        if (selectedIndex == -1) {// 如果初始化时设置第0个为空，拿到的-1不用做操作
            int x_end = firstTabCenterX - ivWidth / 2;
            iv.setX(x_end);
            x_start = x_end;
            postInvalidate();
            selectedIndex = index;
        }
    }

    /**
     * 底部指示器的动画
     */
    private void animateToIndicate(int index, int scrollX) {
        if (selectedIndex == index) return; // lazy
        int preIndex = selectedIndex;
        int ivWidth = iv.getLayoutParams().width;
        TextView tv = (TextView) linearLayout.getChildAt(index);
        int x_end = (tv.getLeft() + tv.getRight()) / 2 - ivWidth / 2 - scrollX;

        TextView preTv = (TextView) linearLayout.getChildAt(preIndex);
        preTv.setTextColor(textColorNomal);
        preTv.setTypeface(Typeface.DEFAULT);
        if (x_end == 0) return;
        C_AnimationHelper.startAnimation(x_start, x_end, animationTime, C_AnimationHelper.decelerateInterpolator, C_AnimationHelper.ACTION_X, iv);
        x_start = x_end;

        selectedIndex = index;
    }

    public int getCurrentIndex() {
        return selectedIndex;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.setTextSelected(position);
        animateToTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static final int TYPE_WHITE_BLUE = 0; //一般的类型（一般在用）
    public static final int TYPE_BLACK20_ALPHA = 1; //黑色透明的（特殊地方用）

    /**
     * 设置indicator 显示类型
     */
    public void setViewType(int type) {
        switch (type) {
            case TYPE_WHITE_BLUE:
                setDrawableId(R.drawable.c_icon_tab_rectangle_blue);
//                setTextColor(C_ResUtil.getSrcColor(R.color.text_main), C_ResUtil.getSrcColor(R.color.color_main));
                setTextColor(C_ResUtil.getSrcColor(R.color.text_main), C_ResUtil.getSrcColor(R.color.text_main));
                setBackgroundResource(R.color.bg_white);
                break;
            case TYPE_BLACK20_ALPHA:
                setDrawableId(R.drawable.c_icon_tab_rectangle_alpha);
                setTextColor(C_ResUtil.getSrcColor(R.color.text_white_disable), C_ResUtil.getSrcColor(R.color.text_white));
                setBackgroundResource(R.color.black_20);
                break;
        }
        if (tabs != null && tabs.length > 0) {
            refreshTabsText();
        }
    }

    public boolean isHasMark() {
        return hasMark;
    }

    public void setHasMark(boolean hasMark) {
        this.hasMark = hasMark;
    }


    //==========================自动左右滑动========================//
    private Runnable mTabSelector;


    private void animateToTab(final int position) {
        final View tabView = linearLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            @Override
            public void run() {
                int scrollPos = tabView.getLeft()
                        - (getWidth() - tabView.getWidth()) / 2;
                if (scrollPos < 0) {
                    scrollPos = 0;
                }
                int max = linearLayout.getWidth() - Common.SCREEN_WIDTH;
                if (scrollPos > max) {
                    scrollPos = max;
                }
                scrollView.smoothScrollTo(scrollPos, 0);
                animateToIndicate(position, scrollPos);
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
}
