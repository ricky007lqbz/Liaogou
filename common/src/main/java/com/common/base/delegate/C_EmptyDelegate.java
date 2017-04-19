package com.common.base.delegate;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.widget.C_ProgressLayout;

/**
 * Created by ricky on 2016/10/21.
 * <p>
 * 空视图
 */

public class C_EmptyDelegate extends BaseViewDelegate {

    private C_ProgressLayout progressLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.c_delegate_empty;
    }

    @Override
    public void initWidget() {
        progressLayout = (C_ProgressLayout) findViewById(R.id.progress_layout);
    }

    public void showEmpty(int imgResId, @Nullable String content,
                          @Nullable String btnContent,
                          @Nullable View.OnClickListener onIvClickListener,
                          @Nullable View.OnClickListener onBtnClickListener) {
        if (progressLayout != null) {
            progressLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            progressLayout.showEmpty(imgResId, content, btnContent, onIvClickListener, onBtnClickListener);
        }
    }

    public void hide() {
        if (progressLayout != null) {
            progressLayout.getLayoutParams().height = 0;
        }
    }

    public void showError(int imgResId, String msg, View.OnClickListener listener) {
        if (progressLayout != null) {
            progressLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            progressLayout.showError(imgResId, msg, listener);
        }
    }
}
