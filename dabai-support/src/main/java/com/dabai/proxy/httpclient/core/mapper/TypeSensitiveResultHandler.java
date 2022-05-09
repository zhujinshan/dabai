package com.dabai.proxy.httpclient.core.mapper;

import com.dabai.proxy.httpclient.core.RawTypeReference;
import org.apache.http.HttpEntity;

import java.nio.charset.Charset;

public interface TypeSensitiveResultHandler {

	Object handle(HttpEntity entity, Charset defaultCharset, RawTypeReference typeReference) throws Throwable;

}
