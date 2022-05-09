package com.dabai.proxy.httpclient.core;

import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.RequestProcessor;
import com.dabai.proxy.httpclient.api.exception.HttpRequestException;
import com.dabai.proxy.httpclient.api.metadata.HttpEntityMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpHeaderMetadata;
import com.dabai.proxy.httpclient.api.metadata.HttpMappingMetadata;
import com.dabai.proxy.httpclient.core.handler.ResultHandler;
import com.dabai.proxy.httpclient.core.handler.ResultHandlerFactory;
import com.dabai.proxy.httpclient.core.io.TransferHttpEntity;
import com.dabai.proxy.httpclient.core.log.HttpLog;
import com.dabai.proxy.httpclient.core.log.HttpMonitor;
import com.dabai.proxy.httpclient.core.mapper.DynamicJsonXmlResultHandler;
import com.dabai.proxy.httpclient.core.mapper.JsonResultHandler;
import com.dabai.proxy.httpclient.core.mapper.XmlResultHandler;
import com.dabai.proxy.httpclient.core.metadata.HttpClientMetadataImpl;
import com.dabai.proxy.httpclient.core.metadata.HttpMappingMetadataImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HttpRequestHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HttpClientMetadataImpl clientMetadata;

    private final ObjectMapper jsonMapper;

    private final XmlMapper xmlMapper;

    private final ResultHandlerFactory resultHandlerFactory;

    private final JsonResultHandler jsonResultHandler;

    private final XmlResultHandler xmlResultHandler;

    private final DynamicJsonXmlResultHandler dynamicJsonXmlResultHandler;

    private final ExecutorService aysncExecutorService;

    private final boolean async;

    public HttpRequestHandler(Class<?> clazz) {
        this(clazz, false);
    }

    public HttpRequestHandler(Class<?> clazz, boolean async) {
        this.async = async;
        this.clientMetadata = new HttpClientMetadataImpl(clazz);

        this.jsonMapper = GlobalContext.getInstance().jsonMapper();
        this.jsonResultHandler = new JsonResultHandler();

        this.xmlMapper = GlobalContext.getInstance().xmlMapper();
        this.xmlResultHandler = new XmlResultHandler();

        this.aysncExecutorService = GlobalContext.getInstance().aysncExecutorService();

        this.dynamicJsonXmlResultHandler = new DynamicJsonXmlResultHandler();

        this.resultHandlerFactory = ResultHandlerFactory.getInstance();
    }


    public Object invoke(Method method, Object[] args) throws Exception {

        HttpMappingMetadataImpl mappingMetadata = clientMetadata.getMappingMetadata(method);
        if (mappingMetadata == null) {
            throw new HttpRequestException("MappingMetadata not found in method : " + method);
        }

        if (Future.class == method.getReturnType()) {
            // Async support
            return aysncExecutorService.submit(() -> handle(mappingMetadata, args));

        } else {

            // handle
            Object result = handle(mappingMetadata, args);

            // result wrap
            if (Optional.class == method.getReturnType()) {
                // Optional support
                return Optional.ofNullable(result);
            } else {
                return result;
            }

        }
    }

    private boolean isText(HttpEntity entity) {
        Header contentType = entity.getContentType();
        if (contentType == null) {
            return false;
        }
        String value = contentType.getValue();
        if (value.contains("json") || value.contains("text") || value.contains("xml") || value.contains("html")) {
            return true;
        }
        return false;
    }

    private Object handle(HttpMappingMetadataImpl mappingMetadata, Object[] args) throws Exception {
        HttpLog httpLog = new HttpLog();
        HttpUriRequest uriRequest = null;
        HttpResponse response = null;
        HttpContext context = null;
        ByteArrayOutputStream responseContent = null;
        try {
            HttpMethodRequest request = new HttpMethodRequest(mappingMetadata, args);
            for (RequestProcessor processor : mappingMetadata.getRequestProcessors()) {
                processor.process(request);
            }
            httpLog.setRequestPath(request.getUri());
            uriRequest = request.getHttpUriRequest();
            putEntity(request, uriRequest, httpLog);
            for (HttpHeaderMetadata header : mappingMetadata.getHeaders()) {
                uriRequest.setHeader(header.getName(), header.getValue());
            }


            if (mappingMetadata.getHttpContextIndex() != -1) {
                context = (HttpContext) args[mappingMetadata.getHttpContextIndex()];
            }
            if (context == null) {
                context = GlobalContext.getInstance().defaultBaseContext();
            }
            //【重要】把context变成每个Request一个，保证请求时的临时变量不会影响其它请求
            context = new BasicHttpContext(context);
            {
                Header[] headers = this.getHeaders(context);
                if (headers != null) {
                    uriRequest.setHeaders(headers);
                }
            }

            httpLog.setRequestTime(System.currentTimeMillis());

            if (async) {
                Future<HttpResponse> future = GlobalContext.getInstance().defaultAsyncClient().execute(uriRequest, context, null);
                response = future.get();
            } else {
                response = GlobalContext.getInstance().defaultClient().execute(uriRequest, context);
            }

            this.check(response.getStatusLine());

            HttpEntity httpEntity = null;
            {
                HttpEntity _entity = response.getEntity();
                if (_entity == null) {
                    responseContent = new ByteArrayOutputStream();
                } else {
                    if (_entity.getContentLength() != 0) {
                        // -1表示长度未知，可能也有返回内容
                        httpLog.setHasResponseBody(true);
                    }
                    if (this.isText(_entity)) {
                        if (_entity.getContentLength() == -1) {
                            //unkown length
                            responseContent = new ByteArrayOutputStream();
                            httpEntity = new TransferHttpEntity(_entity, responseContent);
                        } else if (_entity.getContentLength() <= Integer.MAX_VALUE) {
                            responseContent = new ByteArrayOutputStream((int) _entity.getContentLength());
                            httpEntity = new TransferHttpEntity(_entity, responseContent);
                        } else {
                            //to large
                            httpEntity = _entity;
                        }
                    } else {
                        httpEntity = _entity;
                    }
                }
            }

            // 无返回值，直接返回null
            if (Void.TYPE == mappingMetadata.getMethod().getReturnType()) {
                httpLog.setResponseBody(EntityUtils.toString(httpEntity, mappingMetadata.getCharset()));
                this.completeLog(httpLog);
                return null;
            }

            // 根据DataType和ReturnType选择ResultHandler
            switch (mappingMetadata.getDataType()) {
                case AUTO:
                    ResultHandler<?> resultHandler = resultHandlerFactory
                            .getHandler(mappingMetadata.getResolvableReturnType().getRawClass());
                    if (resultHandler != null) {
                        // 如果ResultHandler存在，使用ResultHanlder处理
                        return resultHandler.handle(httpEntity, mappingMetadata.getCharset());
                    } else if (mappingMetadata.getResolvableReturnType().isInstance(response)) {
                        // 如果返回类型是response，使用原生HttpResponse返回则无需处理
                        return response;
                    } else if (httpEntity == null) {
                        return null;
                    } else if (mappingMetadata.getResolvableReturnType().isInstance(httpEntity)) {
                        // 如果返回类型是entity，使用原生HttpEntity返回则无需处理
                        return httpEntity;
                    } else {
                        // 使用JSON/XML反序列化
                        return dynamicJsonXmlResultHandler.handle(httpEntity, mappingMetadata.getCharset(),
                                mappingMetadata.getReturnType());
                    }
                case JSON:
                    if (httpEntity == null) {
                        return null;
                    }
                    return jsonResultHandler.handle(httpEntity, mappingMetadata.getCharset(), mappingMetadata.getReturnType());
                case XML:
                    if (httpEntity == null) {
                        return null;
                    }
                    return xmlResultHandler.handle(httpEntity, mappingMetadata.getCharset(), mappingMetadata.getReturnType());
                default:
                    throw new HttpRequestException("Unsupported DataType : " + mappingMetadata.getDataType());
            }
        } catch (Exception e) {
            httpLog.setException(e);
            throw e;
        } finally {
            try {
                this.logRequest(httpLog, uriRequest, context);
                this.logResponse(httpLog, response);
                if (responseContent != null) {
                    httpLog.setResponseBody(responseContent.toString(mappingMetadata.getCharset().name()));
                }
                this.completeLog(httpLog);
            } finally {
                if (response instanceof Closeable) {
                    ((Closeable) response).close();
                }
            }
        }
    }

    private void putEntity(HttpMethodRequest request, HttpUriRequest uriRequest, HttpLog httpLog) throws JsonProcessingException {
        Object entity = request.getEntity();
        if (entity == null) {
            return;
        }
        httpLog.setHasRequestBody(true);
        if (request.getMappingMetadata().containsEntity() && uriRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntityMetadata entityMetadata = request.getMappingMetadata().getEntityMetadata();
            HttpEntity httpEntity = null;
            switch (entityMetadata.getDataType()) {
                case URL_ENCODED_FORM:
                    Map<Object, Object> mapEntity;
                    if (entity instanceof Map) {
                        mapEntity = (Map<Object, Object>) entity;
                    } else {
                        mapEntity = GlobalContext.getInstance().getJsonMapper().convertValue(entity, Map.class);
                    }
                    if (mapEntity.size() > 0) {
                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        for (Object key : mapEntity.keySet()) {
                            Object o = mapEntity.get(key);
                            if (o instanceof List) {
                                List<Object> listValue = (List<Object>) o;
                                listValue.forEach(e -> nameValuePairs.add(new BasicNameValuePair(key.toString(), e.toString())));
                                continue;
                            }
                            if (o.getClass().isArray()) {
                                int length = Array.getLength(o);
                                for (int i = 0; i < length; i++) {
                                    nameValuePairs.add(new BasicNameValuePair(key.toString(), Array.get(o, i).toString()));
                                }
                                continue;
                            }
                            nameValuePairs.add(new BasicNameValuePair(key.toString(), mapEntity.get(key).toString()));
                        }
                        httpEntity = new UrlEncodedFormEntity(nameValuePairs, Charset.defaultCharset());
                    }
                    break;
                case AUTO:
                    if (entity instanceof CharSequence) {
                        String post = entity.toString();
                        httpLog.setRequestBody(post);
                        httpEntity = new StringEntity(post, request.getMappingMetadata().getCharset());
                    } else if (entity instanceof InputStream) {
                        httpLog.setRequestBody("<<Stream:" + entity + ">>");
                        httpEntity = new InputStreamEntity((InputStream) entity);
                    } else if (entity instanceof File) {
                        httpLog.setRequestBody("<<File:" + entity + ">>");
                        httpEntity = new FileEntity((File) entity);
                    } else if (entity instanceof byte[]) {
                        byte[] data = (byte[]) entity;
                        httpLog.setRequestBody("<<Bytes[" + data.length + ">>");
                        httpEntity = new ByteArrayEntity(data);
                    } else if (entity instanceof Number) {
                        String post = entity.toString();
                        httpLog.setRequestBody(post);
                        httpEntity = new StringEntity(post, request.getMappingMetadata().getCharset());
                    } else if (entity instanceof HttpEntity) {
                        httpLog.setRequestBody("<<HttpEntity:" + entity + ">>");
                        httpEntity = (HttpEntity) entity;
                    } else {
                        // 如果HttpMapping指定了数据类型，那么使用指定的序列化方法
                        if (DataType.JSON == request.getMappingMetadata().getDataType()) {
                            httpEntity = createJsonEntity(request.getMappingMetadata(), entity, httpLog);
                        } else if (DataType.XML == request.getMappingMetadata().getDataType()) {
                            httpEntity = createXmlEntity(request.getMappingMetadata(), entity, httpLog);
                        } else {
                            // 默认使用JSON序列化方案
                            httpEntity = createJsonEntity(request.getMappingMetadata(), entity, httpLog);
                        }
                    }
                    break;

                case JSON:
                    httpEntity = createJsonEntity(request.getMappingMetadata(), entity, httpLog);
                    break;
                case XML:
                    httpEntity = createXmlEntity(request.getMappingMetadata(), entity, httpLog);
                    break;
                default:
                    break;
            }

            ((HttpEntityEnclosingRequest) uriRequest).setEntity(httpEntity);
        }
    }

    private StringEntity createJsonEntity(HttpMappingMetadata mappingMetadata, Object entity, HttpLog httpLog)
            throws JsonProcessingException {
        String json = mappingMetadata.getEntityMetadata().containsJsonView() ? jsonMapper
                .writerWithView(mappingMetadata.getEntityMetadata().getJsonViewClass()).writeValueAsString(entity)
                : jsonMapper.writeValueAsString(entity);
        httpLog.setRequestBody(json);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }

    private HttpEntity createXmlEntity(HttpMappingMetadata mappingMetadata, Object entity, HttpLog httpLog)
            throws JsonProcessingException {
        String xml = mappingMetadata.getEntityMetadata().containsJsonView() ? xmlMapper
                .writerWithView(mappingMetadata.getEntityMetadata().getJsonViewClass()).writeValueAsString(entity)
                : xmlMapper.writeValueAsString(entity);
        httpLog.setRequestBody(xml);
        return new StringEntity(xml, ContentType.APPLICATION_ATOM_XML);
    }

    private Header[] getHeaders(HttpContext context) {
        return (Header[]) context.getAttribute(GlobalContext.HTTP_HEADERS);
    }

    private void check(StatusLine statusLine) throws HttpResponseException {
        if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
    }

    private void logRequest(HttpLog httpLog, HttpUriRequest request, HttpContext context) {
        HttpRequest wrappedRequest = null;
        Object _request = context != null ? context.getAttribute(HttpClientContext.HTTP_REQUEST) : null;
        if (_request instanceof HttpRequest) {
            wrappedRequest = (HttpRequest) _request;
        } else {
            wrappedRequest = request;
        }
        if (wrappedRequest == null) {
            return;
        }
        if (wrappedRequest.getProtocolVersion() != null) {
            httpLog.setProtocolVersion(wrappedRequest.getProtocolVersion().toString());
        }
        httpLog.setRequestUri(wrappedRequest.getRequestLine().getUri());
        httpLog.setRequestMethod(wrappedRequest.getRequestLine().getMethod());
        Header[] headers = wrappedRequest.getAllHeaders();
        if (headers != null) {
            for (Header header : headers) {
                httpLog.addRequestHeader(header.getName(), header.getValue());
            }
        }
    }

    private void logResponse(HttpLog httpLog, HttpResponse response) {
        if (response == null) {
            return;
        }
        StatusLine statusLine = response.getStatusLine();
        if (statusLine != null) {
            httpLog.setStatusCode(statusLine.getStatusCode());
            httpLog.setReasonPhrase(statusLine.getReasonPhrase());
            if (statusLine.getProtocolVersion() != null) {
                httpLog.setProtocolVersion(statusLine.getProtocolVersion().toString());
            }
        }
        Header[] headers = response.getAllHeaders();
        if (headers != null) {
            for (Header header : headers) {
                httpLog.addResponseHeader(header.getName(), header.getValue());
            }
        }
    }

    private void print(HttpLog httpLog) {
        HttpClientConfig config = GlobalContext.getInstance().getConfig();
        logger.info(">> path: {}", httpLog.getRequestPath());
        if (config.isLogRequest()) {
            logger.info(">> {} {} {}", httpLog.getRequestMethod(), httpLog.getRequestUri(), httpLog.getProtocolVersion());
        }
        if (config.isLogHeader()) {
            for (Map.Entry<String, String> entry : httpLog.getRequestHeaders().entrySet()) {
                logger.info(">> {}: {}", entry.getKey(), entry.getValue());
            }
        }
        if (config.isLogRequest()) {
            if (httpLog.isHasRequestBody()) {
                if (httpLog.getRequestBody() != null) {
                    logger.info(">> {}", httpLog.getRequestBody());
                } else {
                    logger.info(">> [request body cannot convert to string]");
                }
            }
        }

        if (config.isLogResponse()) {
            logger.info("<< {} {} {}", httpLog.getProtocolVersion(), httpLog.getStatusCode(), httpLog.getReasonPhrase());
        }

        if (config.isLogHeader()) {
            for (Map.Entry<String, String> entry : httpLog.getResponseHeaders().entrySet()) {
                logger.info("<< {}: {}", entry.getKey(), entry.getValue());
            }
        }

        if (config.isLogResponse()) {
            if (httpLog.isHasResponseBody()) {
                if (httpLog.getResponseBody() != null) {
                    logger.info("<< {}", httpLog.getResponseBody());
                } else {
                    logger.info("<< [response body cannot convert to string]");
                }
            }
        }
    }

    private void completeLog(HttpLog httpLog) {
        httpLog.setResponseTime(System.currentTimeMillis());
        this.print(httpLog);
        HttpMonitor monitor = GlobalContext.getInstance().getMonitor();
        if (monitor != null) {
            try {
                monitor.on(httpLog);
            } catch (Exception e) {
                logger.error("HttpMonitor error", e);
            }
        }
    }

}
