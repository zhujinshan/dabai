package com.dabai.proxy.httpclient.spring;

import com.dabai.proxy.httpclient.api.HttpClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author zhujinshan
 */
public class HttpClientComponentProvider extends ClassPathScanningCandidateComponentProvider {

	public HttpClientComponentProvider() {
		super(false);
		super.addIncludeFilter(new HttpClientTypeFilter());
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return (beanDefinition.getMetadata().isInterface() || beanDefinition.getMetadata().isAbstract())
				&& beanDefinition.getMetadata().isIndependent();
	}
	
	static class HttpClientTypeFilter implements TypeFilter {
		
		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
				throws IOException {
			AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
			boolean match = annotationMetadata
					.isAnnotated(HttpClient.class.getName());
			return match;
		}

	}

}