package com.dabai.proxy.httpclient.spring;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujinshan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({HttpClientImportBeanDefinitionRegistrar.class, HttpClientAutoConfiguration.class })
public @interface EnableHttpClient {

	/**
	 * HttpClient service所在的包
	 */
	@AliasFor("basePackages")
	String[] value() default {};

	@AliasFor("value")
	String[] basePackages() default {};

	Class<?>[] basePackageClasses() default {};
	
	boolean enableAsyncClient() default false;

}
