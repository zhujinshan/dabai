package com.dabai.proxy.httpclient.spring;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhujinshan
 */
public class HttpClientProperties {
	
	protected final static Logger logger = LoggerFactory.getLogger(HttpClientProperties.class);

	private String userAgent;

	private Integer maxConnTotal;

	private Integer maxConnPerRoute;
	
	private int asyncExecutorSize = 10;
	
	private boolean evictIdleConnections;
	
	private long maxIdleTime = 10000;
	
	private TimeUnit maxIdleTimeUnit = TimeUnit.MILLISECONDS;

	private Request request = new Request();
	
	private List<String> resultHandlers = new ArrayList<>();
	
	private boolean mixMode = true;
	
	private boolean logRequest = true;
	
	private boolean logHeader = false;
	
	private boolean logResponse = true;

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Integer getMaxConnTotal() {
		return maxConnTotal;
	}

	public void setMaxConnTotal(Integer maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}

	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}

	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}

	public int getAsyncExecutorSize() {
		return asyncExecutorSize;
	}

	public void setAsyncExecutorSize(int asyncExecutorSize) {
		this.asyncExecutorSize = asyncExecutorSize;
	}

	public boolean isEvictIdleConnections() {
		return evictIdleConnections;
	}

	public void setEvictIdleConnections(boolean evictIdleConnections) {
		this.evictIdleConnections = evictIdleConnections;
	}

	public long getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(long maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public TimeUnit getMaxIdleTimeUnit() {
		return maxIdleTimeUnit;
	}

	public void setMaxIdleTimeUnit(TimeUnit maxIdleTimeUnit) {
		this.maxIdleTimeUnit = maxIdleTimeUnit;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	static class Request {

		private Boolean expectContinueEnabled;

		private Proxy proxy = new Proxy();

		private InetAddress localAddress;

		private String cookieSpec;

		private Boolean redirectsEnabled;

		private Boolean relativeRedirectsAllowed;

		private Boolean circularRedirectsAllowed;

		private Integer maxRedirects;

		private Boolean authenticationEnabled;

		private Collection<String> targetPreferredAuthSchemes;

		private Collection<String> proxyPreferredAuthSchemes;

		private Integer connectionRequestTimeout;

		private Integer connectTimeout;

		private Integer socketTimeout;

		private Boolean decompressionEnabled;

		public Boolean getExpectContinueEnabled() {
			return expectContinueEnabled;
		}

		public void setExpectContinueEnabled(Boolean expectContinueEnabled) {
			this.expectContinueEnabled = expectContinueEnabled;
		}

		public Proxy getProxy() {
			return proxy;
		}

		public void setProxy(Proxy proxy) {
			this.proxy = proxy;
		}

		public InetAddress getLocalAddress() {
			return localAddress;
		}

		public void setLocalAddress(InetAddress localAddress) {
			this.localAddress = localAddress;
		}

		public String getCookieSpec() {
			return cookieSpec;
		}

		public void setCookieSpec(String cookieSpec) {
			this.cookieSpec = cookieSpec;
		}

		public Boolean getRedirectsEnabled() {
			return redirectsEnabled;
		}

		public void setRedirectsEnabled(Boolean redirectsEnabled) {
			this.redirectsEnabled = redirectsEnabled;
		}

		public Boolean getRelativeRedirectsAllowed() {
			return relativeRedirectsAllowed;
		}

		public void setRelativeRedirectsAllowed(Boolean relativeRedirectsAllowed) {
			this.relativeRedirectsAllowed = relativeRedirectsAllowed;
		}

		public Boolean getCircularRedirectsAllowed() {
			return circularRedirectsAllowed;
		}

		public void setCircularRedirectsAllowed(Boolean circularRedirectsAllowed) {
			this.circularRedirectsAllowed = circularRedirectsAllowed;
		}

		public Integer getMaxRedirects() {
			return maxRedirects;
		}

		public void setMaxRedirects(Integer maxRedirects) {
			this.maxRedirects = maxRedirects;
		}

		public Boolean getAuthenticationEnabled() {
			return authenticationEnabled;
		}

		public void setAuthenticationEnabled(Boolean authenticationEnabled) {
			this.authenticationEnabled = authenticationEnabled;
		}

		public Collection<String> getTargetPreferredAuthSchemes() {
			return targetPreferredAuthSchemes;
		}

		public void setTargetPreferredAuthSchemes(Collection<String> targetPreferredAuthSchemes) {
			this.targetPreferredAuthSchemes = targetPreferredAuthSchemes;
		}

		public Collection<String> getProxyPreferredAuthSchemes() {
			return proxyPreferredAuthSchemes;
		}

		public void setProxyPreferredAuthSchemes(Collection<String> proxyPreferredAuthSchemes) {
			this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
		}

		public Integer getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}

		public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
		}

		public Integer getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Integer getSocketTimeout() {
			return socketTimeout;
		}

		public void setSocketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
		}

		public Boolean getDecompressionEnabled() {
			return decompressionEnabled;
		}

		public void setDecompressionEnabled(Boolean decompressionEnabled) {
			this.decompressionEnabled = decompressionEnabled;
		}

	}

	static class Proxy {

		private String name;

		private Integer port;
		
		private String redisCollection;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getRedisCollection() {
			return redisCollection;
		}

		public void setRedisCollection(String redisCollection) {
			this.redisCollection = redisCollection;
		}

	}

	public List<String> getResultHandlers() {
		return resultHandlers;
	}

	public void setResultHandlers(List<String> resultHandlers) {
		this.resultHandlers = resultHandlers;
	}
	
	private Json json = new Json();
	
	public Json getJson() {
		return json;
	}

	public void setJson(Json json) {
		this.json = json;
	}

	static class Json{
		
		private String dateFormat;
		
		private String namingStrategy;

		public String getDateFormat() {
			return dateFormat;
		}

		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}

		public String getNamingStrategy() {
			return namingStrategy;
		}

		public void setNamingStrategy(String namingStrategy) {
			this.namingStrategy = namingStrategy;
		}
		
	}
	
	public RequestConfig buildRequestConfig() {
		RequestConfig.Builder builder = RequestConfig.custom();
		if (request.getExpectContinueEnabled() != null) {
			builder.setExpectContinueEnabled(request.getExpectContinueEnabled());
		}
		if (request.getLocalAddress() != null) {
			builder.setLocalAddress(request.getLocalAddress());
		}
		if (request.getCookieSpec() != null) {
			builder.setCookieSpec(request.getCookieSpec());
		}
		if (request.getRedirectsEnabled() != null) {
			builder.setRedirectsEnabled(request.getRedirectsEnabled());
		}
		if (request.getRelativeRedirectsAllowed() != null) {
			builder.setRelativeRedirectsAllowed(request.getRelativeRedirectsAllowed());
		}
		if (request.getCircularRedirectsAllowed() != null) {
			builder.setCircularRedirectsAllowed(request.getCircularRedirectsAllowed());
		}
		if (request.getMaxRedirects() != null) {
			builder.setMaxRedirects(request.getMaxRedirects());
		}
		if (request.getAuthenticationEnabled() != null) {
			builder.setAuthenticationEnabled(request.getAuthenticationEnabled());
		}
		if (!CollectionUtils.isEmpty(request.getTargetPreferredAuthSchemes())) {
			builder.setTargetPreferredAuthSchemes(request.getTargetPreferredAuthSchemes());
		}
		if (!CollectionUtils.isEmpty(request.getProxyPreferredAuthSchemes())) {
			builder.setProxyPreferredAuthSchemes(request.getProxyPreferredAuthSchemes());
		}
		if (request.getConnectionRequestTimeout() != null) {
			builder.setConnectionRequestTimeout(request.getConnectionRequestTimeout());
		}
		if (request.getConnectTimeout() != null) {
			builder.setConnectTimeout(request.getConnectTimeout());
		}
		if (request.getSocketTimeout() != null) {
			builder.setSocketTimeout(request.getSocketTimeout());
		}
		if (request.getDecompressionEnabled() != null) {
			builder.setDecompressionEnabled(request.getDecompressionEnabled());
		}
		Proxy proxy = request.getProxy();
		if (proxy != null && proxy.getName() != null && proxy.getPort() != null) {
			builder.setProxy(new HttpHost(proxy.getName(), proxy.getPort()));
		}
		return builder.build();
	}
	
	public HttpClientBuilder builder() {
		RequestConfig config = this.buildRequestConfig();
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config);
		if(evictIdleConnections){
			builder.evictIdleConnections(maxIdleTime, maxIdleTimeUnit);
		}
		if (this.getUserAgent() != null) {
			builder.setUserAgent(this.getUserAgent());
		}
		if (this.getMaxConnTotal() != null) {
			builder.setMaxConnTotal(this.getMaxConnTotal());
		}
		if (this.getMaxConnPerRoute() != null) {
			builder.setMaxConnPerRoute(this.getMaxConnPerRoute());
		}
		//特性：从HttpContext中获取代理(targetHost)
		//注意：只有当RequestConfig中没设代理是才会生效
		builder.setRoutePlanner(new DefaultContextProxyRoutePlanner(DefaultSchemePortResolver.INSTANCE));
		
		//无效代理RetryHandler
//		String redisCollection = properties.getRequest().getProxy().getRedisCollection();
//		if(stringRedisTemplate != null && redisCollection != null){
//			Supplier<HttpHost> httpHostSupplier = new RedisHttpHostSupplier(stringRedisTemplate, redisCollection);
//			HttpRequestRetryHandler retryHandler = new ProxyUnavailableRetryHandler(3, httpHostSupplier);
//			builder.setRetryHandler(retryHandler);
//		}
		
		if(this.isMixMode()){
			System.setProperty("jsse.enableSNIExtension", "false");
			System.setProperty("https.protocols", "TLSv1");
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("TLSv1");
				sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			builder.setSSLHostnameVerifier(new HostnameVerifier() {

						@Override
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}

					}).setSslcontext(sslcontext);
			logger.info("Use HttpClient mix mode");
		}
		
		return builder;
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	public CloseableHttpClient buildHttpClient() {
		return this.builder().build();
	}
	
	public HttpAsyncClientBuilder asyncClientBuilder() {
		RequestConfig config = this.buildRequestConfig();
		HttpAsyncClientBuilder builder = HttpAsyncClients.custom().setDefaultRequestConfig(config);
		if (this.getUserAgent() != null) {
			builder.setUserAgent(this.getUserAgent());
		}
		if (this.getMaxConnTotal() != null) {
			builder.setMaxConnTotal(this.getMaxConnTotal());
		}
		if (this.getMaxConnPerRoute() != null) {
			builder.setMaxConnPerRoute(this.getMaxConnPerRoute());
		}
		if(this.isMixMode()){
			System.setProperty("jsse.enableSNIExtension", "false");
			System.setProperty("https.protocols", "TLSv1");
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("TLSv1");
				sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			builder.setSSLHostnameVerifier(new HostnameVerifier() {

						@Override
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}

					}).setSSLContext(sslcontext);
			logger.info("Use HttpClient mix mode");
		}
		// builder.addInterceptorFirst(itcp)
		// 特性：从HttpContext中获取代理(targetHost)
		// 注意：只有当RequestConfig中没设代理是才会生效
		builder.setRoutePlanner(new DefaultContextProxyRoutePlanner(DefaultSchemePortResolver.INSTANCE));
		return builder;
	}
	
	public CloseableHttpAsyncClient buildHttpAsyncClient() {
		CloseableHttpAsyncClient client = this.asyncClientBuilder().build();
		client.start();
		return client;
	}
	
	public XmlMapper buildXmlMapper() {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		return xmlMapper;
	}
	
	public ExecutorService buildAysncExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(this.asyncExecutorSize);
		return executorService;
	}

	public boolean isMixMode() {
		return mixMode;
	}

	public void setMixMode(boolean mixMode) {
		this.mixMode = mixMode;
	}

	public boolean isLogRequest() {
		return logRequest;
	}

	public void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	public boolean isLogHeader() {
		return logHeader;
	}

	public void setLogHeader(boolean logHeader) {
		this.logHeader = logHeader;
	}

	public boolean isLogResponse() {
		return logResponse;
	}

	public void setLogResponse(boolean logResponse) {
		this.logResponse = logResponse;
	}

}
