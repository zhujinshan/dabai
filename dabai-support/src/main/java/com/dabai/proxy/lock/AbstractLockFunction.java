package com.dabai.proxy.lock;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author zhujinshan
 */
public abstract class AbstractLockFunction implements LockFunction {

	@Override
	public void execute(Collection<LockObject> objects, Runnable task) {
        Assert.isTrue(!CollectionUtils.isEmpty(objects), "objects cannot be empty");
		// XXX 是否应该去重？
		LockObject[] locks = objects.toArray(new LockObject[objects.size()]);
		Arrays.sort(locks);
		executeWithoutResult(locks, task, 0);
	}

	@Override
	public <R> R execute(Collection<LockObject> objects, Supplier<R> function) {
        Assert.isTrue(!CollectionUtils.isEmpty(objects), "objects cannot be empty");
		LockObject[] locks = objects.toArray(new LockObject[objects.size()]);
		Arrays.sort(locks);
		return executeWithResult(locks, function, 0);
	}

	@Override
	public void execute(Runnable task, LockObject... objects) {
        Assert.isTrue(objects.length > 0, "objects cannot be empty");
		LockObject[] locks = new LockObject[objects.length];
		System.arraycopy(objects, 0, locks, 0, objects.length);
		Arrays.sort(locks);
		executeWithoutResult(locks, task, 0);
	}

	@Override
	public <R> R execute(Supplier<R> function, LockObject... objects) {
        Assert.isTrue(objects.length > 0, "objects cannot be empty");
		LockObject[] locks = new LockObject[objects.length];
		System.arraycopy(objects, 0, locks, 0, objects.length);
		Arrays.sort(locks);
		return executeWithResult(locks, function, 0);
	}

	/**
	 * 无结果执行函数<br>
	 * 实现方应该从{@code locks[index]}开始递归执行
	 * 
	 * @param locks
	 *            锁对象的数组
	 * @param task
	 *            执行的任务
	 * @param index
	 *            锁的下标
	 */
	protected abstract void executeWithoutResult(LockObject[] locks, Runnable task, int index);

	/**
	 * 有结果执行函数<br>
	 * 实现方应该从{@code locks[index]}开始递归执行
	 * 
	 * @param locks
	 *            锁对象的数组
	 * @param function
	 *            执行的函数
	 * @param index
	 *            锁的下标
	 * @return 返回结果
	 */
	protected abstract <R> R executeWithResult(LockObject[] locks, Supplier<R> function, int index);

}
