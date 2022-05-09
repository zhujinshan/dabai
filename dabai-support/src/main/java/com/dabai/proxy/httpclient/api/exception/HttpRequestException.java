package com.dabai.proxy.httpclient.api.exception;

@SuppressWarnings("serial")
public class HttpRequestException extends RuntimeException {

	public HttpRequestException() {
		super();
	}

	public HttpRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpRequestException(String message) {
		super(message);
	}

	public HttpRequestException(Throwable cause) {
		super(cause);
	}

}
