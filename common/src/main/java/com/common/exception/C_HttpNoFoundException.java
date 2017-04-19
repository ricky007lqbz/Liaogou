package com.common.exception;

/**
 * Created by ricky on 2016/08/17.
 * <p>
 * 网络服务异常
 */
public class C_HttpNoFoundException extends Exception {

    private String message;

    public C_HttpNoFoundException() {
        this("404 not found");
    }

    public C_HttpNoFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
