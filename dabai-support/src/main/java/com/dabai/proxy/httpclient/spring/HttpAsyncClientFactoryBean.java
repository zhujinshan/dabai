package com.dabai.proxy.httpclient.spring;

import org.apache.http.nio.client.HttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhujinshan
 */
public class HttpAsyncClientFactoryBean implements FactoryBean<HttpAsyncClient>{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpAsyncClient client;
	
	public void setClient(HttpAsyncClient client) {
		this.client = client;
	}

	@Override
	public HttpAsyncClient getObject() throws Exception {
		return client;
	}

	@Override
	public Class<?> getObjectType() {
		return HttpAsyncClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
