package com.dabai.proxy.lock;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 锁函数<br>
 * 
 * 使用建议：
 * 加锁的地方会成为即使集群部署也无法突破的性能瓶颈，请勿滥用。高并发时，同步调用应该去考量锁的性能损耗。能用无锁数据结构，就不要用锁。尽可能使加锁的代码块工作量尽可能的小，避免在锁代码块中调用
 * RPC 方法。锁对象的范围尽可能小，使线程在不同的锁对象上可以并行。
 * 
 * @author zhujinshan
 *
 */
public interface LockFunction {

	/**
	 * 获取指定对象的锁，执行指定函数<br>
	 * 
	 * @param object
	 *            加锁的对象
	 * @param task
	 *            需要执行的任务函数，函数不应该抛出非运行时异常
	 * @return 函数返回结果
	 */
	void execute(LockObject object, Runnable task);

	/**
	 * 获取指定对象的锁，执行指定函数，获取返回值<br>
	 * 
	 * @param object
	 *            加锁的对象
	 * @param function
	 *            需要执行的函数，函数不应该抛出非运行时异常
	 * @return 函数返回结果
	 */
	<R> R execute(LockObject object, Supplier<R> function);
	

	/**
	 * 获取指定对象的锁，执行指定函数<br>
	 * 会使用嵌套锁，请不要使用过深的嵌套层数。
	 * 
	 * @param objects
	 *            加锁的对象集合
	 * @param task
	 *            需要执行的任务函数
	 * @return 函数返回结果
	 */
	void execute(Collection<LockObject> objects, Runnable task);

	/**
	 * 获取指定对象的锁，执行指定函数，获取返回值<br>
	 * 会使用嵌套锁，请不要使用过深的嵌套层数。
	 * 
	 * @param objects
	 *            加锁的对象集合，不需要是有序的，加锁时会按照自然顺序加锁
	 * @param function
	 *            需要执行的函数
	 * @return 函数返回结果
	 */
	<R> R execute(Collection<LockObject> objects, Supplier<R> function);

	/**
	 * 获取指定对象的锁，执行指定函数<br>
	 * 会使用嵌套锁，请不要使用过深的嵌套层数。
	 * 
	 * @param task
	 *            需要执行的任务函数
	 * @param objects
	 *            加锁的对象数组，不需要是有序的，加锁时会按照自然顺序加锁
	 * @return 函数返回结果
	 */
	void execute(Runnable task, LockObject... objects);

	/**
	 * 获取指定对象的锁，执行指定函数，获取返回值<br>
	 * 会使用嵌套锁，请不要使用过深的嵌套层数。
	 * 
	 * @param function
	 *            需要执行的函数
	 * @param objects
	 *            加锁的对象数组，不需要是有序的，加锁时会按照自然顺序加锁
	 * @return 函数返回结果
	 */
	<R> R execute(Supplier<R> function, LockObject... objects);

}
