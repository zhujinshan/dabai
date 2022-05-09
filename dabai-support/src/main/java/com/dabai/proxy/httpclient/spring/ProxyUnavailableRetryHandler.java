package com.dabai.proxy.httpclient.spring;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author zhujinshan
 */
public class ProxyUnavailableRetryHandler implements HttpRequestRetryHandler {

	private static final Logger logger = LoggerFactory.getLogger(ProxyUnavailableRetryHandler.class);

	public final int maxRetry;

	public final Supplier<HttpHost> httpHostSupplier;

	public ProxyUnavailableRetryHandler(int maxRetry, Supplier<HttpHost> httpHostSupplier) {
		super();
		Assert.notNull(httpHostSupplier, "httpHostSupplier");
		this.maxRetry = maxRetry;
		this.httpHostSupplier = httpHostSupplier;
	}

	@Override
	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
		if (!(exception instanceof HttpHostConnectException) && !(exception instanceof ConnectTimeoutException)) {
			return false;
		}
		if (maxRetry > 0 && executionCount >= maxRetry) {
			logger.debug("Stop to retry after {} times", executionCount);
			return false;
		}
		HttpHost host = httpHostSupplier.get();
		if (host == null) {
			logger.info("No proxy found");
			return false;
		}
		context.setAttribute(HttpClientContext.HTTP_TARGET_HOST, host);
		logger.debug("Retry another Proxy : {}", host);
		return true;
	}

}
