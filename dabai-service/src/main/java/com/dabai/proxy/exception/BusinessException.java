package com.dabai.proxy.exception;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:22
 */
public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
