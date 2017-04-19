package com.common.base.delegate;

import android.support.v4.view.ViewCompat;

/**
 * Created by Ziwu on 2016/9/26.
 * <p/>
 * <p/>
 * 获取头部滑动位移量和速率的自定义动画头部
 */
public class C_BaseTabTerminalDelegateAdvance extends C_TransparentTabTerminalDelegate {

    float maxRatio = 1f;

    /**
     * 固定banner 标题动画。
     */
    @Override
    protected void setTitleByAnim(float f) {
        /**
         *当前控件移动距离/ 当前控件总移动距离  =  当前移动距离/ 总移动距离
         */
        float radiusAlpha = 4f;
        float curAlpha = maxRatio - (radiusAlpha * f);

        if (tvCenter != null) {
            ViewCompat.setAlpha(tvCenter, curAlpha);
        }
    }

    @Override
    protected void setTopViewAnim(float f) {
        //不做动画
    }
}
