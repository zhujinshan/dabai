package com.dabai.proxy.encypt.exception;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 15:32
 */
public class ApiSignCheckException extends RuntimeException{

    public ApiSignCheckException() {
        super();
    }

    public ApiSignCheckException(String message) {
        super(message);
    }

    public ApiSignCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiSignCheckException(Throwable cause) {
        super(cause);
    }

    protected ApiSignCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
