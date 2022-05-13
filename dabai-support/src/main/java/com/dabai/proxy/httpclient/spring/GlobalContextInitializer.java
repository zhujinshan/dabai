package com.dabai.proxy.httpclient.spring;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.HttpClientConfig;
import com.dabai.proxy.httpclient.core.PropertyResolver;
import com.dabai.proxy.httpclient.core.handler.ResultHandler;
import com.dabai.proxy.httpclient.core.handler.ResultHandlerFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhujinshan
 */
public class GlobalContextInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientAutoConfiguration.class);

	private static final Map<String, PropertyNamingStrategy> NAMING_STRATEGY_MAPPING = new HashMap<>();
	static{
		NAMING_STRATEGY_MAPPING.put("CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES",PropertyNamingStrategy.SNAKE_CASE);
		NAMING_STRATEGY_MAPPING.put("LOWER_CASE", PropertyNamingStrategy.LOWER_CASE);
		NAMING_STRATEGY_MAPPING.put("PASCAL_CASE_TO_CAMEL_CASE", PropertyNamingStrategy.UPPER_CAMEL_CASE);
	}
	
	private HttpClientProperties properties;

	private Environment environment;

	private ResourceLoader resourceLoader;
	
	private boolean enableAsyncClient;
	
	public static GlobalContextInitializer create(){
		return new GlobalContextInitializer();
	}
	
	public GlobalContextInitializer environment(Environment environment){
		this.environment = environment;
		return this;
	}
	
	public GlobalContextInitializer resourceLoader(ResourceLoader resourceLoader){
		this.resourceLoader = resourceLoader;
		return this;
	}
	
	public GlobalContextInitializer enableAsyncClient(boolean enableAsyncClient){
		this.enableAsyncClient = enableAsyncClient;
		return this;
	}

	private void initProperties(){
		this.properties = new HttpClientProperties();
		HttpClientProperties.Request request = this.properties.getRequest();
		if(environment.getProperty("http.client.user-agent") != null){
			this.properties.setUserAgent(environment.getProperty("http.client.user-agent"));
		}
		
		//Connection Number Configuration
		if(environment.getProperty("http.client.max-conn-total") != null){
			this.properties.setMaxConnTotal(environment.getProperty("http.client.max-conn-total", Integer.class));
		}
		if(environment.getProperty("http.client.max-conn-per-route") != null){
			this.properties.setMaxConnPerRoute(environment.getProperty("http.client.max-conn-per-route", Integer.class));
		}
		
		//Idle connection Configuration
		if(environment.getProperty("http.client.evict-idle-connections") != null){
			this.properties.setEvictIdleConnections(environment.getProperty("http.client.evict-idle-connections", Boolean.class));
		}
		if(environment.getProperty("http.client.max-idle-time") != null){
			this.properties.setMaxIdleTime(environment.getProperty("http.client.max-idle-time", Long.class));
		}
		if(environment.getProperty("http.client.max-idle-time-unit") != null){
			this.properties.setMaxIdleTimeUnit(environment.getProperty("http.client.max-idle-time-unit", TimeUnit.class));
		}
		
		if(environment.getProperty("http.client.async-executor-size") != null){
			this.properties.setAsyncExecutorSize(environment.getProperty("http.client.async-executor-size", Integer.class));
		}
		if(environment.getProperty("http.client.request.expect-continue-enabled") != null){
			request.setExpectContinueEnabled(environment.getProperty("http.client.request.expect-continue-enabled", Boolean.class));
		}
		if(environment.getProperty("http.client.request.local-address") != null){
			String localAddressName = environment.getProperty("http.client.request.local-address");
			try {
				InetAddress localAddress = InetAddress.getByName(localAddressName);
				request.setLocalAddress(localAddress);
			} catch (UnknownHostException e) {
				logger.warn("Failed to Set LocalAddress : " + localAddressName, e);
			}
		}
		if(environment.getProperty("http.client.request.cookie-spec") != null){
			request.setCookieSpec(environment.getProperty("http.client.request.cookie-spec"));
		}
		if(environment.getProperty("http.client.request.redirects-enabled") != null){
			request.setRedirectsEnabled(environment.getProperty("http.client.request.redirects-enabled", Boolean.class));
		}
		if(environment.getProperty("http.client.request.relative-redirects-allowed") != null){
			request.setRelativeRedirectsAllowed(environment.getProperty("http.client.request.relative-redirects-allowed", Boolean.class));
		}
		if(environment.getProperty("http.client.request.circular-redirects-allowed") != null){
			request.setCircularRedirectsAllowed(environment.getProperty("http.client.request.circular-redirects-allowed", Boolean.class));
		}
		if(environment.getProperty("http.client.request.max-redirects") != null){
			request.setMaxRedirects(environment.getProperty("http.client.request.max-redirects", Integer.class));
		}
		if(environment.getProperty("http.client.request.authentication-enabled") != null){
			request.setAuthenticationEnabled(environment.getProperty("http.client.request.authentication-enabled", Boolean.class));
		}
		//not support
		//targetPreferredAuthSchemes,proxyPreferredAuthSchemes
		if(environment.getProperty("http.client.request.connection-request-timeout") != null){
			request.setConnectionRequestTimeout(environment.getProperty("http.client.request.connection-request-timeout", Integer.class));
		}
		if(environment.getProperty("http.client.request.connect-timeout") != null){
			request.setConnectTimeout(environment.getProperty("http.client.request.connect-timeout", Integer.class));
		}
		if(environment.getProperty("http.client.request.socket-timeout") != null){
			request.setSocketTimeout(environment.getProperty("http.client.request.socket-timeout", Integer.class));
		}
		if(environment.getProperty("http.client.request.decompression-enabled") != null){
			request.setDecompressionEnabled(environment.getProperty("http.client.request.decompression-enabled", Boolean.class));
		}
		if(environment.getProperty("http.client.request.proxy.name") != null){
			request.getProxy().setName(environment.getProperty("http.client.request.proxy.name"));
		}
		if(environment.getProperty("http.client.request.proxy.port") != null){
			request.getProxy().setPort(environment.getProperty("http.client.request.proxy.port", Integer.class));
		}
		//not support
		//redisCollection
		if(environment.getProperty("http.client.result-handlers") != null){
			properties.setResultHandlers(environment.getProperty("http.client.result-handlers", List.class));
		}
		if(environment.getProperty("http.client.json.naming-strategy") != null){
			properties.getJson().setNamingStrategy(environment.getProperty("http.client.json.naming-strategy"));
		}
		if(environment.getProperty("http.client.json.date-format") != null){
			properties.getJson().setDateFormat(environment.getProperty("http.client.json.date-format"));
		}
		
		if(environment.getProperty("http.client.log-request") != null){
			this.properties.setLogRequest(environment.getProperty("http.client.log-request", Boolean.class));
		}
		if(environment.getProperty("http.client.log-header") != null){
			this.properties.setLogHeader(environment.getProperty("http.client.log-header", Boolean.class));
		}
		if(environment.getProperty("http.client.log-response") != null){
			this.properties.setLogResponse(environment.getProperty("http.client.log-response", Boolean.class));
		}
		if(environment.getProperty("http.client.mix-mode") != null){
			this.properties.setMixMode(environment.getProperty("http.client.mix-mode", Boolean.class));
		}
	}
	
	private void initResultHandler() {
		for (String name : properties.getResultHandlers()) {
			try {
				Class<?> handlerType = resourceLoader.getClassLoader().loadClass(name);
				ResultHandler<?> resultHandler = (ResultHandler<?>) handlerType.newInstance();
				ResultHandlerFactory.getInstance().addHandler(resultHandler);
				logger.debug("Add ResultHandler : {}", resultHandler);
			} catch (ClassNotFoundException e) {
				throw new BeanCreationException("ResultHandler not found : " + name, e);
			} catch (InstantiationException e) {
				throw new BeanCreationException(
						"ResultHandler failed to Instantiation : " + name + ", are you add default constructor?", e);
			} catch (IllegalAccessException e) {
				throw new BeanCreationException(
						"ResultHandler not found : " + name + ", are you add default constructor?", e);
			} catch (ClassCastException e) {
				throw new BeanCreationException(
						"ResultHandler :" + name + " should implements " + ResultHandler.class.getName(), e);
			}
		}
	}

	private void initJsonMapper() {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (properties.getJson().getDateFormat() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(properties.getJson().getDateFormat());
			jsonMapper.setDateFormat(dateFormat);
		}
		if (properties.getJson().getNamingStrategy() != null) {
			try {
				PropertyNamingStrategy namingStrategy = NAMING_STRATEGY_MAPPING
						.get(properties.getJson().getNamingStrategy());
				if (namingStrategy == null) {
					Class<?> namingStrategyType = resourceLoader.getClassLoader()
							.loadClass(properties.getJson().getNamingStrategy());
					namingStrategy = (PropertyNamingStrategy) namingStrategyType.newInstance();
				}
				jsonMapper.setPropertyNamingStrategy(namingStrategy);
				logger.debug("Use NamingStrategy : {}", namingStrategy);
			} catch (ClassNotFoundException e) {
				throw new BeanCreationException(
						"NamingStrategy not found : " + properties.getJson().getNamingStrategy(), e);
			} catch (InstantiationException e) {
				throw new BeanCreationException("NamingStrategy failed to Instantiation : "
						+ properties.getJson().getNamingStrategy() + ", are you add default constructor?", e);
			} catch (IllegalAccessException e) {
				throw new BeanCreationException("NamingStrategy not found : " + properties.getJson().getNamingStrategy()
						+ ", are you add default constructor?", e);
			} catch (ClassCastException e) {
				throw new BeanCreationException("NamingStrategy :" + properties.getJson().getNamingStrategy()
						+ " should extends " + PropertyNamingStrategy.class.getName(), e);
			}
		}
		jsonMapper.findAndRegisterModules();
		GlobalContext.getInstance().setJsonMapper(jsonMapper);
		logger.debug("init ObjectMapper(for json) : {}", jsonMapper);
	}

	private void initXmlMapper() {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		xmlMapper.findAndRegisterModules();
		GlobalContext.getInstance().setXmlMapper(xmlMapper);
		logger.debug("init ObjectMapper(for xml) : {}", xmlMapper);
	}

	private void initAysncExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(properties.getAsyncExecutorSize());
		GlobalContext.getInstance().setAysncExecutorService(executorService);
		logger.debug("init AysncExecutorService : {}", executorService);
	}

	private void initPropertyResolver() {
		PropertyResolver propertyResolver = new PropertyResolver() {

			@Override
			public String resolvePlaceholders(String text) {
				return environment.resolvePlaceholders(text);
			}

			@Override
			public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
				return environment.resolveRequiredPlaceholders(text);
			}

			@Override
			public String getProperty(String key) {
				return environment.getProperty(key);
			}

		};
		GlobalContext.getInstance().setPropertyResolver(propertyResolver);
		logger.debug("init PropertyResolver : {}", propertyResolver);
	}
	
	private void initHttpClient(){
		CloseableHttpClient httpClient = this.properties.buildHttpClient();
		GlobalContext.getInstance().setDefaultClient(httpClient);
		logger.debug("init DefaultClient : {}", httpClient);
	}
	
	private void initHttpAsyncClient(){
		CloseableHttpAsyncClient httpClient = this.properties.buildHttpAsyncClient();
		GlobalContext.getInstance().setDefaultAsyncClient(httpClient);
		logger.debug("init DefaultAsyncClient : {}", httpClient);
	}
	
	private void initConfig(){
		HttpClientConfig config = GlobalContext.getInstance().getConfig();
		config.setLogHeader(this.properties.isLogHeader());
		config.setLogRequest(this.properties.isLogRequest());
		config.setLogResponse(this.properties.isLogResponse());
	}
	
	public void init(){
		this.initProperties();
		this.initConfig();/*需要在initProperties之后*/
		this.initJsonMapper();
		this.initXmlMapper();
		this.initAysncExecutorService();
		this.initPropertyResolver();
		this.initResultHandler();
		this.initHttpClient();
		if(this.enableAsyncClient){
			this.initHttpAsyncClient();
		}
	}

}
