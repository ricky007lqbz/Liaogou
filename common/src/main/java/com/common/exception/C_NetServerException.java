package com.common.exception;

/**
 * Created by ricky on 2016/08/17.
 *
 * 网络服务异常
 */
public class C_NetServerException extends Exception {

    private String message;

    public C_NetServerException() {
        this("网络服务异常");
    }

    public C_NetServerException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
