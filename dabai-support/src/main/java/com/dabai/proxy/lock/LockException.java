package com.dabai.proxy.lock;

/**
 * 锁异常<br>
 * 超时异常、连接异常等
 * 
 * @author liuwu
 *
 */
public class LockException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public LockException() {
		super();
	}

	public LockException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockException(String s) {
		super(s);
	}

	public LockException(Throwable cause) {
		super(cause);
	}

}
