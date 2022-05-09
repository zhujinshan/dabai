package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class LineNumberReaderResultHandler implements ResultHandler<LineNumberReader> {

	private final ReaderResultHandler readerResultHandler = new ReaderResultHandler();

	@Override
	public LineNumberReader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		Reader reader = readerResultHandler.handle(entity, defaultCharset);
		if (reader == null) {
			return null;
		}
		return new LineNumberReader(reader);
	}

}
