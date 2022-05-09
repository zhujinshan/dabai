package com.dabai.proxy.httpclient.core;

/**
 * @author zhujinshan
 */
public class HttpClientConfig {
	
	private boolean logRequest = true;
	
	private boolean logHeader = false;
	
	private boolean logResponse = true;

	public boolean isLogRequest() {
		return logRequest;
	}

	public void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	public boolean isLogHeader() {
		return logHeader;
	}

	public void setLogHeader(boolean logHeader) {
		this.logHeader = logHeader;
	}

	public boolean isLogResponse() {
		return logResponse;
	}

	public void setLogResponse(boolean logResponse) {
		this.logResponse = logResponse;
	}

}
