package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

public class FileInputStreamResultHandler implements ResultHandler<FileInputStream> {

	private final FileResultHandler fileResultHandler = new FileResultHandler();

	@Override
	public FileInputStream handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		File file = fileResultHandler.handle(entity, defaultCharset);
		return new FileInputStream(file);
	}

}
