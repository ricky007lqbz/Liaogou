package com.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ricky on 2016/03/25.
 * <p>
 * http://stackoverflow.com/questions/21092888/windowsoftinputmode-adjustresize-not-working-with-translucent-action-navbar
 */
public class C_RootRelativeLayout extends RelativeLayout {

    public C_RootRelativeLayout(Context context) {
        super(context);
    }

    public C_RootRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public C_RootRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }

        return super.fitSystemWindows(insets);
    }

}
