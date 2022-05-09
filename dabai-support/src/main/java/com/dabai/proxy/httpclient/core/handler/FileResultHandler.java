package com.dabai.proxy.httpclient.core.handler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileResultHandler implements ResultHandler<File> {

	@Override
	public File handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		File file = File.createTempFile("HttpClientTempFile", null);
		try (InputStream input = entity.getContent(); FileOutputStream output = new FileOutputStream(file)) {
			IOUtils.copy(input, output);
		}
		return file;
	}

}
