package com.common.base.listener;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by ricky on 2016/10/10.
 * <p>
 * 生命周期监听
 */

public interface C_LifeCycleListener {

    void onCreate(Context context, boolean isActivity, @Nullable Bundle savedInstanceState);

    void onStart(Context context, boolean isActivity, boolean isFromForeground, CharSequence pageName);

    void onResume(Context context, boolean isActivity, boolean isFromForeground, CharSequence pageName);

    void onPause(Context context, boolean isActivity, boolean isFromForeground, CharSequence pageName);

    void onStop(Context context, boolean isActivity, boolean isFromForeground, CharSequence pageName);

    void onDestroy(Context context, boolean isActivity, boolean isFromForeground, CharSequence pageName);

    void onRequestStart(Context context, boolean isActivity, boolean isFirsTimeRequest);

    void onRequestFinish(Context context, boolean isActivity, boolean isFirsTimeRequest);
}
