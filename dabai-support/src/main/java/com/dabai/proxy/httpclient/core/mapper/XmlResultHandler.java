package com.dabai.proxy.httpclient.core.mapper;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.RawTypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class XmlResultHandler implements TypeSensitiveResultHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public XmlResultHandler() {
	}

	@Override
	public Object handle(HttpEntity entity, Charset defaultCharset, RawTypeReference type) throws Exception {
		String xml = EntityUtils.toString(entity, defaultCharset);
		if (xml == null || xml.isEmpty()) {
			return null;
		}
		if(xml.length() > 1024){
			logger.debug("RESPONSE content length = {}", xml.length());
		}else{
			logger.debug("RESPONSE {}", xml);
		}
		Object result = GlobalContext.getInstance().xmlMapper().readerFor(type).readValue(xml);
		return result;
	}
}
