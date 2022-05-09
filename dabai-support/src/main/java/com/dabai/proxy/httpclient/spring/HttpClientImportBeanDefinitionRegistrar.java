package com.dabai.proxy.httpclient.spring;

import com.dabai.proxy.httpclient.core.GlobalContext;
import org.apache.http.client.HttpClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhujinshan
 */
public class HttpClientImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

	private static final String BASE_PACKAGES = "basePackages";
	private static final String BASE_PACKAGE_CLASSES = "basePackageClasses";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Environment environment;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	private ResourceLoader resourceLoader;
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	private HttpClientAnnotationSource createHttpClientAnnotationSource(AnnotationMetadata metadata){
		AnnotationAttributes attributes = new AnnotationAttributes(
				metadata.getAnnotationAttributes(EnableHttpClient.class.getName()));

		String[] value = attributes.getStringArray("value");
		String[] basePackages = attributes.getStringArray(BASE_PACKAGES);
		Class<?>[] basePackageClasses = attributes.getClassArray(BASE_PACKAGE_CLASSES);

		Set<String> packages = new HashSet<String>();
		packages.addAll(Arrays.asList(value));
		packages.addAll(Arrays.asList(basePackages));

		for (Class<?> typeName : basePackageClasses) {
			packages.add(ClassUtils.getPackageName(typeName));
		}

		logger.debug("packages : {}", packages);

		if (packages.isEmpty()) {
			packages.add(ClassUtils.getPackageName(metadata.getClassName()));
			logger.debug("default packages : {}", packages);
		}
		
		HttpClientAnnotationSource source = new HttpClientAnnotationSource();
		source.setPackages(packages);
		source.setEnableAsyncClient(attributes.getBoolean("enableAsyncClient"));
		
		return source;
	}
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		HttpClientAnnotationSource source = this.createHttpClientAnnotationSource(metadata);
		
		// Register HttpClientAnnotationSource
		RootBeanDefinition definition = new RootBeanDefinition(HttpClientAnnotationSource.class);
		definition.setSource(source);
		definition.setRole(AbstractBeanDefinition.ROLE_INFRASTRUCTURE);
		definition.setLazyInit(true);
		registry.registerBeanDefinition(source.getClass().getName(), definition);
		
		// Init GlobalContext
		GlobalContextInitializer.create().environment(environment).resourceLoader(resourceLoader).enableAsyncClient(source.isEnableAsyncClient()).init();
		
		// Register HttpClient
		RootBeanDefinition httpClientDefinition = new RootBeanDefinition(HttpClientFactoryBean.class);
		httpClientDefinition.getPropertyValues().add("client", GlobalContext.getInstance().getDefaultClient());
		httpClientDefinition.setRole(AbstractBeanDefinition.ROLE_APPLICATION);
		registry.registerBeanDefinition(HttpClient.class.getName(), httpClientDefinition);
		
		// Register HttpAsyncClient while enabled
		if(source.isEnableAsyncClient()){
			logger.info("EnableAsyncClient");
			RootBeanDefinition httpAsyncClientDefinition = new RootBeanDefinition(HttpAsyncClientFactoryBean.class);
			httpAsyncClientDefinition.getPropertyValues().add("client", GlobalContext.getInstance().getDefaultAsyncClient());
			httpAsyncClientDefinition.setRole(AbstractBeanDefinition.ROLE_APPLICATION);
			registry.registerBeanDefinition(HttpAsyncClient.class.getName(), httpAsyncClientDefinition);
		}
	}

}
