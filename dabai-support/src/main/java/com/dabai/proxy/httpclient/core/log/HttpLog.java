package com.dabai.proxy.httpclient.core.log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HttpLog implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 请求协议版本
	 */
	private String protocolVersion;
	
	/**
	 * 请求方法
	 */
	private String requestMethod;

	/**
	 * 请求地址（带参）（不带域名）
	 */
	private String requestUri;

    /**
     * 请求地址
     */
    private String requestPath;

	/**
	 * 请求头
	 */
	private Map<String, String> requestHeaders = new HashMap<>();

	/**
	 * 是否有请求主体
	 */
	private boolean hasRequestBody;

	/**
	 * 请求主体
	 */
	private String requestBody;

	/**
	 * 响应状态码
	 */
	private int statusCode;

	/**
	 * 响应信息
	 */
	private String reasonPhrase;

	/**
	 * 响应头
	 */
	private Map<String, String> responseHeaders = new HashMap<>();

	/**
	 * 是否有响应主体
	 */
	private boolean hasResponseBody;

	/**
	 * 响应主体
	 */
	private String responseBody;
	
	/**
	 * 请求时间（毫秒数）
	 */
	private long requestTime;
	
	/**
	 * 响应/完成时间（毫秒数）
	 */
	private long responseTime;
	
	private Exception exception;

	public void addRequestHeader(String name, String value) {
		this.requestHeaders.put(name, value);
	}

	public void addResponseHeader(String name, String value) {
		this.responseHeaders.put(name, value);
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public boolean isHasRequestBody() {
		return hasRequestBody;
	}

	public void setHasRequestBody(boolean hasRequestBody) {
		this.hasRequestBody = hasRequestBody;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public boolean isHasResponseBody() {
		return hasResponseBody;
	}

	public void setHasResponseBody(boolean hasResponseBody) {
		this.hasResponseBody = hasResponseBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
	public String toString() {
		return String.format(
				"HttpLog [protocolVersion=%s, requestMethod=%s, requestUri=%s, requestPath=%s, requestHeaders=%s, hasRequestBody=%s, requestBody=%s, statusCode=%s, reasonPhrase=%s, responseHeaders=%s, hasResponseBody=%s, responseBody=%s, requestTime=%s, responseTime=%s, exception=%s]",
				protocolVersion, requestMethod, requestUri, requestPath, requestHeaders, hasRequestBody, requestBody, statusCode,
				reasonPhrase, responseHeaders, hasResponseBody, responseBody, requestTime, responseTime, exception);
	}

}
