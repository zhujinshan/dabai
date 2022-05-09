package com.dabai.proxy.httpclient.core;

import com.dabai.proxy.httpclient.core.log.HttpMonitor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author zhujinshan
 *
 */
public class GlobalContext implements Closeable {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String HTTP_HEADERS = "http.headers";
	
	private boolean closed;

	private CloseableHttpClient defaultClient;
	
	private CloseableHttpAsyncClient defaultAsyncClient;

	private ObjectMapper jsonMapper;

	private XmlMapper xmlMapper;

	private ExecutorService aysncExecutorService;

	private PropertyResolver propertyResolver;
	
	private HttpContext baseContext;
	
	private HttpClientConfig config = new HttpClientConfig();
	
	private HttpMonitor monitor;

	private GlobalContext() {
		super();
	}
	
	public HttpContext defaultBaseContext(){
		if(this.baseContext == null){
			HttpClientContext context = HttpClientContext.create();
			
			//not thread safe ！ 这样可以吗？
//			context.setAttribute(HttpClientContext.TARGET_AUTH_STATE, new AuthState());
//		    context.setAttribute(HttpClientContext.PROXY_AUTH_STATE, new AuthState());
			
		    context.setAuthCache(new BasicAuthCache());
			context.setCookieStore(new BasicCookieStore());
			context.setCredentialsProvider(new BasicCredentialsProvider());
			this.baseContext = context;
		}
		return this.baseContext;
	}

	public HttpContext getBaseContext() {
		return baseContext;
	}

	public void setBaseContext(HttpContext baseContext) {
		this.baseContext = baseContext;
	}

	public CloseableHttpClient defaultClient() {
		if (this.defaultClient == null) {
			this.defaultClient = HttpClientBuilder.create().build();
		}
		return this.defaultClient;
	}

	public CloseableHttpClient getDefaultClient() {
		return this.defaultClient;
	}

	public void setDefaultClient(CloseableHttpClient defaultClient) {
		this.defaultClient = defaultClient;
	}
	
	public CloseableHttpAsyncClient defaultAsyncClient() {
		if (this.defaultAsyncClient == null) {
			this.defaultAsyncClient = HttpAsyncClients.createDefault();
			this.defaultAsyncClient.start();
		}
		return this.defaultAsyncClient;
	}

	public HttpClientConfig getConfig() {
		return config;
	}

	public CloseableHttpAsyncClient getDefaultAsyncClient() {
		return this.defaultAsyncClient;
	}

	public void setDefaultAsyncClient(CloseableHttpAsyncClient defaultAsyncClient) {
		this.defaultAsyncClient = defaultAsyncClient;
	}

	public ObjectMapper jsonMapper() {
		if (this.jsonMapper == null) {
			this.jsonMapper = new ObjectMapper();
			this.jsonMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
			this.jsonMapper.findAndRegisterModules();
		}
		return this.jsonMapper;
	}

	public ObjectMapper getJsonMapper() {
		return this.jsonMapper;
	}

	public void setJsonMapper(ObjectMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public XmlMapper xmlMapper() {
		if (this.xmlMapper == null) {
			this.xmlMapper = new XmlMapper();
			this.xmlMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
			this.xmlMapper.findAndRegisterModules();
		}
		return this.xmlMapper;
	}

	public XmlMapper getXmlMapper() {
		return this.xmlMapper;
	}

	public void setXmlMapper(XmlMapper xmlMapper) {
		this.xmlMapper = xmlMapper;
	}

	public ExecutorService aysncExecutorService() {
		if (this.aysncExecutorService == null) {
			this.aysncExecutorService = Executors.newFixedThreadPool(10);
		}
		return this.aysncExecutorService;
	}

	public ExecutorService getAysncExecutorService() {
		return this.aysncExecutorService;
	}

	public void setAysncExecutorService(ExecutorService aysncExecutorService) {
		this.aysncExecutorService = aysncExecutorService;
	}

	public PropertyResolver propertyResolver() {
		if (this.propertyResolver == null) {
			this.propertyResolver = new PropertyResolver() {

				@Override
				public String getProperty(String key) {
					return null;
				}

				@Override
				public String resolvePlaceholders(String text) {
					return text;
				}

				@Override
				public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
					return text;
				}

			};
		}
		return this.propertyResolver;
	}

	public PropertyResolver getPropertyResolver() {
		return this.propertyResolver;
	}

	public void setPropertyResolver(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}

	public HttpMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(HttpMonitor monitor) {
		this.monitor = monitor;
	}

	private static final GlobalContext INSTANCE = new GlobalContext();

	public static GlobalContext getInstance() {
		return INSTANCE;
	}

	@Override
	public void close() throws IOException {
		if (closed) {
			return;
		}
		if (aysncExecutorService != null) {
			aysncExecutorService.shutdown();
			logger.info("AysncExecutorService shutting down...");
			try {
				while (!aysncExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
					logger.info("waiting aysnc tasks to completing...");
				}
				logger.info("AysncExecutorService shutdown success");
			} catch (InterruptedException e) {
				logger.warn("AysncExecutorService shutdown failed", e);
			}
		}
		if(this.defaultClient != null){
			try {
				this.defaultClient.close();
				logger.info("HttpClient closed");
			} catch (Exception e) {
				logger.warn("HttpClient failed to close", e);
			}
		}
		if(this.defaultAsyncClient != null){
			try {
				this.defaultAsyncClient.close();
				logger.info("HttpAsyncClient closed");
			} catch (Exception e) {
				logger.warn("HttpAsyncClient failed to close", e);
			}
		}
		closed = true;
	}

}
