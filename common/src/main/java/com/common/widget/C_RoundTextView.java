package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_ResUtil;


public class C_RoundTextView extends TextView {
    private final String TAG = "RoundTextView";
    private Paint mPaint;
    private int mColor;

    public C_RoundTextView(Context context) {
        this(context, null);
    }

    public C_RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.C_RoundTextView);
        mColor = type.getColor(R.styleable.C_RoundTextView_background_color, C_ResUtil.getSrcColor(android.R.color.transparent));
        type.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setGravity(Gravity.CENTER);
        ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() + getPaddingRight() + getPaddingLeft();
        int height = getHeight() + getPaddingTop() + getPaddingBottom();
        mPaint.setColor(mColor);
        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);
        super.onDraw(canvas);
    }


    public void setBackGroundColor(int color) {
        this.mColor = color;
        invalidate();
    }

}
