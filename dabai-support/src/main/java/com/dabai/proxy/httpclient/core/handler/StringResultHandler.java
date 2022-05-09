package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

public class StringResultHandler implements ResultHandler<String> {

	@Override
	public String handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		String string = EntityUtils.toString(entity, defaultCharset);
		return string;
	}

}
