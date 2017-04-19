package com.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;

/**
 * Created by ricky on 2016/08/19.
 * <p>
 * ListView/GridView/RecyclerView 分页加载时使用到的LoadMoreView
 */
public class C_LoadMoreView extends RelativeLayout {

    protected State mState = State.NORMAL;
    private OnClickListener errorListener;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private ProgressBar mLoadingProgress;
    private TextView mLoadingText;

    private CharSequence endString;

    public C_LoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public C_LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public C_LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.c_list_footer, this);
        setOnClickListener(null);
        setState(State.NORMAL, true);
    }

    public void registerErrorLisenter(OnClickListener listener) {
        this.errorListener = listener;
    }

    public State getState() {
        return mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    public void setEndString(CharSequence endString) {
        this.endString = endString;
    }

    /**
     * 设置状态
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;
        switch (status) {
            case NORMAL:
                setOnClickListener(null);
                setViewVisible(null);
                break;
            case LOADING:
                setOnClickListener(null);
                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
                }
                setViewVisible(mLoadingView);
                mLoadingView.setVisibility(showView ? VISIBLE : GONE);
                mLoadingProgress.setVisibility(View.VISIBLE);
                mLoadingText.setText(R.string.list_footer_loading);
                break;
            case END:
                setOnClickListener(null);
                if (mTheEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.end);
                    mTheEndView = viewStub.inflate();
                }
                setViewVisible(mTheEndView);
                if (showView) {
                    mTheEndView.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(endString)) {
                        ((TextView) mTheEndView.findViewById(R.id.loading_text)).setText(endString);
                    }
                } else {
                    mTheEndView.setVisibility(GONE);
                }
                break;
            case ERROR:
                setOnClickListener(errorListener);
                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error);
                    mNetworkErrorView = viewStub.inflate();
                }
                setViewVisible(mNetworkErrorView);
                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                break;
            default:
                break;
        }
    }

    private void setViewVisible(View view) {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(view == mLoadingView ? VISIBLE : GONE);
        }
        if (mTheEndView != null) {
            mTheEndView.setVisibility(view == mTheEndView ? VISIBLE : GONE);
        }
        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(view == mNetworkErrorView ? VISIBLE : GONE);
        }
    }

    public enum State {
        NORMAL /**正常*/
        , END /**加载到最底了*/
        , LOADING /**加载中..*/
        , ERROR/**网络异常*/
    }
}