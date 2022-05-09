package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.CharArrayReader;
import java.nio.charset.Charset;

/**
 * 不建议的返回类型！
 * @author zhujinshan
 *
 */
public class CharArrayReaderResultHandler implements ResultHandler<CharArrayReader> {

	@Override
	public CharArrayReader handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		String string = EntityUtils.toString(entity, defaultCharset);
		if(string == null){
			return null;
		}
		return new CharArrayReader(string.toCharArray());
	}

}
