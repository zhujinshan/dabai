package com.dabai.proxy.exception;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:23
 */
public class HttpClientBusinessException extends RuntimeException{

    public HttpClientBusinessException() {
        super();
    }

    public HttpClientBusinessException(String message) {
        super(message);
    }

    public HttpClientBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpClientBusinessException(Throwable cause) {
        super(cause);
    }
}
