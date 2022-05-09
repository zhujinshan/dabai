package com.dabai.proxy.httpclient.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HttpParam {

	/**
	 * 参数名<br>
	 * name/value二选一即可
	 */
	String value() default "";

	/**
	 * 参数名<br>
	 * name/value二选一即可
	 */
	String name() default "";

	/**
	 * 参数默认值，当被注解的参数传入null时会使用这个默认值
	 */
	String defaultValue() default "";

	/**
	 * 是否为必须参数，默认为非必须的<br>
	 * 如果设为true，那么当参数为empty时（考虑了defaultValue之后），会抛出IllegalArgumentException异常
	 */
	boolean required() default false;

	/**
	 * 是否进行URL encode，默认会进行编码
	 */
	boolean encode() default true;

}