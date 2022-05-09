package com.dabai.proxy.httpclient.core.metadata;

import com.dabai.proxy.httpclient.api.HttpClient;
import com.dabai.proxy.httpclient.api.exception.HttpClientDefinitionException;
import com.dabai.proxy.httpclient.api.metadata.HttpClientMetadata;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class HttpClientMetadataImpl implements HttpClientMetadata {

    private String name;

    private Class<?> beanClass;

    private Map<Method, HttpMappingMetadataImpl> methodMappings;

    public HttpClientMetadataImpl(Class<?> beanClass) {
        this.beanClass = beanClass;
        HttpClient httpClient = beanClass.getAnnotation(HttpClient.class);
        //abstract class 获取不到HttpClient注解？
        if (httpClient != null) {
            this.name = httpClient.value();
        }
        if (this.name == null || this.name.isEmpty()) {
            String simpleName = beanClass.getSimpleName();
            this.name = Character.toLowerCase(simpleName.indexOf(0)) + simpleName.substring(1);
        }
        Method[] methods = beanClass.getDeclaredMethods();
        this.methodMappings = new HashMap<>(methods.length);
        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers())) {
                HttpMappingMetadataImpl mappingMetadata = HttpMappingMetadataImpl.create(method);
                if (mappingMetadata == null) {
                    throw new HttpClientDefinitionException("Please add HttpMapping to your method : " + method);
                } else {
                    this.methodMappings.put(method, mappingMetadata);
                }
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public HttpMappingMetadataImpl getMappingMetadata(Method method) {
        return this.methodMappings.get(method);
    }

    @Override
    public Method[] getMethods() {
        return this.methodMappings.keySet().toArray(new Method[0]);
    }

}
