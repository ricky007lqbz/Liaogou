package com.common.app;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Date;

public class C_AppException extends Exception implements UncaughtExceptionHandler {

    private static final long serialVersionUID = -3261790237682117251L;

    private final static boolean Debug = false;//是否保存错误日志

    /**
     * 定义异常类型
     */
    public final static byte TYPE_NETWORK = 0x01;
    public final static byte TYPE_SOCKET = 0x02;
    public final static byte TYPE_HTTP_CODE = 0x03;
    public final static byte TYPE_HTTP_ERROR = 0x04;
    public final static byte TYPE_XML = 0x05;
    public final static byte TYPE_IO = 0x06;
    public final static byte TYPE_RUN = 0x07;

    private byte type;
    private int code;

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    private C_AppException() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    private C_AppException(byte type, int code, Exception excp) {
        super(excp);
        this.type = type;
        this.code = code;
        if (Debug) {
            this.saveErrorLog(excp);
        }
    }

    public int getCode() {
        return this.code;
    }

    public int getType() {
        return this.type;
    }

    /**
     * 保存异常日志
     *
     * @param excp Exception
     */
    public void saveErrorLog(Exception excp) {
        String errorlog = "errorlog.txt";
        String savePath;
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OSChina/Log/";
                File file = new File(savePath);
                if (!file.exists()) {
                    if (!file.mkdirs()){
                        return;
                    }
                }
                logFilePath = savePath + errorlog;
            }
            //没有挂载SD卡，无法写文件
            if ("".equals(logFilePath)) {
                return;
            }
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                if (!logFile.createNewFile()){
                    return;
                }
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.println("--------------------" + (new Date().toString()) + "---------------------");
            excp.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static C_AppException http(int code) {
        return new C_AppException(TYPE_HTTP_CODE, code, null);
    }

    public static C_AppException http(Exception e) {
        return new C_AppException(TYPE_HTTP_ERROR, 0, e);
    }

    public static C_AppException socket(Exception e) {
        return new C_AppException(TYPE_SOCKET, 0, e);
    }

    public static C_AppException io(Exception e) {
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            return new C_AppException(TYPE_NETWORK, 0, e);
        } else if (e instanceof IOException) {
            return new C_AppException(TYPE_IO, 0, e);
        }
        return run(e);
    }

    public static C_AppException xml(Exception e) {
        return new C_AppException(TYPE_XML, 0, e);
    }

    public static C_AppException run(Exception e) {
        return new C_AppException(TYPE_RUN, 0, e);
    }

    /**
     * 获取APP异常崩溃处理对象
     *
     * @return C_AppException
     */
    public static C_AppException getAppExceptionHandler() {
        return new C_AppException();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }

    }

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex 异常
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        System.out.println(ex.getMessage());

        StringBuilder exceptionStr = new StringBuilder();
        StackTraceElement[] elements = ex.getStackTrace();
        for (StackTraceElement element : elements) {
            exceptionStr.append(element.toString()).append("\n");
        }
        System.out.println(exceptionStr.toString());
        return true;
    }
}
