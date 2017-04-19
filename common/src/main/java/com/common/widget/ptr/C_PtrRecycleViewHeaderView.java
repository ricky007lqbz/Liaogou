package com.common.widget.ptr;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Ziwu on 2016/5/23.
 * <p/>
 * 自定义下拉刷新 headerView
 */
public class C_PtrRecycleViewHeaderView extends RelativeLayout implements PtrUIHandler {

    ImageView ivHeadBaby;
    TextView ivHeaderText;

    private ValueAnimator cwRotationAmimator;//顺时针旋转
    private ValueAnimator ccwRotationAmimator;//逆时针旋转
    private final int ANIMATION_DURATION = 1000; // 时间戳
    private boolean isLoading = false;

    public C_PtrRecycleViewHeaderView(Context context) {
        super(context);
        init();
    }

    public C_PtrRecycleViewHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public C_PtrRecycleViewHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.c_layout_ptr_refresh_view, this);
        ivHeadBaby = (ImageView) findViewById(R.id.iv_header_baby);
        ivHeaderText = (TextView) findViewById(R.id.tv_header_text);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /**
         *在这里调用不对，引发空指针。因为初始化view 根本不可见，没有进行onFinishInflate调用？
         *
         * ButterKnife.bind(this);
         */
    }

    @Override
    protected void onDetachedFromWindow() {
        stopCcwRotationAnimator();
        stopCwRotationAnimator();
        ButterKnife.unbind(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        ivHeaderText.setText("下拉刷新");
        stopCcwRotationAnimator();
        stopCwRotationAnimator();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        ivHeaderText.setText("下拉刷新");
        isLoading = false;
        stopCwRotationAnimator();
        startCcwRotationAnimator();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        ivHeaderText.setText("加载中...");
        isLoading = true;
        startCwRotationAnimator();
        stopCcwRotationAnimator();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        isLoading = false;
        ivHeaderText.setText("加载完成");
        stopCcwRotationAnimator();
        stopCwRotationAnimator();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (ptrIndicator.getCurrentPosY() >= ptrIndicator.getOffsetToRefresh()) {
            ivHeaderText.setText("释放立即刷新...");
        } else {
            if (isLoading) {
                ivHeaderText.setText("加载中");
            } else {
                ivHeaderText.setText("下拉刷新");
            }
        }
    }

    private void startCcwRotationAnimator() {
        if (ccwRotationAmimator == null) {
            ccwRotationAmimator = ValueAnimator.ofInt(-360);
            ccwRotationAmimator.setDuration(ANIMATION_DURATION);
            ccwRotationAmimator.setRepeatCount(-1);
            ccwRotationAmimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int rotateValue = (Integer) animation.getAnimatedValue();
                    if (ivHeadBaby != null)
                        ivHeadBaby.setRotation(rotateValue);
                }
            });
            ccwRotationAmimator.setInterpolator(new LinearInterpolator());
        }
        ccwRotationAmimator.start();
    }

    private void stopCcwRotationAnimator() {
        if (ccwRotationAmimator != null && ccwRotationAmimator.isRunning()) {
            ccwRotationAmimator.end();
        }
    }

    /**
     * 顺时针
     */
    private void startCwRotationAnimator() {
        if (cwRotationAmimator == null) {
            cwRotationAmimator = ValueAnimator.ofInt(360);
            cwRotationAmimator.setDuration(ANIMATION_DURATION);
            cwRotationAmimator.setRepeatCount(-1);
            cwRotationAmimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int rotateValue = (Integer) animation.getAnimatedValue();
                    if (ivHeadBaby != null)
                        ivHeadBaby.setRotation(rotateValue);
                }
            });
            cwRotationAmimator.setInterpolator(new LinearInterpolator());
        }
        cwRotationAmimator.start();
    }

    /**
     * 顺时针
     */
    private void stopCwRotationAnimator() {
        if (cwRotationAmimator != null && cwRotationAmimator.isRunning()) {
            cwRotationAmimator.end();
        }
    }

}
