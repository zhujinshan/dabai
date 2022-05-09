package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

public class IntegerResultHandler implements ResultHandler<Integer>{

	@Override
	public Integer handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		String string = EntityUtils.toString(entity, defaultCharset);
		if(string == null){
			return null;
		}
		return Integer.parseInt(string);
	}

}
