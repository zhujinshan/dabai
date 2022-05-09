package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamReaderResultHandler implements ResultHandler<InputStreamReader> {

	@Override
	public InputStreamReader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return new InputStreamReader(input, defaultCharset);
	}

}
