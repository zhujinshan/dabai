package com.dabai.proxy.httpclient.api;


import com.dabai.proxy.httpclient.api.metadata.HttpMappingMetadata;
import org.apache.http.protocol.HttpContext;

import java.util.Map;

public interface HttpRequest {

	HttpMappingMetadata getMappingMetadata();

	Object getParameter(String name);

	void setParameter(String name, Object value);

    void removeParameter(String name);

    void clearParameters();

	Map<String, Object> getParameters();

	Object getEntity();

	void setEntity(Object entity);

	void clearEntity();

	String getUri();
	
	HttpContext getHttpContext();
}
