package com.dabai.proxy.httpclient.core.handler;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.http.HttpEntity;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 此类把非base64的结果流转换成base64输入流
 * 
 * @author zhujinshan
 *
 */
public class Base64InputStreamResultHandler implements ResultHandler<Base64InputStream> {

	@Override
	public Base64InputStream handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		InputStream input = entity.getContent();
		if (input == null) {
			return null;
		}
		return new Base64InputStream(input, true);
	}

}
