package com.common.base.activity;

import android.os.Bundle;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by ricky on 2016/05/23.
 * <p/>
 * 自己封装的在左侧右滑，实现返回的activity
 */
public class C_BaseSwipeBackActivity extends C_BaseAppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper; //右滑返回的activity帮助类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) {
            v = mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);//启动手势
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityFromTranslucent(this); //将当前类转为半透明效果（精髓所在）
        getSwipeBackLayout().scrollToFinishActivity();//关闭当前activity
    }
}
