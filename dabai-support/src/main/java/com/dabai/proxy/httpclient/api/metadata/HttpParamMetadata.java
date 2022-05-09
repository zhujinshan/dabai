package com.dabai.proxy.httpclient.api.metadata;

/**
 * Http参数元数据
 * 
 * @author zhujinshan
 *
 */
public interface HttpParamMetadata {

	/**
	 * 获取http参数名，Never null
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * 获取http参数在方法参数列表中的位置
	 * 
	 * @return index 0 based
	 */
	int index();

	/**
	 * 判断参数是否必须
	 * 
	 * @return true表示参数是必选参数，false表示非必选
	 */
	boolean isRequired();

	/**
	 * 判断参数是否需要编码
	 * 
	 * @return true表示需要进行编码，false表示不进行编码
	 */
	boolean isEncode();

}
