package com.common.util;

import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by yanliang.zhao on 2016-08-04.
 * 原文链接：http://www.jianshu.com/p/fa33f6267b96
 */
public class C_CheckPhoneSystemUtils {
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static boolean isPropertiesExist(String... keys) {

        try {
            C_BuildProperties prop = C_BuildProperties.newInstance();
            for (String key : keys) {
                String str = prop.getProperty(key);
                Log.d("Unity", "str=" + str);
                if (str == null)
                    return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 检查是否是华为系统（EMUI）
     */
    public static boolean isEMUI() {
        return isPropertiesExist(KEY_EMUI_VERSION_CODE);
    }

    /**
     * 检测MIUI
     *
     * @return
     */
    public static boolean isMIUI() {
        try {

            final C_BuildProperties prop = C_BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {

            return false;
        }
    }

    /**
     * 检测Flyme
     *
     * @return
     */
    public static boolean isFlyme() {
        try { // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }
}
