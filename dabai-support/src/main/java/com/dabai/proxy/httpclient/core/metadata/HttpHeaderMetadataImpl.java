package com.dabai.proxy.httpclient.core.metadata;

import com.dabai.proxy.httpclient.api.HttpHeader;
import com.dabai.proxy.httpclient.api.metadata.HttpHeaderMetadata;
import com.dabai.proxy.httpclient.core.GlobalContext;

import java.lang.reflect.Method;

public class HttpHeaderMetadataImpl implements HttpHeaderMetadata {

	private final String name;

	private final String value;

	public HttpHeaderMetadataImpl(HttpHeader header) {
		this.name = GlobalContext.getInstance().propertyResolver().resolveRequiredPlaceholders(header.name());
		this.value = GlobalContext.getInstance().propertyResolver().resolveRequiredPlaceholders(header.value());
	}

	public static HttpHeaderMetadata[] createHeaders(Method method) {
		HttpHeader[] classHeaders = method.getDeclaringClass().getAnnotationsByType(HttpHeader.class);
		HttpHeader[] methodHeaders = method.getAnnotationsByType(HttpHeader.class);
		HttpHeaderMetadata[] headers = new HttpHeaderMetadata[classHeaders.length + methodHeaders.length];
		for (int i = 0; i < classHeaders.length; i++) {
			headers[i] = new HttpHeaderMetadataImpl(classHeaders[i]);
		}
		// 0, 1, 2, 3, 4
		// 0, 1, 0, 1, 2
		for (int i = classHeaders.length; i < headers.length; i++) {
			headers[i] = new HttpHeaderMetadataImpl(methodHeaders[i - classHeaders.length]);
		}
		return headers;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

}
