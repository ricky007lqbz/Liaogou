package com.common.base.delegate;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.common.R;
import com.common.util.C_DevUtil;
import com.common.util.C_L;
import com.common.util.C_ResUtil;
import com.common.widget.C_TopBanner;
import com.common.widget.stickylayout.C_StickyFlingListener;
import com.common.widget.stickylayout.C_StickySingleLayout;
import com.common.widget.stickylayout.C_StickyTopBgView;

/**
 * Created by ricky on 2016/08/22.
 * <p>
 * 终端带tab的delegate基类
 */
public class C_BaseSingleTerminalDelegate extends BaseViewDelegate implements C_StickyFlingListener {

    C_TopBanner topBanner;
    C_StickySingleLayout stickySingleLayout;
    C_StickyTopBgView topBgView;
    LinearLayout topView;
    LinearLayout llBottomView;

    private IView viewListener;
    private String origTitle; //原始的标题
    private String changeTitle; //变换之后的标题（topView隐藏时显示的）
    private int topRightRes; //原始的右上角的图标资源

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_base_single_terminal;
    }

    @Override
    public void initWidget() {
        topBanner = (C_TopBanner) findViewById(R.id.top_banner);
        stickySingleLayout = (C_StickySingleLayout) findViewById(R.id.sticky_single_layout);
        topBgView = (C_StickyTopBgView) findViewById(R.id.top_bg_layout);
        topView = (LinearLayout) findViewById(R.id.id_stick_top_view);
        llBottomView = (LinearLayout) findViewById(R.id.ll_bottom_out);
        stickySingleLayout.setStickyFlingListener(this);
        //把装载背景的头部图塞进StickyLayout
        stickySingleLayout.setTopBgView(topBgView);
        //初始化topBanner的显示
        topBanner.setBackgroundColor(C_ResUtil.getSrcColor(R.color.full_transparent));
        topBanner.findViewById(R.id.iv_bg_center).setVisibility(View.GONE);
    }

    public void registerTopBanner(C_TopBanner.IView viewListener) {
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
     * 设置右上角刷新图片的默认显示图片
     */
    public void setIvTopRightRes(int topRightRes) {
        this.topRightRes = topRightRes;
        setIvTopRightImg(topRightRes);
    }

    /**
     * 显示右上角红点标记
     */
    public void showTopRightRedPoint(boolean visible) {
        if (topBanner != null) {
            topBanner.showRedPoint(visible);
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
            topBgView.setOrigHeight(view.getLayoutParams().height
                    + topBannerHeight
                    + C_ResUtil.getDimens(R.dimen._28dp));
        }
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
     * 设置头部view的背景颜色
     */
    public void setTopViewBackgroundColor(int color) {
        if (topBgView != null) {
            topBgView.setBackgroundColor(color);
        }
    }

    /**
     * 添加外部固定的底部视图
     */
    public void addOutBottomView(View view) {
        if (llBottomView != null) {
            llBottomView.removeAllViews();
            llBottomView.addView(view);
        }
    }

    /**
     * 替换底部的fragment
     */
    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(R.id.id_stick_single_fragment, fragment, fragment.getClass().getName());
            ft.show(fragment);
        }
        ft.commit();
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
            viewListener.onRefresh();
        }
    }

    /**
     * 开始刷新
     */
    public void onRefreshStart() {
        startRefreshAnim();
        if (stickySingleLayout != null) {
            stickySingleLayout.onRefreshStart();
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
        topBanner.getIvRight().setVisibility(View.VISIBLE);
        topBanner.getIvRight().setImageResource(R.drawable.c_btn_top_reflash);
        topBanner.getIvRight().startAnimation(refreshAnim);
    }

    /**
     * 结束刷新
     */
    public void onRefreshEnd() {
        if (System.currentTimeMillis() - startRefreshTime > 500) {
            endRefreshAnim();
            if (stickySingleLayout != null) {
                stickySingleLayout.onRefreshEnd();
            }
        } else {
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endRefreshAnim();
                    if (stickySingleLayout != null) {
                        stickySingleLayout.onRefreshEnd();
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
        setIvTopRightImg(topRightRes);
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
    }

    private void setTitleByAnim(float f) {
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

    /**
     * 添加的头部view隐藏动画
     */
    protected void setTopViewAnim(float f) {
        if (topView != null) {
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
    private void setIvTopRightImg(int res) {
        if (topBanner != null && res > 0) {
            topBanner.getIvRight().setVisibility(View.VISIBLE);
            topBanner.getIvRight().setImageResource(res);
        }
    }

    public void showIvTopRight(boolean visible) {
        if (topBanner != null) {
            topBanner.getIvRight().setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置标题
     */
    public void setIvTopRightSecondRes(int res) {
        if (topBanner != null && res > 0) {
            topBanner.getIvRightSecond().setVisibility(View.VISIBLE);
            topBanner.getIvRightSecond().setImageResource(res);
        }
    }

    public void showIvTopRightSecond(boolean visible) {
        if (topBanner != null) {
            topBanner.getIvRightSecond().setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public interface IView {

        void onRefresh();
    }
}
