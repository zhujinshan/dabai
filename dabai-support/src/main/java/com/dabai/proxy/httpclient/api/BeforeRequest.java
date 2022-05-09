package com.dabai.proxy.httpclient.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeforeRequest {

	/**
	 * 接口调用前执行的额外操作，比如：增加全局参数，记录日志
	 */
	Class<? extends RequestProcessor>[] value();
}
