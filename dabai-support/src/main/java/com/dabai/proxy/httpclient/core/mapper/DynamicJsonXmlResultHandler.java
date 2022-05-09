package com.dabai.proxy.httpclient.core.mapper;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.RawTypeReference;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class DynamicJsonXmlResultHandler implements TypeSensitiveResultHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object handle(HttpEntity entity, Charset defaultCharset, RawTypeReference typeReference) throws Exception {
		String content = EntityUtils.toString(entity, defaultCharset);
		if (content == null || content.isEmpty()) {
			logger.trace("entity is null");
			return null;
		}
		Header contentType = entity.getContentType();
		if (contentType != null) {
			String value = contentType.getValue().toLowerCase();
			logger.trace("content-type : {}", value);
			if (value.contains("json")) {
				logger.trace("content-type contains json, use objectMapper");
				return GlobalContext.getInstance().jsonMapper().readerFor(typeReference).readValue(content);
			} else if (value.contains("xml")) {
				logger.trace("content-type contains xml, use xmlMapper");
				return GlobalContext.getInstance().xmlMapper().readerFor(typeReference).readValue(content);
			} else {
				logger.trace("content-type neither xml or json, continue to guess content-type from content.");
			}
		}
		if (content.trim().startsWith("<")) {
			logger.trace("start with '<', use xmlMapper");
			return GlobalContext.getInstance().xmlMapper().readerFor(typeReference).readValue(content);
		} else {
			logger.trace("not start with '<',use objectMapper");
			return GlobalContext.getInstance().jsonMapper().readerFor(typeReference).readValue(content);
		}
	}
}
