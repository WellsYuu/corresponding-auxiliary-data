package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamDemo5 {

	public static void main(String[] args) {
		// ����parallel ����һ��������
		// IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();

		// ����Ҫʵ��һ��������Ч��: �Ȳ���,�ٴ���
		// ��ε��� parallel / sequential, �����һ�ε���Ϊ׼.
		// IntStream.range(1, 100)
		// // ����parallel����������
		// .parallel().peek(StreamDemo5::debug)
		// // ����sequential ����������
		// .sequential().peek(StreamDemo5::debug2)
		// .count();

		// ������ʹ�õ��̳߳�: ForkJoinPool.commonPool
		// Ĭ�ϵ��߳����� ��ǰ������cpu����
		// ʹ��������Կ����޸�Ĭ�ϵ��߳���
		// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
		// "20");
		// IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();

		// ʹ���Լ����̳߳�, ��ʹ��Ĭ���̳߳�, ��ֹ��������
		// �߳����� : ForkJoinPool-1
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
