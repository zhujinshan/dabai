package com.dabai.proxy.httpclient.api;

/**
 * http请求处理interface，应用程序可以通过实现本interface对接口的调用增加额外操作
 * 
 * @author zhujinshan
 *
 */
public interface RequestProcessor {

	/**
	 * 
	 * @param request 
	 */
	void process(HttpRequest request) throws Exception;

}
