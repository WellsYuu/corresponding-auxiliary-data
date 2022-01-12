package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamDemo5 {

	public static void main(String[] args) {
		// 调用parallel 产生一个并行流
		// IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();

		// 现在要实现一个这样的效果: 先并行,再串行
		// 多次调用 parallel / sequential, 以最后一次调用为准.
		// IntStream.range(1, 100)
		// // 调用parallel产生并行流
		// .parallel().peek(StreamDemo5::debug)
		// // 调用sequential 产生串行流
		// .sequential().peek(StreamDemo5::debug2)
		// .count();

		// 并行流使用的线程池: ForkJoinPool.commonPool
		// 默认的线程数是 当前机器的cpu个数
		// 使用这个属性可以修改默认的线程数
		// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
		// "20");
		// IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();

		// 使用自己的线程池, 不使用默认线程池, 防止任务被阻塞
		// 线程名字 : ForkJoinPool-1
		ForkJoinPool pool = new ForkJoinPool(20);
		pool.submit(() -> IntStream.range(1, 100).parallel()
				.peek(StreamDemo5::debug).count());
		pool.shutdown();
		
		synchronized (pool) {
			try {
				pool.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void debug(int i) {
		System.out.println(Thread.currentThread().getName() + " debug " + i);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void debug2(int i) {
		System.err.println("debug2 " + i);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
