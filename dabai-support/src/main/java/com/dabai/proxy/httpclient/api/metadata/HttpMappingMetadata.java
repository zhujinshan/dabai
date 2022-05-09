package com.dabai.proxy.httpclient.api.metadata;

import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.RequestMethod;
import com.dabai.proxy.httpclient.api.RequestProcessor;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Http映射元数据
 * 
 * @author zhujinshan
 *
 */
public interface HttpMappingMetadata {

	/**
	 * 获取映射所属方法，Never null
	 * 
	 * @return method
	 */
	Method getMethod();

	/**
	 * 获取映射的名称
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * 获取映射的路径<br>
	 * 此路径并非uri，因为路径里可以包含参数或变量的placeholder
	 * 
	 * @return path
	 */
	String getPath();

	/**
	 * 获取请求方法，Never null
	 * 
	 * @return requestMethod
	 */
	RequestMethod getRequestMethod();

	/**
	 * 获取数据类型，Never null<br>
	 * 
	 * @return DataType
	 */
	DataType getDataType();

	/**
	 * 获取字符集，Never null<br>
	 * 不做任何配置的话默认是utf8
	 * 
	 * @return charset
	 */
	Charset getCharset();

	/**
	 * 判断Http映射的参数列表中是否包含实体<br>
	 * 
	 * @return 包含实体参数返回true，否则返回false
	 */
	boolean containsEntity();

	/**
	 * 获取实体参数的原信息
	 * 
	 * @return entityMetadata，如果不存在返回null
	 */
	HttpEntityMetadata getEntityMetadata();

	/**
	 * 获取Http映射的参数名数组<br>
	 * 请注意：Http参数与方法的参数列表可能不一致，Http参数排除了实体参数（HttpEntity）和ClientProvider参数(
	 * HttpClientProvider)
	 * 
	 * @return paramNames
	 */
	String[] getParamNames();

	/**
	 * 获取Http参数的元数据
	 * 
	 * @param name
	 *            参数名
	 * @return paramMetadata，如果不存在返回null
	 */
	HttpParamMetadata getParamMetadata(String name);

	RequestProcessor[] getRequestProcessors();
	
	HttpHeaderMetadata[] getHeaders();
	
	/**
	 * 获取HttpContext参数在方法参数列表中的位置
	 * 
	 * @return index，不过不存在HttpContext参数返回-1
	 */
	int getHttpContextIndex();

}
