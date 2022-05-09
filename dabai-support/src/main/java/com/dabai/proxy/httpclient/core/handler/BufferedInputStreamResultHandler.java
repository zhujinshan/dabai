package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class BufferedInputStreamResultHandler implements ResultHandler<BufferedInputStream> {

	private static int DEFAULT_BUFFER_SIZE = 8192;

	private final int size;

	public BufferedInputStreamResultHandler() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public BufferedInputStreamResultHandler(int size) {
		this.size = size;
	}

	@Override
	public BufferedInputStream handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return new BufferedInputStream(input, size);
	}

}
