package com.dabai.proxy.httpclient.core;

import com.dabai.proxy.httpclient.api.HttpRequest;
import com.dabai.proxy.httpclient.api.RequestMethod;
import com.dabai.proxy.httpclient.api.exception.HttpRequestException;
import com.dabai.proxy.httpclient.api.metadata.HttpEntityMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpMappingMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpParamMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpMethodRequest implements HttpRequest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Pattern PATTERN = Pattern.compile("\\{([^{}]+?)}");

    private HttpMappingMetadata mappingMetadata;

    private List<Object> arguments;

    private Map<String, Object> parameters = new HashMap<>();

    private Object entity;

    public HttpMethodRequest(HttpMappingMetadata mappingMetadata, Object[] args) {
        this.mappingMetadata = mappingMetadata;
        if (args == null) {
            args = new Object[0];
        }
        this.arguments = new ArrayList<>(args.length + 3);
        if (mappingMetadata.containsEntity()) {
            HttpEntityMetadata entityMetadata = mappingMetadata.getEntityMetadata();
            int entityIndex = entityMetadata.index();
            this.entity = args[entityIndex];
        }

        this.arguments.addAll(Arrays.asList(args));

        String[] paramNames = this.mappingMetadata.getParamNames();
        if (paramNames != null) {
            for (String paramName : paramNames) {
                HttpParamMetadata paramMetadata = this.mappingMetadata.getParamMetadata(paramName);
                int index = paramMetadata.index();
                Object value = this.arguments.get(index);
                if (value != null && BeanUtils.isSimpleProperty(value.getClass())) {
                    this.parameters.put(paramName, value);
                }
            }
        }

        RequestMethod requestMethod = mappingMetadata.getRequestMethod();
        if (requestMethod.equals(RequestMethod.GET) && args.length == 1 && args[0] != null) {
            Class<?> argClass = args[0].getClass();
            if (!BeanUtils.isSimpleProperty(argClass) && argClass != String.class) {
                if (Map.class.isAssignableFrom(argClass)) {
                    Map<Object, Object> valueMap = (Map<Object, Object>) args[0];
                    for (Object key : valueMap.keySet()) {
                        this.parameters.put(key.toString(), valueMap.get(key));
                    }
                } else {
                    try {
                        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>() {
                        };
                        Map<String, String> entityMap = GlobalContext.getInstance().jsonMapper().convertValue(args[0], typeReference);
                        for (String key : entityMap.keySet()) {
                            if (entityMap.get(key) != null && BeanUtils.isSimpleProperty(entityMap.get(key).getClass())) {
                                this.parameters.put(key, entityMap.get(key));
                            }
                        }
                    } catch (Exception e) {
                        logger.error("convert to map error", e);
                    }
                }
            }
        }

    }

    @Override
    public HttpMappingMetadata getMappingMetadata() {
        return mappingMetadata;
    }

    @Override
    public Object getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public void setParameter(String name, Object value) {
        this.parameters.put(name, value);
    }

    @Override
    public void removeParameter(String name) {
        this.parameters.remove(name);
    }

    @Override
    public void clearParameters() {
        this.parameters.clear();
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public void clearEntity() {
        this.entity = null;
    }

    @Override
    public String getUri() {
        Matcher matcher = PATTERN.matcher(this.mappingMetadata.getPath());
        StringBuffer sb = new StringBuffer();
        Set<String> pathVariables = new HashSet<String>();
        while (matcher.find()) {
            String match = matcher.group(1);
            String name = null;
            String defaultValue = null;
            if (match.contains(":")) {
                int spr = match.indexOf(':');
                name = match.substring(0, spr);
                defaultValue = match.substring(spr + 1);
            } else {
                name = match;
            }
            Object value = this.getParameter(name);
            if (value == null) {
                value = defaultValue;
            }
            pathVariables.add(name);
            matcher.appendReplacement(sb, toParameterString(name, value));
        }
        matcher.appendTail(sb);
        String uri = sb.toString();
        return uri.contains("?") ? (uri + getQuery("&", pathVariables)) : (uri + getQuery("?", pathVariables));
    }


    private String getQuery(String prefix, Set<String> excludes) {
        if (this.parameters.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = parameters.keySet().iterator();
        int index = 0;
        while (itr.hasNext()) {
            String name = itr.next();
            Object value = this.getParameter(name);
            if (!excludes.contains(name)) {
                if (index == 0) {
                    sb.append(prefix).append(name).append('=').append(toParameterString(name, value));
                } else {
                    sb.append('&').append(name).append('=').append(toParameterString(name, value));
                }
                index++;
            }
        }
        return sb.toString();
    }

    private String toParameterString(String name, Object value) {
        if (value == null) {
            return "";
        }
        HttpParamMetadata paramMetadata = this.mappingMetadata.getParamMetadata(name);
        if (paramMetadata != null && !paramMetadata.isEncode()) {
            return value.toString();
        }
        try {
            return URLEncoder.encode(value.toString(), this.mappingMetadata.getCharset().name());
        } catch (UnsupportedEncodingException e) {
            // impossible
            throw new HttpRequestException("Failed to encode url parameter", e);
        }
    }

    HttpUriRequest getHttpUriRequest() {
        String uri = getUri();
        switch (this.mappingMetadata.getRequestMethod()) {
            case GET:
                return new HttpGet(uri);
            case POST:
                return new HttpPost(uri);
            case DELETE:
                return new HttpDelete(uri);
            case HEAD:
                return new HttpHead(uri);
            case PUT:
                return new HttpPut(uri);
            case PATCH:
                return new HttpPatch(uri);
            case OPTIONS:
                return new HttpOptions(uri);
            case TRACE:
                return new HttpTrace(uri);
            default:
                throw new HttpRequestException("Unsupported HttpMethod : " + this.mappingMetadata.getRequestMethod());
        }
    }

    @Override
    public HttpContext getHttpContext() {
        int index = mappingMetadata.getHttpContextIndex();
        if (index < 0) {
            return null;
        }
        return (HttpContext) arguments.get(index);
    }
}
