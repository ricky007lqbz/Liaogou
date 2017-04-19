package com.common.widget;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 按下的时候产生颜色过滤的ImageView
 * 默认按下时为图片变暗
 * 需要按下时候有其他效果，继承PressedImageView，并重写getColorMatrixColorFilter（）方法
 * @author Administrator
 */
public class C_PressedImageView extends ImageView {

    //颜色矩阵
    private final static ColorMatrix mColorMatrix = new ColorMatrix(new float[] {
            0.8f, 0, 0, 0, 0.1f, // ---> R（红色）的向量
            0,0.8f, 0, 0, 0, // ---> G（绿色）的向量
            0, 0, 0.8f, 0, 0, // ---> B（蓝色）的向量
            0, 0, 0, 1, 0, // ---> A（透明度）的向量
    });

    //颜色过滤器
    private ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(
            mColorMatrix);

    public C_PressedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPressed(boolean pressed) {
        if(pressed){
            setColorFilter(getColorMatrixColorFilter());
            invalidate();
        }else {
            setColorFilter(null);
            invalidate();
        }
        super.setPressed(pressed);
    }

    public ColorMatrixColorFilter getColorMatrixColorFilter(){
        return mColorFilter;
    }
}
