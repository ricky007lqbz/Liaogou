package com.common.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.common.util.C_ArrayUtil;

/**
 * Created by ricky on 2016/06/07.
 * <p/>
 * 动态改变view高度的动画
 */
public class C_HeightChangeAnimation extends Animation {

    private int startHeight; //初始的高度
    private int deltaHeight; //最终的高度和初始高度之差
    private int startTopPadding; //开始的顶部padding值
    private int deltaTopPadding;//结束和开始的顶部padding值
    private int startBottomPadding;//开始的底部padding值
    private int deltaBottomPadding;//结束和开始的底部padding值
    private View[] views;

    private boolean isByPadding; //是否以padding作为动画

    public C_HeightChangeAnimation(View... views) {
        this.views = views;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (!C_ArrayUtil.isEmpty(views)) {
            for (View view : views) {
                applyTransformation(view, interpolatedTime);
            }
        }
    }

    private void applyTransformation(View view, float interpolatedTime) {
        if (view == null) {
            return;
        }
        if (isByPadding) {
            view.setPadding(0, (int) (startTopPadding + deltaTopPadding * interpolatedTime),
                    0, (int) (startBottomPadding + deltaBottomPadding * interpolatedTime));
        } else {
            view.getLayoutParams().height = (int) (startHeight + deltaHeight * interpolatedTime);
            view.requestLayout();
        }
    }

    public void setParams(int start, int end) {
        isByPadding = false;
        this.startHeight = start;
        this.deltaHeight = end - startHeight;
    }

    public void setPadding(int startTopPadding, int endTopPadding, int startBottomPadding, int endBottomPadding) {
        isByPadding = true;
        this.startTopPadding = startTopPadding;
        this.deltaTopPadding = endTopPadding - startTopPadding;
        this.startBottomPadding = startBottomPadding;
        this.deltaBottomPadding = endBottomPadding - startBottomPadding;
    }
}
