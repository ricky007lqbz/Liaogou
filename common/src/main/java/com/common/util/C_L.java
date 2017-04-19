package com.common.util;

import android.util.Log;

import com.common.util.logger.C_Logger;
import com.common.util.logger.C_LogLevel;

/**
 * Created by ricky on 2016/05/10.
 * <p/>
 * 自定义的log工具类
 */
public class C_L {
    /**
     * 输出等级NONE
     */
    public static final int LEVEL_OFF = 0;
    /**
     * 输出等级NONE
     */
    public static final int LEVEL_ALL = 7;

    /**
     * 输出等级V
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 输出等级D
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 输出等级I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 输出等级W
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 输出等级E
     */
    public static final int LEVEL_ERROR = 5;
    /**
     * 输出等级S
     */
    public static final int LEVEL_SYSTEM = 6;

    private static int currentLevel = LEVEL_ALL;

    public static void init(boolean isDebug) {
        if (isDebug) {
            currentLevel = LEVEL_ALL;
        } else {
            currentLevel = LEVEL_OFF;
        }
        C_Logger.init().logLevel(isDebug ? C_LogLevel.FULL : C_LogLevel.NONE); //初始化log工具
    }

    //=========================输出Verbose级别Log==================//

    public static void v(String message) {
        if (currentLevel >= LEVEL_VERBOSE) {
            C_Logger.v(message, message);
        }
    }

    public static void v(String tag, String message) {
        if (currentLevel >= LEVEL_VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void v(String message, Object... args) {
        if (currentLevel >= LEVEL_VERBOSE) {
            C_Logger.v(message, args);
        }
    }

    //=========================输出Debug级别Log==================//

    public static void d(String message) {
        if (currentLevel >= LEVEL_DEBUG) {
            C_Logger.d(message, message);
        }
    }

    public static void d(String tag, String message) {
        if (currentLevel >= LEVEL_DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(String message, Object... args) {
        if (currentLevel >= LEVEL_DEBUG) {
            C_Logger.d(message, args);
        }
    }

    //=========================输出Info级别Log==================//

    public static void i(String message) {
        if (currentLevel >= LEVEL_INFO) {
            C_Logger.i(message, message);
        }
    }

    public static void i(String tag, String message) {
        if (currentLevel >= LEVEL_INFO) {
            Log.i(tag, message);
        }
    }

    public static void i(String message, Object... args) {
        if (currentLevel >= LEVEL_INFO) {
            C_Logger.i(message, args);
        }
    }

    //=========================输出Warn级别Log==================//

    public static void w(String message) {
        if (currentLevel >= LEVEL_WARN) {
            C_Logger.w(message, message);
        }
    }

    public static void w(String tag, String message) {
        if (currentLevel >= LEVEL_WARN) {
            Log.w(tag, message);
        }
    }

    public static void w(String message, Object... args) {
        if (currentLevel >= LEVEL_WARN) {
            C_Logger.w(message, args);
        }
    }

    //=========================输出Error级别Log==================//

    public static void e(String message) {
        if (currentLevel >= LEVEL_ERROR) {
            C_Logger.i(message, message);
        }
    }

    public static void e(String tag, String message) {
        if (currentLevel >= LEVEL_ERROR) {
            Log.i(tag, message);
        }
    }

    public static void e(String message, Object... args) {
        if (currentLevel >= LEVEL_ERROR) {
            C_Logger.i(message, args);
        }
    }

    //=========================输出Assert级别Log==================//

    public static void wtf(String message) {
        if (currentLevel >= LEVEL_SYSTEM) {
            C_Logger.wtf(message, message);
        }
    }

    public static void wtf(String tag, String message) {
        if (currentLevel >= LEVEL_ERROR) {
            Log.wtf(tag, message);
        }
    }

    public static void wtf(String message, Object... args) {
        if (currentLevel >= LEVEL_ERROR) {
            C_Logger.wtf(message, args);
        }
    }

    //=========================输出带Json的Log==================//

    public static void json(String json) {
        if (currentLevel >= LEVEL_DEBUG) {
            C_Logger.json(json);
        }
    }

    //=========================输出带Xml的Log==================//

    public static void xml(String xml) {
        if (currentLevel >= LEVEL_DEBUG) {
            C_Logger.xml(xml);
        }
    }
}
