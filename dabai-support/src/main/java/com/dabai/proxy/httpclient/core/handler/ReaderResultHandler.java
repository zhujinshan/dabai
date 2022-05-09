package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class ReaderResultHandler implements ResultHandler<Reader> {

	@Override
	public Reader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return new InputStreamReader(input, defaultCharset);
	}

}
