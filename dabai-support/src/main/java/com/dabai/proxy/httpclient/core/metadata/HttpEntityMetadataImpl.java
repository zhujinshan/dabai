package com.dabai.proxy.httpclient.core.metadata;

import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.HttpEntity;
import com.dabai.proxy.httpclient.api.exception.HttpClientDefinitionException;
import com.dabai.proxy.httpclient.api.metadata.HttpEntityMetadata;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class HttpEntityMetadataImpl implements HttpEntityMetadata {

	private static final Logger logger = LoggerFactory.getLogger(HttpEntityMetadataImpl.class);

	private final int index;

	private final DataType dataType;

	private final Class<?> jsonViewClass;

	private HttpEntityMetadataImpl(int index, DataType dataType, Class<?> jsonViewClass) {
		this.index = index;
		this.dataType = dataType;
		this.jsonViewClass = jsonViewClass;
	}

	public static HttpEntityMetadataImpl create(Method method) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		DataType dataType = null;
		Integer entityIndex = null;
		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] annotations = parameterAnnotations[i];
			for (Annotation annotation : annotations) {
				if (annotation instanceof HttpEntity) {
					if (entityIndex == null) {
						entityIndex = i;
						dataType = ((HttpEntity) annotation).dataType();
					} else {
						throw new HttpClientDefinitionException(
								"Only one entity is supported in your http method parameters, please check method : "
										+ method);
					}
				}
			}
		}
		if (entityIndex == null) {
			return null;
		}
		// jsonView support
		Class<?> jsonViewClass = null;
		Annotation[] annotations = parameterAnnotations[entityIndex];
		for (Annotation annotation : annotations) {
			if (annotation instanceof JsonView) {
				Class<?>[] jsonViews = ((JsonView) annotation).value();
				if (jsonViews.length > 1) {
					throw new HttpClientDefinitionException(
							"Only one class is supported in your json view, please check method : " + method);
				} else if (jsonViews.length == 1) {
					jsonViewClass = jsonViews[0];
				} else {
					logger.warn(
							"If you want to use JsonView, please set view in JsonView annotation, please check method : {}",
							method);
				}
			}
		}
		if (jsonViewClass != null) {
			if (DataType.AUTO != dataType && DataType.JSON != dataType) {
				logger.info(
						"JsonView : {} doesn't work, for DataType : {} not support JsonView; please check method : {}",
						jsonViewClass, dataType, method);
				jsonViewClass = null;
			}
		}
		return new HttpEntityMetadataImpl(entityIndex, dataType, jsonViewClass);
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public DataType getDataType() {
		return dataType;
	}

	@Override
	public Class<?> getJsonViewClass() {
		return jsonViewClass;
	}

	@Override
	public boolean containsJsonView() {
		return jsonViewClass != null;
	}

}
