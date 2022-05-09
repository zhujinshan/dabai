package com.dabai.proxy.httpclient.core.mapper;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.RawTypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class JsonResultHandler implements TypeSensitiveResultHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public JsonResultHandler() {
	}

	@Override
	public Object handle(HttpEntity entity, Charset defaultCharset, RawTypeReference type) throws Exception {
		String json = EntityUtils.toString(entity, defaultCharset);
		if (json == null || json.isEmpty()) {
			return null;
		}
		if(json.length() > 1024){
			logger.debug("RESPONSE content length = {}", json.length());
		}else{
			logger.debug("RESPONSE {}", json);
		}
		Object result = GlobalContext.getInstance().jsonMapper().readerFor(type).readValue(json);
		return result;
	}
}
