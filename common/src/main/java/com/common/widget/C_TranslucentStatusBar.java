package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

/**
 * Created by Ziwu on 2016/5/26.
 * <p/>
 * 沉浸式 虚拟状态栏
 * <p/>
 * 自定义View 重写 onMeasure 的重要性;
 * <p/>
 * 问题提出：
 *
 * 在我们自定义view时，如何需要是当前的view内容自适应，这种平常的使用中，
 * 只需要在xml文件中制定宽高或者长高为wrap_content即可，
 *
 * 但是如果该view是我们自定义的，那么此时再在xml文件中指定宽高为wrap_content则不能起到内容自适应的效果，
 * 并且效果为match_parent。本文即是解决此类问题。* http://my.oschina.net/ccqy66/blog/616662 *
 */
public class C_TranslucentStatusBar extends ViewGroup {

    private  int DEFAULT_WIDTH = 0;
    private  int DEFAULT_HEIGHT = 0;
    public C_TranslucentStatusBar(Context context) {
        super(context);
        onInit(context);
    }

    public C_TranslucentStatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit(context);
    }

    public C_TranslucentStatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit(context);
    }

    private void onInit(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        DEFAULT_WIDTH = dm.widthPixels;
//        DEFAULT_HEIGHT   = (int) context.getResources().getDimension(R.dimen.action_bar_height);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = onMeasureDimension(DEFAULT_WIDTH, widthMeasureSpec);
//        int height = onMeasureDimension(44, widthMeasureSpec);
//        setMeasuredDimension(width, height);
//    }

    public int onMeasureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //1. layout给出了确定的值，比如：100dp
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize; //建议：result直接使用确定值
        }
        //1. layout使用的是wrap_content
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize
        }
        //MeasureSpec.UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小
        else {
            result = defaultSize;
        }

        return result;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
