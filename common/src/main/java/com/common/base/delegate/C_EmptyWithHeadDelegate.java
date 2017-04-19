package com.common.base.delegate;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.common.R;
import com.common.util.C_ResUtil;
import com.common.util.C_ViewSetUtils;
import com.common.widget.C_ProgressLayout;

/**
 * Created by ricky on 2016/10/21.
 * <p>
 * 空视图
 */

public class C_EmptyWithHeadDelegate extends BaseViewDelegate {

    private TextView tvHead;
    private C_ProgressLayout progressLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_empty_with_head;
    }

    @Override
    public void initWidget() {
        progressLayout = (C_ProgressLayout) findViewById(R.id.progress_layout);
        tvHead = (TextView) findViewById(R.id.tv_head);
    }

    public void setDelegateBgResId(int resId) {
        if (getRootView() != null) {
            getRootView().setBackgroundResource(resId);
        }
    }

    public void setDelegateMargin(int leftDimen, int topDimen, int rightDimen, int bottomDimen) {
        if (getRootView() != null) {
            C_ViewSetUtils.setMargins(getRootView(),
                    C_ResUtil.getDimens(leftDimen),
                    C_ResUtil.getDimens(topDimen),
                    C_ResUtil.getDimens(rightDimen),
                    C_ResUtil.getDimens(bottomDimen));
        }
    }

    public void setTvHeadText(CharSequence title) {
        if (tvHead != null) {
            tvHead.setText(title);
        }
    }

    public void showEmpty(int imgResId, @Nullable String content,
                          @Nullable String btnContent,
                          @Nullable View.OnClickListener onIvClickListener,
                          @Nullable View.OnClickListener onBtnClickListener) {
        if (progressLayout != null) {
            if (TextUtils.isEmpty(btnContent)) {
                getRootView().getLayoutParams().height = C_ResUtil.getDimens(R.dimen._236dp);
            } else {
                getRootView().getLayoutParams().height = C_ResUtil.getDimens(R.dimen._300dp);
            }
            progressLayout.showEmpty(imgResId, content, btnContent, onIvClickListener, onBtnClickListener);
            progressLayout.setImageViewMarginTop(R.dimen._30dp);
            progressLayout.setImageViewSize(R.dimen._90dp, R.dimen._90dp);
            progressLayout.setTextViewSize(R.dimen.text_12);
        }
    }

    public void showError(int imgResId, String msg, View.OnClickListener listener) {
        if (progressLayout != null) {
            getRootView().getLayoutParams().height = C_ResUtil.getDimens(R.dimen._236dp);
            progressLayout.showError(imgResId, msg, listener);
            progressLayout.setImageViewMarginTop(R.dimen._30dp);
            progressLayout.setImageViewSize(R.dimen._90dp, R.dimen._90dp);
            progressLayout.setTextViewSize(R.dimen.text_12);
        }
    }
}
