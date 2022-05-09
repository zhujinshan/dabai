package com.dabai.proxy.httpclient.api.metadata;

import java.lang.reflect.Method;

public interface HttpClientMetadata {

	String getName();

	Class<?> getBeanClass();

	HttpMappingMetadata getMappingMetadata(Method method);
	
	Method[] getMethods();
}
