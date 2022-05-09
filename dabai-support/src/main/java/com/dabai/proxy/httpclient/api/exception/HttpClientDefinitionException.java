package com.dabai.proxy.httpclient.api.exception;

@SuppressWarnings("serial")
public class HttpClientDefinitionException extends RuntimeException {

	public HttpClientDefinitionException() {
		super();
	}

	public HttpClientDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpClientDefinitionException(String message) {
		super(message);
	}

	public HttpClientDefinitionException(Throwable cause) {
		super(cause);
	}

}
