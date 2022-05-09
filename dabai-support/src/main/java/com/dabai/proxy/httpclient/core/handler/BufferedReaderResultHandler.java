package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class BufferedReaderResultHandler implements ResultHandler<BufferedReader> {

	private static int DEFAULT_BUFFER_SIZE = 8192;

	private final int size;

	public BufferedReaderResultHandler() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public BufferedReaderResultHandler(int size) {
		this.size = size;
	}

	@Override
	public BufferedReader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return new BufferedReader(new InputStreamReader(input, defaultCharset), size);
	}

}
