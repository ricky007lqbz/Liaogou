package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.common.R;

/**
 * Created by ricky on 2016/04/07.
 * <p/>
 * 背景圆形的数字view
 */
public class C_BgRoundNumView extends View {

    private String mNum;
    private int mNumColor;
    private int mNumSize;
    private int mBackColor;
    private int mNumType;

    private Rect mBound;
    private Paint mPaint;

    public C_BgRoundNumView(Context context) {
        this(context, null);
    }

    public C_BgRoundNumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public C_BgRoundNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获取自定义的样式属性
         */
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.C_BgRoundNumView, defStyleAttr, 0);
        int attrsSize = array.getIndexCount();

        for (int i = 0; i < attrsSize; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.C_BgRoundNumView_num) {
                mNum = array.getString(attr);
            } else if (attr == R.styleable.C_BgRoundNumView_numColor) {
                //设置文字默认颜色为黑色
                mNumColor = array.getColor(attr, Color.WHITE);
            } else if (attr == R.styleable.C_BgRoundNumView_numSize) {
                //设置文字默认字体为14sp
                mNumSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.C_BgRoundNumView_backColor) {
                //设置背景默认颜色为黑色
                mBackColor = array.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.C_BgRoundNumView_numType) {
                //设置背景默认颜色为黑色
                mNumType = array.getInt(attr, 0);
                switch (mNumType) {
                    case 0:
                        mNumType = Typeface.NORMAL;
                        break;
                    case 1:
                        mNumType = Typeface.BOLD;
                        break;
                    case 2:
                        mNumType = Typeface.ITALIC;
                        break;
                    case 3:
                        mNumType = Typeface.BOLD_ITALIC;
                        break;
                }
                break;
            }
        }
        array.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mNumSize);
        mBound = new Rect();
        mPaint.getTextBounds(mNum, 0, mNum.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getMeasuredWidth() != getMeasuredHeight()) {
            throw new IllegalStateException("height must equals width");
        }

        mPaint.setColor(mBackColor);
        int radius = getMeasuredWidth() / 2;
        canvas.drawCircle(radius, radius, radius, mPaint);

        mPaint.setColor(mNumColor);
        mPaint.setTypeface(Typeface.defaultFromStyle(mNumType));
        canvas.drawText(mNum, 0, mNum.length(),
                radius - (mBound.right + mBound.left) / 2, radius - (mBound.bottom + mBound.top) / 2,
                mPaint);
    }

    public void setmNum(String mNum) {
        this.mNum = mNum;
        mPaint.getTextBounds(mNum, 0, mNum.length(), mBound);
        invalidate();
    }

    public void setmBackColor(int mBackColor) {
        this.mBackColor = mBackColor;
        invalidate();
    }

    public void setmNumColor(int mNumColor) {
        this.mNumColor = mNumColor;
        invalidate();
    }
}
