package com.common.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

/**
 * Created by ricky on 2016/06/07.
 * <p/>
 * 动态改变view高度的动画
 */
public class C_TextChangeAnimation extends Animation {

    private String startText; //初始的文字
    private String endText; //最终的文字
    private TextView textView;

    public C_TextChangeAnimation(TextView view) {
        this.textView = view;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime < 0.5) {
            textView.setText(startText);
            textView.setAlpha((float) (1 - interpolatedTime / 0.5));
        } else {
            textView.setText(endText);
            textView.setAlpha((float) ((interpolatedTime - 0.5) / 0.5));
        }
    }

    public void setText(String startText, String endText) {
        this.startText = startText;
        this.endText = endText;
    }
}
