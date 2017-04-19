package com.common.util.logger;

/**
 * C_Logger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class C_Logger {
    private static final String DEFAULT_TAG = "PRETTYLOGGER";

    private static C_LPrinter printer = new C_LoggerPrinter();

    //no instance
    private C_Logger() {
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static C_LSettings init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in C_Logger as TAG
     */
    public static C_LSettings init(String tag) {
        printer = new C_LoggerPrinter();
        return printer.init(tag);
    }

    public static void clear() {
        printer.clear();
        printer = null;
    }

    public static C_LPrinter t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static C_LPrinter t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static C_LPrinter t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void e(String message, Object... args) {
        printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }

}
