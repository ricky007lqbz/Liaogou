package com.common.base.delegate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.Common;
import com.common.base.listener.C_BaseIDelegateView;
import com.common.util.C_ToastUtil;
import com.common.util.C_ViewSetUtils;
import com.common.util.glide.C_ImageLoader;
import com.common.widget.C_LeftIconTextView;

import butterknife.ButterKnife;

/**
 * Created by ricky on 2016/05/23.
 * <p/>
 * 视图层代理的基类
 */
public abstract class BaseViewDelegate implements C_BaseIDelegateView {

    protected View rootView;

    public Context context;

    public BaseViewDelegate() {
    }

    public BaseViewDelegate(View rootView) {
        this.context = rootView.getContext();
        this.rootView = rootView;
        ButterKnife.bind(this, rootView);
    }

    public abstract int getRootLayoutId();

    @Override
    public void onCreate(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = context;
        rootView = inflater.inflate(getRootLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initWidget();
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        this.rootView = null;
        this.context = null;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public Context getContext() {
        if (rootView != null) {
            return rootView.getContext();
        } else {
            throw new IllegalStateException("rootView did not initialize!");
        }
    }

    protected View findViewById(int id) {
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
    }

    public void setOnClickListener(View.OnClickListener listener, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 显示默认的短时Toast
     *
     * @param msg toast信息
     */
    public void showToast(String msg) {
        C_ToastUtil.showSingle(msg);
    }


    public boolean hasValue(EditText editText) {
        return editText.getText().toString().trim().length() > 0;
    }

    public boolean hasValue(TextView editText) {
        return editText.getText().toString().trim().length() > 0;
    }

    protected void setText(@NonNull TextView tv, String text) {
        setText(tv, text, "");
    }

    protected void setText(@NonNull C_LeftIconTextView tv, String text) {
        setText(tv, text, "");
    }

    protected void setText(@NonNull TextView tv, String text, String dfText) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setText(dfText);
        }
    }

    protected void setText(@NonNull C_LeftIconTextView tv, String text, String dfText) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setText(dfText);
        }
    }

    protected void setTextDfGone(@NonNull TextView tv, String text) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    protected void setTextDfGone(@NonNull C_LeftIconTextView tv, String text) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    protected void setCircleImg(@NonNull ImageView iv, String url, int defResId) {
        C_ImageLoader.setCircleImageByUrl(context, iv, url, defResId);
    }

    protected void setCircleImg(@NonNull ImageView iv, Uri uri, int defResId) {
        C_ImageLoader.setCircleImageByUri(context, iv, uri, defResId);
    }

    protected void setImg(@NonNull ImageView iv, int resId) {
        if (resId > 0) {
            iv.setImageResource(resId);
        } else {
            iv.setImageResource(Common.image.DF_IMG);
        }
    }

    protected void setImg(@NonNull ImageView iv, String url) {
        C_ImageLoader.setImageByUrlCenterCrop(context, iv, url, Common.image.DF_IMG);
    }

    protected void setImg(@NonNull ImageView iv, String url, int drawable) {
        C_ImageLoader.setImageByUrlCenterCrop(context, iv, url, drawable);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * application 级别的 context  app /activity /fragment.
     */
    public Context getAppContext() {
        return context;
    }

    /**
     * 隐藏
     */
    public void hide() {
        if (getRootView() != null) {
            getRootView().getLayoutParams().height = 0;
            C_ViewSetUtils.setMargins(rootView, 0, 0, 0, 0);
        }
    }

    /**
     * 显示
     */
    public void show() {
        if (getRootView() != null) {
            getRootView().getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }

    public interface IView {
        //查看全部
        void onCheckAllClick(String type);
    }
}
