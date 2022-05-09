package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;

public class FileReaderResultHandler implements ResultHandler<FileReader> {
	
	private final FileResultHandler fileResultHandler = new FileResultHandler();

	@Override
	public FileReader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		File file = fileResultHandler.handle(entity, defaultCharset);
		return new FileReader(file);
	}

}
