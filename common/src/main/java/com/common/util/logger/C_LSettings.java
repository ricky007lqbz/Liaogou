package com.common.util.logger;

public final class C_LSettings {

    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private C_LogTool logTool;

    /**
     * Determines how logs will printed
     */
    private C_LogLevel logLevel = C_LogLevel.FULL;

    public C_LSettings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    /**
     * Use {@link #methodCount}
     */
    @Deprecated
    public C_LSettings setMethodCount(int methodCount) {
        return methodCount(methodCount);
    }

    public C_LSettings methodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    /**
     * Use {@link #logLevel}
     */
    @Deprecated
    public C_LSettings setLogLevel(C_LogLevel logLevel) {
        return logLevel(logLevel);
    }

    public C_LSettings logLevel(C_LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * Use {@link #methodOffset}
     */
    @Deprecated
    public C_LSettings setMethodOffset(int offset) {
        return methodOffset(offset);
    }

    public C_LSettings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public C_LSettings logTool(C_LogTool logTool) {
        this.logTool = logTool;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public C_LogLevel getLogLevel() {
        return logLevel;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public C_LogTool getLogTool() {
        if (logTool == null) {
            logTool = new C_AndroidCLogTool();
        }
        return logTool;
    }
}
