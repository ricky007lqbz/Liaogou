package com.common.widget.stickylayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.common.R;
import com.common.util.C_DevUtil;
import com.common.util.glide.C_ImageLoader;

/**
 * Created by ricky on 2016/08/25.
 * <p/>
 * StickyLayout 用到的topView
 */
public class C_StickyTopBgView extends FrameLayout {

    private ImageView ivBg;
    private ImageView ivCover;

    private boolean isFirst = true;
    private int origHeight = 0;
    private int origWidth = 0;

    public C_StickyTopBgView(Context context) {
        super(context);
        init(context);
    }

    public C_StickyTopBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ivBg = new ImageView(context);
        ivCover = new ImageView(context);
        ivCover.setImageResource(R.color.black_50);

        setClipToPadding(false);
        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(ivBg, 0);
        addView(ivCover, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float scale = (float) getMeasuredHeight() / (origHeight);
        ivBg.setScaleX(scale);
        ivBg.setScaleY(scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isFirst && origHeight == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            origHeight = getMeasuredHeight();
            origWidth = getMeasuredWidth();
        } else {
            setMeasuredDimension(origWidth, origHeight + getPaddingTop() + getPaddingBottom());
        }
        isFirst = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ivCover.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        ivBg.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setContentView(View view) {
        addView(view);
        setIvHeight();
        invalidate();
    }

    public void setOrigHeight(int height) {
        this.origWidth = C_DevUtil.getScreenWidth(getContext());
        this.origHeight = height;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        ivBg.setVisibility(GONE);
        ivCover.setVisibility(GONE);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        ivBg.setVisibility(GONE);
        ivCover.setVisibility(GONE);
    }

    /**
     * 根据url设置背景图
     */
    public void setIvBg(String url) {
        if (ivBg != null) {
            ivBg.setVisibility(VISIBLE);
            ivCover.setVisibility(VISIBLE);
            ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            C_ImageLoader.setImageByUrlCenterCrop(getContext(), ivBg, url, R.color.bg_top_banner);
        }
    }

    /**
     * 根据res资源设置背景图
     */
    public void setIvBg(int imgRes) {
        setIvBg(imgRes, true);
    }

    /**
     * 根据res资源设置背景图
     */
    public void setIvBg(int imgRes, boolean withCover) {
        if (ivBg != null) {
            ivBg.setVisibility(VISIBLE);
            ivCover.setVisibility(withCover ? VISIBLE : GONE);
            ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivBg.setImageResource(imgRes);
        }
    }

    /**
     * 根的高度 = 顶层数据层高度
     */
    private void setIvHeight() {

        View top = this.getChildAt(this.getChildCount() - 1);
        int height = top.getMeasuredHeight();

        FrameLayout.LayoutParams ivBgfl = (LayoutParams) ivBg.getLayoutParams();
        FrameLayout.LayoutParams ivCoverBgfl = (LayoutParams) ivCover.getLayoutParams();

        ivBgfl.height = height;
        ivCoverBgfl.height = height;

        ivBg.setLayoutParams(ivBgfl);
        ivCover.setLayoutParams(ivCoverBgfl);
    }
}
