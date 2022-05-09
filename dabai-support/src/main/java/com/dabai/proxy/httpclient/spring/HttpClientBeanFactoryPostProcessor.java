package com.dabai.proxy.httpclient.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import java.util.Set;

/**
 * @author zhujinshan
 */
public class HttpClientBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ResourceLoader resourceLoader;
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		
		logger.debug("Current ClassLoader : {}", resourceLoader.getClassLoader());
		
		logger.debug("System ClassLoader : {}", ClassLoader.getSystemClassLoader());
		
		BeanDefinition sourceBeanDefinition = registry.getBeanDefinition(HttpClientAnnotationSource.class.getName());
		HttpClientAnnotationSource source = (HttpClientAnnotationSource) sourceBeanDefinition.getSource();

		HttpClientComponentProvider provider = new HttpClientComponentProvider();

		for (String p : source.getPackages()) {
			Set<BeanDefinition> candidates = provider.findCandidateComponents(p);
			for (BeanDefinition beanDefinition : candidates) {
				String beanClassName = beanDefinition.getBeanClassName();
				AbstractBeanDefinition definition = (AbstractBeanDefinition) beanDefinition;
				definition.getPropertyValues().add("beanInterfaceName", beanClassName);
				definition.getPropertyValues().add("enableAsyncClient", source.isEnableAsyncClient());
				definition.setBeanClassName(HttpClientProxyFactoryBean.class.getName());
				definition.setBeanClass(HttpClientProxyFactoryBean.class);
				definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
				registry.registerBeanDefinition(beanClassName, definition);
				logger.debug("HttpClient register : {}", beanClassName);
			}
		}

		registry.removeBeanDefinition(HttpClientAnnotationSource.class.getName());

	}

}
