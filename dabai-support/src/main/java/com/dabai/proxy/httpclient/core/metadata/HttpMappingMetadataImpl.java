package com.dabai.proxy.httpclient.core.metadata;

import com.dabai.proxy.httpclient.api.BeforeRequest;
import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.HttpMapping;
import com.dabai.proxy.httpclient.api.HttpParam;
import com.dabai.proxy.httpclient.api.RequestMethod;
import com.dabai.proxy.httpclient.api.RequestProcessor;
import com.dabai.proxy.httpclient.api.exception.HttpClientDefinitionException;
import com.dabai.proxy.httpclient.api.metadata.HttpEntityMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpHeaderMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpMappingMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpParamMetadata;
import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.RawTypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ResolvableType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

public class HttpMappingMetadataImpl implements HttpMappingMetadata {

    private static final Logger logger = LoggerFactory.getLogger(HttpMappingMetadataImpl.class);

    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    public static final String DEFAULT_CHARSET = "utf8";

    private Method method;
    private String name;
    private RequestMethod requestMethod;
    private DataType dataType;
    private String path;
    private Charset charset;
    private HttpHeaderMetadata[] headers;

    private RequestProcessor[] requestProcessors;

    private HttpEntityMetadataImpl entityMetadata;

    private Map<String, HttpParamMetadataImpl> paramMetadataMapping;

    // 这两个字段可以根据method推算出来，添加进来是为了使用空间换取时间
    private RawTypeReference actualReturnType;
    private ResolvableType actualResolvableReturnType;

    private int httpContextIndex = -1;

    private HttpMappingMetadataImpl() {
    }

    private static String getPath0(HttpMapping mapping) {
        if (mapping == null) {
            return "";
        } else {
            if (mapping.path().isEmpty()) {
                return mapping.value();
            } else {
                return mapping.path();
            }
        }
    }

    public static HttpMappingMetadataImpl create(Method method) {
        return create(method, DEFAULT_CHARSET);
    }

    public static HttpMappingMetadataImpl create(Method method, String defaultCharset) {
        HttpMapping classMapping = method.getDeclaringClass().getAnnotation(HttpMapping.class);
        HttpMapping methodMapping = method.getAnnotation(HttpMapping.class);
        if (methodMapping == null) {
            return null;
        }
        String name = methodMapping.name();
        RequestMethod requestMethod = methodMapping.method();
        DataType dataType = methodMapping.dataType();
        String path = getPath0(classMapping) + getPath0(methodMapping);

        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        Map<String, HttpParamMetadataImpl> paramMetadataMapping = new HashMap<>(parameterAnnotations.length);
        int httpContextIndex = -1;
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            HttpParam httpParam = null;
            HttpEntity httpEntity = null;
            for (Annotation annotation : annotations) {
                if (annotation instanceof HttpParam) {
                    httpParam = (HttpParam) annotation;
                }
                if (annotation instanceof HttpEntity) {
                    httpEntity = (HttpEntity) annotation;
                }
            }
            String defaultName = null;
            if (parameterNames != null && parameterNames.length - 1 >= i) {
                defaultName = parameterNames[i];
            }

            //修复HttpEntity类型Cast错误
            if (HttpContext.class.isAssignableFrom(parameterTypes[i])) {
                httpContextIndex = i;
            } else {
                if (httpEntity == null) {
                    HttpParamMetadataImpl paramMetadata = new HttpParamMetadataImpl(i, httpParam, defaultName);
                    paramMetadataMapping.put(paramMetadata.getName(), paramMetadata);
                }
            }
        }

        String charset = DEFAULT_CHARSET;
        if (classMapping != null) {
            charset = classMapping.charset();
        }
        if (charset.isEmpty()) {
            charset = methodMapping.charset();
        }
        if (charset.isEmpty()) {
            charset = defaultCharset;
        }
        if (charset == null || charset.isEmpty()) {
            charset = DEFAULT_CHARSET;
        }

        HttpMappingMetadataImpl httpMappingMetadata = new HttpMappingMetadataImpl();
        httpMappingMetadata.method = method;
        httpMappingMetadata.name = name;
        httpMappingMetadata.dataType = dataType;
        httpMappingMetadata.path = GlobalContext.getInstance().propertyResolver().resolveRequiredPlaceholders(path);
        httpMappingMetadata.requestMethod = requestMethod;
        httpMappingMetadata.charset = Charset.forName(charset);
        httpMappingMetadata.entityMetadata = HttpEntityMetadataImpl.create(method);
        httpMappingMetadata.paramMetadataMapping = paramMetadataMapping;
        httpMappingMetadata.headers = HttpHeaderMetadataImpl.createHeaders(method);
        httpMappingMetadata.httpContextIndex = httpContextIndex;

        try {
            List<RequestProcessor> requestProcessors = new ArrayList<>();
            BeforeRequest classProcessors = method.getDeclaringClass().getAnnotation(BeforeRequest.class);
            if (classProcessors != null) {
                for (Class<? extends RequestProcessor> proccessType : classProcessors.value()) {
                    requestProcessors.add(proccessType.newInstance());
                }
            }
            BeforeRequest methodProcessors = method.getAnnotation(BeforeRequest.class);
            if (methodProcessors != null) {
                for (Class<? extends RequestProcessor> proccessType : methodProcessors.value()) {
                    requestProcessors.add(proccessType.newInstance());
                }
            }
            httpMappingMetadata.requestProcessors = requestProcessors
                    .toArray(new RequestProcessor[requestProcessors.size()]);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new HttpClientDefinitionException(
                    "Cannot create instance of some request processors, please check @BeforeRequest in your method : "
                            + method,
                    e);
        }

        // return type
        ResolvableType resolvableReturnType = ResolvableType.forMethodReturnType(method);
        ResolvableType actualType = null;
        if (Optional.class == resolvableReturnType.getRawClass()
                || Future.class == resolvableReturnType.getRawClass()) {
            actualType = resolvableReturnType.getGeneric(0);
        } else {
            actualType = resolvableReturnType;
        }
        httpMappingMetadata.actualResolvableReturnType = actualType;
        httpMappingMetadata.actualReturnType = new RawTypeReference(actualType.getType());

        if (httpMappingMetadata.containsEntity()) {
            if (requestMethod != RequestMethod.POST && requestMethod != RequestMethod.PATCH
                    && requestMethod != RequestMethod.PUT) {
                httpMappingMetadata.requestMethod = RequestMethod.POST;
                logger.info("Auto changed http method to POST, for {} is NOT a http entity enclosing request, see : {}",
                        requestMethod, method);
            }
        }

        return httpMappingMetadata;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public boolean containsEntity() {
        return entityMetadata != null;
    }

    @Override
    public HttpEntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public HttpParamMetadata getParamMetadata(String name) {
        return paramMetadataMapping.get(name);
    }

    @Override
    public String[] getParamNames() {
        return this.paramMetadataMapping.keySet().toArray(new String[this.paramMetadataMapping.size()]);
    }

    public RawTypeReference getReturnType() {
        return actualReturnType;
    }

    public ResolvableType getResolvableReturnType() {
        return actualResolvableReturnType;
    }

    @Override
    public RequestProcessor[] getRequestProcessors() {
        return requestProcessors;
    }

    @Override
    public HttpHeaderMetadata[] getHeaders() {
        return headers;
    }

    @Override
    public int getHttpContextIndex() {
        return httpContextIndex;
    }


}
