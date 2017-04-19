package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.common.R;

/**
 * Created by Ziwu on 2016/5/26.
 * <p/>
 * 沉浸式  虚拟导航栏
 */
public class C_TranslucentNavigationBar extends ViewGroup {
    private int DEFAULT_WIDTH = 0;
    private int DEFAULT_HEIGHT = 0;

    public C_TranslucentNavigationBar(Context context) {
        super(context);
        onInit(context);
    }

    public C_TranslucentNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit(context);
    }

    public C_TranslucentNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit(context);
    }

    private void onInit(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        DEFAULT_WIDTH = dm.widthPixels;
        DEFAULT_HEIGHT = (int) context.getResources().getDimension(R.dimen.navigation_bar_height);
    }

    public int onMeasureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            //建议：result直接使用确定值
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            //建议：result不能大于specSize
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
    }
}
