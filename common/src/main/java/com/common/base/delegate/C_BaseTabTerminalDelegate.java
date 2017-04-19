package com.common.base.delegate;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.R;
import com.common.base.adapter.C_FragmentAdapter;
import com.common.base.fragment.C_BaseListFragment2;
import com.common.util.C_DevUtil;
import com.common.util.C_L;
import com.common.util.C_ResUtil;
import com.common.util.C_ViewSetUtils;
import com.common.widget.C_TabIndicator;
import com.common.widget.C_TopBanner;
import com.common.widget.stickylayout.C_StickyFlingListener;
import com.common.widget.stickylayout.C_StickyTabLayout;
import com.common.widget.stickylayout.C_StickyTopBgView;

/**
 * Created by ricky on 2016/08/22.
 * <p/>
 * 终端带tab的delegate基类
 */
public class C_BaseTabTerminalDelegate extends BaseViewDelegate implements C_StickyFlingListener {

    protected FrameLayout llTabTop;
    protected C_TabIndicator indicator;
    protected ViewPager viewpager;
    protected C_TopBanner topBanner;
    protected ImageView ivBgCenter; //topBanner里的
    protected C_StickyTabLayout stickyTabLayout;
    protected C_StickyTopBgView topBgView;
    protected LinearLayout topView;

    protected View dividerTop;
    protected View dividerBottom;

    protected IView viewListener;
    protected String origTitle; //原始的标题
    protected String changeTitle; //变换之后的标题（topView隐藏时显示的）
    protected int topRightRes; //原始的右上角的图标资源

    protected ImageView ivBottomRight; //右下角图片（其他入口）

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_base_tab_terminal;
    }

    @Override
    public void initWidget() {
        topBgView = (C_StickyTopBgView) findViewById(R.id.top_bg_layout);
        topView = (LinearLayout) findViewById(R.id.id_stick_top_view);
        llTabTop = (FrameLayout) findViewById(R.id.id_stick_tab_top_ll);
        indicator = (C_TabIndicator) findViewById(R.id.id_stick_tab_indicator);
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);
        viewpager = (ViewPager) findViewById(R.id.id_stick_tab_viewpager);
        topBanner = (C_TopBanner) findViewById(R.id.top_banner);
        ivBgCenter = (ImageView) topBanner.findViewById(R.id.iv_bg_center);
        stickyTabLayout = (C_StickyTabLayout) findViewById(R.id.sticky_tab_layout);
        ivBottomRight = (ImageView) findViewById(R.id.iv_right_bottom);
        stickyTabLayout.setStickyFlingListener(this);
        //把装载背景的头部图塞进StickyLayout
        stickyTabLayout.setTopBgView(topBgView);
        //初始化topBanner的显示
        topBanner.setBackgroundColor(C_ResUtil.getSrcColor(R.color.full_transparent));
        ivBgCenter.setAlpha(0f);
        //默认时显示dividerTop
        dividerTop.setVisibility(View.GONE);
    }

    /**
     * 设置顶部分割线颜色
     */
    public void setDividerTopBackgroundColor(int color) {
        if (dividerTop != null) {
            dividerTop.setVisibility(View.VISIBLE);
            dividerTop.setBackgroundResource(color);
        }
    }

    /**
     * 设置底部分割线颜色
     */
    public void setDividerBottomBackgroundColor(int color) {
        if (dividerBottom != null) {
//            dividerBottom.setVisibility(View.VISIBLE);
            dividerBottom.setBackgroundResource(color);
        }
    }

    /**
     * 注册头部banner的交互事件
     */
    public void registerTopBannerListener(C_TopBanner.IView viewListener) {
        if (topBanner != null) {
            topBanner.registerViewListener(viewListener);
        }
    }

    /**
     * 隐藏返回按钮（默认显示）
     */
    public void hideTopIvBack() {
        if (topBanner != null) {
            topBanner.getIvLeft().setVisibility(View.GONE);
        }
    }

    /**
     * 设置头部的tabView
     */
    public void setTabView(View view) {
        if (llTabTop != null) {
            llTabTop.removeAllViews();
            llTabTop.addView(view);
        }
    }

    /**
     * 恢复头部的tabView
     */
    public void resetTabView() {
        if (llTabTop != null && indicator != null) {
            llTabTop.removeAllViews();
            llTabTop.addView(indicator);
        }
    }

    /**
     * 设置初始显示的标题
     */
    public void setOrigTitle(String origTitle) {
        this.origTitle = origTitle;
        //设置初始显示的标题
        setTitle(origTitle);
    }

    /**
     * 设置改变之后显示的标题
     */
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }

    /**
     * 设置改变之后显示的标题
     */
    public void setTopRightRes(int topRightRes) {
        this.topRightRes = topRightRes;
        setTopRightImg(topRightRes);
    }

    /**
     * 显示右下角图片
     */
    public void showBottomRight(int bottomRightRes, View.OnClickListener listener) {
        if (ivBottomRight != null) {
            ivBottomRight.setVisibility(View.VISIBLE);
            ivBottomRight.setImageResource(bottomRightRes);
            ivBottomRight.setOnClickListener(listener);
        }
    }

    /**
     * 设置右下角图片边距
     */
    public void setBottomRightMargin(int marginRightDimen, int marginBottomDimen) {
        if (ivBottomRight != null) {
            C_ViewSetUtils.setMarginRight(ivBottomRight, C_ResUtil.getDimens(marginRightDimen));
            C_ViewSetUtils.setMarginBottom(ivBottomRight, C_ResUtil.getDimens(marginBottomDimen));
        }
    }

    /**
     * 隐藏右下角图片
     */
    public void hideBottomRight() {
        if (ivBottomRight != null) {
            ivBottomRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置delegate和presenter交互的接口
     */
    public void registerViewListener(IView viewListener) {
        this.viewListener = viewListener;
    }

    /**
     * 设置头部view的内容部分
     */
    public void setTopViewContent(View view) {
        if (topView != null && view != null) {
            topView.removeAllViews();
            int topBannerHeight = C_ResUtil.getDimens(R.dimen.top_banner_height) + C_DevUtil.getStatusBarHeight(getContext());
            topView.addView(new Space(getContext()), 0, topBannerHeight);
            topView.addView(view);
            topBgView.setOrigHeight(topBannerHeight
                    + view.getLayoutParams().height
                    + C_ResUtil.getDimens(R.dimen.tab_indicator_height) - ivBgMarginBottom);
        }
    }

    private int ivBgMarginBottom = 0;

    /**
     * 设置头部view的背景图片距离底部宽度(需要在setTopViewContent 之前执行才有效)
     */
    public void setTopViewBgMarginBottom(int dimenRes) {
        ivBgMarginBottom = C_ResUtil.getDimens(dimenRes);
    }

    /**
     * 设置头部view的背景图片
     */
    public void setTopViewBgImg(String url) {
        if (topBgView != null) {
            topBgView.setIvBg(url);
        }
    }

    /**
     * 设置头部view的背景图片
     */
    public void setTopViewBgImg(int imgRes) {
        if (topBgView != null) {
            topBgView.setIvBg(imgRes);
        }
    }

    /**
     * 设置头部view的背景图片
     */
    public void setTopViewBgImg(int imgRes, boolean withCover) {
        if (topBgView != null) {
            topBgView.setIvBg(imgRes, withCover);
        }
    }

    /**
     * 设置tab indicator
     */
    public void setTabIndicator(int type) {
        if (indicator != null) {
            indicator.setViewType(type);
        }
    }

    /**
     * 改变背景高度
     *
     * @param dimensId
     */
    public void setTopViewBgHeight(int dimensId) {
        if (topBgView != null) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) topBgView.getLayoutParams();
            ll.height = C_ResUtil.getDimens(dimensId);
            topBgView.setLayoutParams(ll);
        }
    }


    /**
     * 设置头部view的背景颜色
     */
    public void setTopViewBackgroundColor(int color) {
        if (topBgView != null) {
            topBgView.setBackgroundColor(color);
        }
    }

    /**
     * 设置下拉拉伸动画拉伸位置类型
     */
    public void setPullAnimType(int type) {
        if (stickyTabLayout != null) {
            stickyTabLayout.setAnimType(type);
        }
    }

    public void initTabIndicator(C_FragmentAdapter<C_BaseListFragment2> adapter, final String[] titles) {
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager).setTabs(titles);
    }

    /**
     * 添加左右滑动监听事件
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (viewpager != null) {
            viewpager.addOnPageChangeListener(listener);
        }
    }

    @Override
    public void onNestedPreScroll(int topHeight, int scrollY, int dy) {
        C_L.d("C_BaseSingleTerminalDelegate", "topHeight:" + topHeight + ",scrollY:" + scrollY + ",dy:" + dy);
        float f = (float) scrollY / topHeight;
        setBgColorByAnim(f);
        setTitleByAnim(f);
        setTopViewAnim(f);
    }

    @Override
    public void onStopNestedScroll(int topHeight, int scrollY, int dy) {
        float f = (float) scrollY / topHeight;
        setBgColorByAnim(f);
        setTitleByAnim(f);
        setTopViewAnim(f);
    }

    @Override
    public void onRefresh() {
        if (viewListener != null) {
            viewListener.onRefresh(viewpager.getCurrentItem());
        }
    }

    /**
     * 开始刷新
     */
    public void onRefreshStart() {
        startRefreshAnim();
        if (stickyTabLayout != null) {
            stickyTabLayout.onRefreshStart();
        }
    }

    private RotateAnimation refreshAnim;
    private long startRefreshTime;

    /**
     * 开始刷新动画
     */
    private void startRefreshAnim() {
        startRefreshTime = System.currentTimeMillis();
        if (refreshAnim == null) {
            refreshAnim = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.c_rotate_infinite);
        }
        setTopRightImg(R.drawable.c_btn_top_reflash);
        topBanner.getIvRight().startAnimation(refreshAnim);
    }

    /**
     * 结束刷新
     */
    public void onRefreshEnd() {
        if (System.currentTimeMillis() - startRefreshTime > 500) {
            endRefreshAnim();
            if (stickyTabLayout != null) {
                stickyTabLayout.onRefreshEnd();
            }
        } else {
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endRefreshAnim();
                    if (stickyTabLayout != null) {
                        stickyTabLayout.onRefreshEnd();
                    }
                }
            }, (500 + startRefreshTime - System.currentTimeMillis()));
        }
    }

    /**
     * 结束刷新动画
     */
    private void endRefreshAnim() {
        topBanner.getIvRight().clearAnimation();
        setTopRightImg(topRightRes);
    }

    private ObjectAnimator bgColorAnim;

    private void setBgColorByAnim(float f) {
        if (bgColorAnim == null) {
            bgColorAnim = ObjectAnimator.ofInt(topBanner, "backgroundColor", C_ResUtil.getSrcColor(R.color.full_transparent), C_ResUtil.getSrcColor(R.color.bg_top_banner));
            bgColorAnim.setEvaluator(new ArgbEvaluator());
            bgColorAnim.setDuration(1000);
        }
        bgColorAnim.setCurrentPlayTime((long) (f * 1000));
        topBanner.setBackgroundColor((Integer) bgColorAnim.getAnimatedValue());
        ivBgCenter.setAlpha(f);
    }

    protected void setTitleByAnim(float f) {
        if (TextUtils.isEmpty(changeTitle)) {
            return;
        }
        if (f < 0.5) {
            setTitle(origTitle);
            setTitleAlpha((float) (1 - f / 0.5));
        } else {
            setTitle(changeTitle);
            setTitleAlpha((float) ((f - 0.5) / 0.5));
        }
    }

    //是否带头部为隐藏动画
    private boolean withTopViewHideAnim = true;

    public void setWithTopViewHideAnim(boolean withTopViewHideAnim) {
        this.withTopViewHideAnim = withTopViewHideAnim;
    }

    /**
     * 添加的头部view隐藏动画
     */
    protected void setTopViewAnim(float f) {
        if (topView != null && withTopViewHideAnim) {
            topView.setAlpha(1 - f);
        }
    }

    /**
     * 设置标题
     */
    private void setTitle(String title) {
        if (topBanner != null) {
            topBanner.getTvCenter().setText(title);
        }
    }

    /**
     * 设置标题
     */
    private void setTitleAlpha(float f) {
        if (topBanner != null) {
            topBanner.getTvCenter().setAlpha(f);
        }
    }

    /**
     * 设置标题
     */
    private void setTopRightImg(int res) {
        if (topBanner != null && res > 0) {
            topBanner.getIvRight().setVisibility(View.VISIBLE);
            topBanner.getIvRight().setImageResource(res);
        }
    }

    public interface IView {

        void onRefresh(int position);
    }

    public C_TopBanner getTopBanner() {
        return topBanner;
    }

    public void setTopBanner(C_TopBanner topBanner) {
        this.topBanner = topBanner;
    }
}
