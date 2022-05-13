package com.dabai.proxy.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 
 * @author liuwu
 * @date 2017年12月13日
 *
 */
public class JdkLockFunction extends AbstractLockFunction implements LockFunction {

	private static final Logger logger = LoggerFactory.getLogger(JdkLockFunction.class);

	private Map<LockObject, Lock> locks = new HashMap<>();

	static class Lock {
		/**
		 * 锁对象
		 */
		final LockObject object;
		/**
		 * 在当前对象上等待的线程数
		 */
		final AtomicInteger waitings;

		public Lock(LockObject object) {
			this.object = object;
			// 构造的新锁，默认等待
			this.waitings = new AtomicInteger(1);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Lock [");
			if (object != null) {
				builder.append("object=");
				builder.append(object);
				builder.append(", ");
			}
			if (waitings != null) {
				builder.append("waitings=");
				builder.append(waitings);
			}
			builder.append("]");
			return builder.toString();
		}

	}

	private LockObject getLock(LockObject lockObject) {
		synchronized (locks) {
			Lock cachedLock = locks.get(lockObject);
			if (cachedLock == null) {
				cachedLock = new Lock(lockObject);
				locks.put(lockObject, cachedLock);
			} else {
				cachedLock.waitings.incrementAndGet();
			}
			logger.trace("getLock : {}", cachedLock);
			return cachedLock.object;
		}
	}

	private void releaseLock(LockObject lockObject) {
		synchronized (locks) {
			Lock lock = locks.get(lockObject);
			int waitings = lock.waitings.decrementAndGet();
			if (waitings < 1) {
				// 只有去报当前锁没有任何的等待线程，才能够删除对象
				locks.remove(lockObject);
				logger.trace("releaseLock : {}", lock);
			} else {
				logger.trace("holdingLock : {}", lock);
			}
		}
	}

	@Override
	public void execute(LockObject object, Runnable task) {
		Assert.notNull(object, "object is required");
        Assert.notNull(task, "task is required");
		LockObject lock = getLock(object);
		try {
			synchronized (lock) {
				logger.debug("acquire lock : {}", lock);
				task.run();
				logger.debug("release lock : {}", lock);
			}
		} finally {
			releaseLock(lock);
		}
	}

	@Override
	public <R> R execute(LockObject object, Supplier<R> function) {
        Assert.notNull(object, "object is required");
        Assert.notNull(function, "task is required");
		LockObject lock = getLock(object);
		try {
			synchronized (lock) {
				logger.debug("acquire lock : {}", lock);
				R result = function.get();
				logger.debug("release lock : {}", lock);
				return result;
			}
		} finally {
			releaseLock(lock);
		}
	}

	@Override
	protected void executeWithoutResult(LockObject[] locks, Runnable task, int index) {
		LockObject object = locks[index];
		LockObject lock = getLock(object);
		try {
			synchronized (lock) {
				logger.debug("acquire lock : {}", lock);
				int next = index + 1;
				if (next < locks.length) {
					executeWithoutResult(locks, task, next);
				} else {
					task.run();
				}
				logger.debug("release lock : {}", lock);
			}
		} finally {
			releaseLock(lock);
		}
	}

	@Override
	protected <R> R executeWithResult(LockObject[] locks, Supplier<R> function, int index) {
		LockObject object = locks[index];
		LockObject lock = getLock(object);
		synchronized (lock) {
			try {
				int next = index + 1;
				if (next < locks.length) {
					return executeWithResult(locks, function, next);
				} else {
					return function.get();
				}
			} finally {
				releaseLock(lock);
			}
		}
	}
}
