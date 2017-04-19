package com.common.util;

/**
 * Created by ricky on 2016/03/18.
 * <p>
 * 按钮重复点击处理
 */
public class C_BtnClickUtils {

    private static long mLastClickTime = 0;

    private C_BtnClickUtils() {
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long tiemDoubleClick = time - mLastClickTime;
        if (0 < tiemDoubleClick && tiemDoubleClick < 800) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }
}
