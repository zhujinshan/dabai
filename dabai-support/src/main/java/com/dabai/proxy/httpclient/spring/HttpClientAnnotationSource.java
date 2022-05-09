package com.dabai.proxy.httpclient.spring;

import java.util.Set;

/**
 * @author zhujinshan
 */
public class HttpClientAnnotationSource {
	
	private Set<String> packages;
	
	private boolean enableAsyncClient;

	public Set<String> getPackages() {
		return packages;
	}

	public void setPackages(Set<String> packages) {
		this.packages = packages;
	}

	public boolean isEnableAsyncClient() {
		return enableAsyncClient;
	}

	public void setEnableAsyncClient(boolean enableAsyncClient) {
		this.enableAsyncClient = enableAsyncClient;
	}

}
