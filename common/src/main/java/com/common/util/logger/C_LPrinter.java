package com.common.util.logger;

public interface C_LPrinter {

    C_LPrinter t(String tag, int methodCount);

    C_LSettings init(String tag);

    C_LSettings getSettings();

    void d(String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void xml(String xml);

    void clear();
}
