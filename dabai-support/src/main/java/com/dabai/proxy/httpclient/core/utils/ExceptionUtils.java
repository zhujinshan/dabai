package com.dabai.proxy.httpclient.core.utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;

public class ExceptionUtils {

	/**
	 * 获取原来的异常
	 * 
	 * @param e
	 *            异常
	 * @return 原来的异常
	 */
	public static Throwable getOriginal(Throwable e) {
		Throwable cause = null;
		if (e.getCause() != null) {
			if (e instanceof UndeclaredThrowableException) {
				// 使用JDK代理抛出异常会封装成UndeclaredThrowableException
				cause = e.getCause();
			} else {
				cause = e;
			}
		} else {
			cause = e;
		}
		if (cause instanceof ExecutionException) {
			Throwable rootCause = cause.getCause();
			if (rootCause != null) {
				cause = rootCause;
			}
		}
		return cause;
	}

}
