package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

public class FloatResultHandler implements ResultHandler<Float> {

	@Override
	public Float handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		String string = EntityUtils.toString(entity, defaultCharset);
		if (string == null) {
			return null;
		}
		return Float.parseFloat(string);
	}

}
