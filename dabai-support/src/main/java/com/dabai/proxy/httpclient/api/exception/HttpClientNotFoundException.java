package com.dabai.proxy.httpclient.api.exception;

@SuppressWarnings("serial")
public class HttpClientNotFoundException extends HttpRequestException {

	public HttpClientNotFoundException() {
		super();
	}

	public HttpClientNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpClientNotFoundException(String message) {
		super(message);
	}

	public HttpClientNotFoundException(Throwable cause) {
		super(cause);
	}

}
