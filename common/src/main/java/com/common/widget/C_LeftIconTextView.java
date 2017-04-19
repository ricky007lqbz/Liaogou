package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_DevUtil;
import com.common.util.C_ResUtil;

/**
 * Created by ricky on 2016/10/20.\
 * <p>
 * 左边固定带 icon 的文字
 */

public class C_LeftIconTextView extends LinearLayout {

    private ImageView ivLeft;
    private TextView textView;

    private int ivLeftResId;
    private int textColor;
    private float textSize;
    private CharSequence text;
    private boolean isSingleLine;

    public C_LeftIconTextView(Context context) {
        super(context);
        init(context, null);
    }

    public C_LeftIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public C_LeftIconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        /**
         * 获取自定义的样式属性
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.C_LeftIconTextView);
        this.ivLeftResId = ta.getResourceId(R.styleable.C_LeftIconTextView_ivLeft, R.drawable.c_icon_c1_left_small);
        this.textColor = ta.getColor(R.styleable.C_LeftIconTextView_textColor, C_ResUtil.getSrcColor(R.color.text_second));
        float textSizeTemp = ta.getDimension(R.styleable.C_LeftIconTextView_textSize, C_ResUtil.getDimens(R.dimen.text_12));
        this.textSize = textSizeTemp / C_DevUtil.getDensity(context);
        this.text = ta.getText(R.styleable.C_LeftIconTextView_text);
        this.isSingleLine = ta.getBoolean(R.styleable.C_LeftIconTextView_singleLine, false);
        ta.recycle();
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            getAttr(context, attrs);
        }
        inflate(context, R.layout.c_layout_left_icon_text, this);
        setGravity(Gravity.CENTER_VERTICAL);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        textView = (TextView) findViewById(R.id.tv_text);
        setIvLeft(ivLeftResId);
        setText(text);
        setTextSize(textSize);
        setTextColor(textColor);
        setTextSingleLine(isSingleLine, true);
    }

    public void setIvLeft(int resId) {
        if (ivLeft != null) {
            if (resId <= 0) {
                ivLeft.setVisibility(GONE);
            } else {
                ivLeft.setVisibility(VISIBLE);
                ivLeft.setImageResource(resId);
            }
        }
    }

    public void setText(CharSequence text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    public void setTextColor(int color) {
        if (textView != null) {
            textView.setTextColor(color);
        }
    }

    public void setTextSize(float size) {
        if (textView != null) {
            textView.setTextSize(size);
        }
    }

    public void setTextSingleLine(boolean isSingleLine, boolean withEllipsize) {
        if (textView != null) {
            textView.setSingleLine(isSingleLine);
            if (isSingleLine && withEllipsize) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
            } else {
                textView.setEllipsize(null);
            }
        }
    }
}
