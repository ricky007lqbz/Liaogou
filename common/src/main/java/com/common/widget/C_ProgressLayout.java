package com.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_ResUtil;
import com.common.util.C_ViewSetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的用来显示不同网络状态下的自定义ViewGroup
 */
public class C_ProgressLayout extends RelativeLayout {

    private static final String LOADING_TAG = "ProgressLayout.LOADING_TAG";
    private static final String ERROR_TAG = "ProgressLayout.ERROR_TAG";
    private static final String EMPTY_TAG = "ProgressLayout.EMPTY_TAG";
    private static final String OTHER_EMPTY_TAG = "ProgressLayout.OTHER_EMPTY_TAG";

    private LayoutParams layoutParams;
    private View loadingGroup;
    private View errorGroup;
    private View emptyGroup;


    private RelativeLayout loadingLayout;
    private RelativeLayout errorLayout;
    private RelativeLayout emptyLayout;
    private RelativeLayout otherEmptyLayout;
    private TextView errorTextView;
    private ImageView errorIv;
    private TextView emptyTextView;
    private ImageView emptyIv;
    private TextView emptyBottomBtn;

    private List<View> contentViews = new ArrayList<>();

    private enum State {
        LOADING, CONTENT, ERROR, EMPTY
    }

    private State currentState = State.LOADING;

    public C_ProgressLayout(Context context) {
        super(context);
    }

    public C_ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public C_ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(LOADING_TAG)
                && !child.getTag().equals(ERROR_TAG)
                && !child.getTag().equals(EMPTY_TAG)
                && !child.getTag().equals(OTHER_EMPTY_TAG))) {
            contentViews.add(child);
        }
    }

    public void showLoading() {
        currentState = State.LOADING;
        C_ProgressLayout.this.showLoadingView();
        C_ProgressLayout.this.hideErrorView();
        C_ProgressLayout.this.hideEmptyView();
        C_ProgressLayout.this.hideOtherEmptyView();
        C_ProgressLayout.this.setContentVisibility(false);
    }

    public void showContent() {
        currentState = State.CONTENT;
        C_ProgressLayout.this.hideLoadingView();
        C_ProgressLayout.this.hideErrorView();
        C_ProgressLayout.this.hideEmptyView();
        C_ProgressLayout.this.hideOtherEmptyView();
        C_ProgressLayout.this.setContentVisibility(true);
    }

    public void showError(int imgResId, String msg, @NonNull OnClickListener onClickListener) {
        currentState = State.ERROR;
        C_ProgressLayout.this.showErrorView();
        C_ProgressLayout.this.hideLoadingView();
        C_ProgressLayout.this.hideEmptyView();
        C_ProgressLayout.this.hideOtherEmptyView();

        errorTextView.setText(msg);
        errorIv.setImageResource(imgResId);
        errorIv.setOnClickListener(onClickListener);
        C_ProgressLayout.this.setContentVisibility(false);
    }

    public void showEmpty(int imgResId, String content,
                           String btnContent,
                           OnClickListener onIvClickListener,
                           OnClickListener onBtnClickListener) {
        currentState = State.EMPTY;
        C_ProgressLayout.this.showEmptyView();
        C_ProgressLayout.this.hideLoadingView();
        C_ProgressLayout.this.hideErrorView();
        C_ProgressLayout.this.hideOtherEmptyView();

        emptyIv.setImageResource(imgResId);
        emptyTextView.setText(content);
        emptyIv.setOnClickListener(onIvClickListener);
        if (!TextUtils.isEmpty(btnContent)) {
            emptyBottomBtn.setVisibility(VISIBLE);
            emptyBottomBtn.setText(btnContent);
            emptyBottomBtn.setOnClickListener(onBtnClickListener);
        } else {
            emptyBottomBtn.setVisibility(GONE);
        }
        C_ProgressLayout.this.setContentVisibility(false);
    }


    public void showOtherEmpty(View view) {
        currentState = State.EMPTY;
        C_ProgressLayout.this.showOtherEmptyView(view);
        C_ProgressLayout.this.hideLoadingView();
        C_ProgressLayout.this.hideErrorView();
        C_ProgressLayout.this.hideEmptyView();
        C_ProgressLayout.this.setContentVisibility(false);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (errorIv != null) {
            errorIv.setOnClickListener(null);
        }
        if (emptyIv != null) {
            emptyIv.setOnClickListener(null);
        }
    }

    public void setImageViewMarginTop(int dimenRes) {
        if (emptyIv != null) {
            C_ViewSetUtils.setMarginTop(emptyIv, C_ResUtil.getDimens(dimenRes));
        }
        if (errorIv != null) {
            C_ViewSetUtils.setMarginTop(errorIv, C_ResUtil.getDimens(dimenRes));
        }
    }

    public void setImageViewSize(int withDimen, int heightDimen) {
        if (emptyIv != null) {
            emptyIv.getLayoutParams().width = C_ResUtil.getDimens(withDimen);
            emptyIv.getLayoutParams().height = C_ResUtil.getDimens(heightDimen);
        }
        if (errorIv != null) {
            errorIv.getLayoutParams().width = C_ResUtil.getDimens(withDimen);
            errorIv.getLayoutParams().height = C_ResUtil.getDimens(heightDimen);
        }
    }

    public void setTextViewSize(int dimenRes) {
        if (emptyTextView != null) {
            emptyTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, C_ResUtil.getDimens(dimenRes));
        }
        if (errorTextView != null) {
            errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, C_ResUtil.getDimens(dimenRes));
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public boolean isContent() {
        return currentState == State.CONTENT;
    }

    public boolean isLoading() {
        return currentState == State.LOADING;
    }

    public boolean isError() {
        return currentState == State.ERROR;
    }

    public boolean isEmpty() {
        return currentState == State.EMPTY;
    }

    /**
     * 显示其他的空view
     *
     * @param view 需要显示的空view
     */
    private void showOtherEmptyView(View view) {
        if (otherEmptyLayout == null) {
            otherEmptyLayout = new RelativeLayout(getContext());
            otherEmptyLayout.setTag(OTHER_EMPTY_TAG);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            C_ProgressLayout.this.addView(otherEmptyLayout, layoutParams);
        } else {
            otherEmptyLayout.setVisibility(VISIBLE);
        }
        otherEmptyLayout.removeAllViews();
        otherEmptyLayout.addView(view, layoutParams);
    }

    /**
     * 显示空view
     */
    private void showEmptyView() {
        if (emptyGroup == null) {
            emptyGroup = View.inflate(getContext(), R.layout.c_progress_empty_view, null);
            emptyLayout = (RelativeLayout) emptyGroup.findViewById(R.id.emptyStateRelativeLayout);
            emptyLayout.setTag(EMPTY_TAG);

            emptyTextView = (TextView) emptyGroup.findViewById(R.id.tv_empty);
            emptyIv = (ImageView) emptyGroup.findViewById(R.id.progress_empty_img);
            emptyBottomBtn = (TextView) emptyGroup.findViewById(R.id.tv_btn_bottom);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            C_ProgressLayout.this.addView(emptyLayout, layoutParams);
        } else {
            emptyLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示加载中的视图
     */
    private void showLoadingView() {
        if (loadingGroup == null) {
            loadingGroup = View.inflate(getContext(), R.layout.c_progress_loading_view, null);
            loadingLayout = (RelativeLayout) loadingGroup.findViewById(R.id.loadingStateRelativeLayout);
            loadingLayout.setTag(LOADING_TAG);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            C_ProgressLayout.this.addView(loadingLayout, layoutParams);
        } else {
            loadingLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示加载错误的视图
     */
    private void showErrorView() {
        if (errorGroup == null) {
            errorGroup = View.inflate(getContext(), R.layout.c_progress_error_layout, null);
            errorLayout = (RelativeLayout) errorGroup.findViewById(R.id.progress_error_layout_rl);
            errorLayout.setTag(ERROR_TAG);

            errorTextView = (TextView) errorGroup.findViewById(R.id.progress_error_tv);
            errorIv = (ImageView) errorGroup.findViewById(R.id.progress_error_img);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            C_ProgressLayout.this.addView(errorLayout, layoutParams);
        } else {
            errorLayout.setVisibility(VISIBLE);
        }
    }

    private void hideLoadingView() {
        if (loadingLayout != null && loadingLayout.getVisibility() != GONE) {
            loadingLayout.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorLayout != null && errorLayout.getVisibility() != GONE) {
            errorLayout.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyLayout != null && emptyLayout.getVisibility() != GONE) {
            emptyLayout.setVisibility(GONE);
        }
    }

    private void hideOtherEmptyView() {
        if (otherEmptyLayout != null && otherEmptyLayout.getVisibility() != GONE) {
            otherEmptyLayout.setVisibility(GONE);
        }
    }

    private void setContentVisibility(boolean visible) {
        for (View contentView : contentViews) {
            contentView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}