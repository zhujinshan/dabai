package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.Args;

import java.io.BufferedReader;
import java.nio.charset.Charset;

public class StringBufferResultHandler implements ResultHandler<StringBuffer> {

	/**
	 * 不用StringResultHandler而用BufferedReaderResultHandler的原因：
	 * StringResultHandler会把所有结果读到内存，而BufferedReaderResultHandler只是返回一个Reader对象，
	 * 此时并没有读取所有结果
	 */
	private final BufferedReaderResultHandler bufferedReaderResultHandler = new BufferedReaderResultHandler();

	@Override
	public StringBuffer handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		Args.check(entity.getContentLength() <= Integer.MAX_VALUE, "HTTP entity too large to be buffered in memory");
		BufferedReader reader = bufferedReaderResultHandler.handle(entity, defaultCharset);
		if (reader == null) {
			return null;
		}
		int contentLength = (int) entity.getContentLength();
		if (contentLength < 0) {
			contentLength = 4096;
		}
		StringBuffer sb = new StringBuffer(contentLength);

		int len;
		char[] cbuf = new char[1024];
		while ((len = reader.read(cbuf)) != -1) {
			sb.append(cbuf, 0, len);
		}

		return sb;
	}

}
