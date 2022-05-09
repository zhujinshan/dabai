package com.dabai.proxy.httpclient.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HTTP映射
 * 
 * @author zhujinshan
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpMapping {

	/**
	 * API别名
	 * 
	 * @return 别名
	 */
	String name() default "";

	/**
	 * API路径，与path含义相同，二选一即可
	 * 
	 * @see #path()
	 * @return 绝对地址或相对路径
	 */
	String value() default "";

	/**
	 * API路径，与value含义相同，二选一即可
	 * 
	 * @return 绝对地址或相对路径
	 */
	String path() default "";

	/**
	 * 请求方法，支持：GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
	 * <p>
	 * Default: {@code GET}
	 * </p>
	 * <p>
	 * 注解到类上时，这个属性不起作用
	 * </p>
	 * 
	 * @return request method
	 */
	RequestMethod method() default RequestMethod.GET;

	/**
	 * 数据类型，支持：JSON, XML
	 * <p>
	 * Default: {@code AUTO}
	 * </p>
	 * <p>
	 * 注解到类上时，这个属性不起作用
	 * </p>
	 */
	DataType dataType() default DataType.AUTO;

	/**
	 * 字符集
	 */
	String charset() default "";
}
