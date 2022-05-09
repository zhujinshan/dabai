package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;

import java.nio.charset.Charset;

public interface ResultHandler<T> {

	T handle(HttpEntity entity, Charset defaultCharset) throws Exception;

}
