package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.InputStream;
import java.nio.charset.Charset;

public class InputStreamResultHandler implements ResultHandler<InputStream> {

	@Override
	public InputStream handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		return entity.getContent();
	}

}
