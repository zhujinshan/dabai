package com.dabai.proxy.httpclient.spring;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhujinshan
 */
public class HttpClientFactoryBean implements FactoryBean<HttpClient>{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpClient client;
	
	public void setClient(HttpClient client) {
		this.client = client;
	}

	@Override
	public HttpClient getObject() throws Exception {
		return client;
	}

	@Override
	public Class<?> getObjectType() {
		return HttpClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
