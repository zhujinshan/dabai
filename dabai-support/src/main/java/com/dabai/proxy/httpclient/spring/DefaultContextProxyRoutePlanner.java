package com.dabai.proxy.httpclient.spring;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;

/**
 * @author zhujinshan
 */
public class DefaultContextProxyRoutePlanner extends DefaultRoutePlanner {

	public DefaultContextProxyRoutePlanner(final SchemePortResolver schemePortResolver) {
		super(schemePortResolver);
	}

	@Override
	protected HttpHost determineProxy(final HttpHost target, final HttpRequest request, final HttpContext context)
			throws HttpException {
		return (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
	}

}
