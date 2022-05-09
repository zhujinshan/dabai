package com.dabai.proxy.httpclient.core.handler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import java.io.InputStream;
import java.nio.charset.Charset;

public class ByteArrayResultHandler implements ResultHandler<byte[]> {

	@Override
	public byte[] handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return IOUtils.toByteArray(input);
	}

}
