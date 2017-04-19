package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_BtnClickUtils;
import com.common.util.C_DevUtil;
import com.common.util.C_ResUtil;

/**
 * Created by Ziwu on 2016/5/18.
 * <p>
 * 通用ActionBar -> 沿用原来的topBanner叫法。
 * <p>
 * BaseToolBar只用在推拉操作后显示在顶部；
 */
public class C_TopBanner extends RelativeLayout implements View.OnClickListener {

    ImageView ivLeft;
    ImageView ivCenter;
    ImageView ivBgCenter;
    TextView tvLeft;
    TextView tvCenter;
    ImageView ivRight;
    TextView tvRight;
    C_PressedImageView ivRightSecond;
    ImageView ivRegPoint;
    View barFix;

    private IView viewListener;

    public C_TopBanner(Context context) {
        this(context, null);
    }

    public C_TopBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public C_TopBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.c_layout_top_banner, this);
        //设置根根布局的属性
        setBackgroundColor(C_ResUtil.getSrcColor(R.color.bg_top_banner));

        ivLeft = (ImageView) findViewById(R.id.iv_left);
        ivCenter = (ImageView) findViewById(R.id.iv_center);
        ivBgCenter = (ImageView) findViewById(R.id.iv_bg_center);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRightSecond = (C_PressedImageView) findViewById(R.id.iv_right_second);
        ivRegPoint = (ImageView) findViewById(R.id.iv_red_point);
        barFix = findViewById(R.id.bar_fix);

        barFix.getLayoutParams().height = C_DevUtil.getStatusBarHeight(getContext());

        tvCenter.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        ivRightSecond.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 设置中间图片
     */
    public void setIvCenter(int resId) {
        if (ivCenter != null && tvCenter != null) {
            ivCenter.setImageResource(resId);
            ivCenter.setVisibility(VISIBLE);
            tvCenter.setVisibility(GONE);
        }
    }

    /**
     * 是否显示中间的背景图片
     */
    public void showIvBgCenter(boolean show) {
//        if (ivBgCenter != null) {
//            ivBgCenter.setVisibility(show ? VISIBLE : GONE);
//        }
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public void setTvCenter(CharSequence title) {
        if (tvCenter != null && ivCenter != null) {
            tvCenter.setVisibility(VISIBLE);
            ivCenter.setVisibility(GONE);
            tvCenter.setText(title);
            tvCenter.post(new Runnable() {
                @Override
                public void run() {
                    if (tvCenter.getLineCount() > 1) {
                        tvCenter.setTextSize(15);
                    } else {
                        tvCenter.setTextSize(17);
                    }
                }
            });
        }
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public C_PressedImageView getIvRightSecond() {
        return ivRightSecond;
    }

    public void showRedPoint(boolean visible) {
        if (ivRight == null || ivRegPoint == null) {
            return;
        }
        if (ivRight.getVisibility() == VISIBLE) {
            ivRegPoint.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (viewListener == null) {
            return;
        }
        if (view == tvCenter) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onTvCenterPress();
            }
        }
        if (view == ivLeft) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onIvLeftPress();
            }
        } else if (view == tvLeft) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onTvLefPress();
            }
        } else if (view == ivRight) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onIvRightPress();
            }
        } else if (view == tvRight) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onTvRightPress();
            }
        } else if (view == ivRightSecond) {
            if (!C_BtnClickUtils.isFastDoubleClick()) {
                viewListener.onIvRightSecondPress();
            }
        }
    }

    public void registerViewListener(IView viewListener) {
        this.viewListener = viewListener;
    }

    public interface IView {

        void onTvCenterPress();

        void onIvLeftPress();

        void onTvLefPress();

        void onIvRightPress();

        void onTvRightPress();

        void onIvRightSecondPress();
    }
}
