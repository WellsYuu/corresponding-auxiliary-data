package ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolManager implements ThreadPool {

	// 线程池中默认线程的个数为5
	private static int workerNum = 5;
	// 工作线程数组
	WorkThread[] workThrads;
	// 在执行的任务数量 volatile
	private static volatile int executeTaskNumber = 0;
	// 任务队列，作为一个缓冲,List线程不安全
	private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
	private static ThreadPoolManager threadPool;
	private AtomicLong threadNum = new AtomicLong();

	private ThreadPoolManager() {
		this(workerNum);
	}

	private ThreadPoolManager(int workerNum2) {
		if (workerNum2 > 0) {
			workerNum = workerNum2;
		}
		// 工作线程池初始化 开辟了一个连续的空间
		workThrads = new WorkThread[workerNum];

		for (int i = 0; i < workerNum; i++) {
			// 每个空间的对象初始化
			workThrads[i] = new WorkThread();
			workThrads[i].setName("ThreadPool-worker" + threadNum.incrementAndGet());
			System.out.println("初始化线数: " + (i + 1)+"/"+(workerNum) + "  ----当前线程名称是：" + workThrads[i].getName());
			workThrads[i].start();
		}
	}

	@Override
	public void execute(Runnable task) {
		synchronized (taskQueue) {
			try {
				taskQueue.put(task);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 taskQueue.notifyAll();
			// taskQueue.notify();
		}

	}

	@Override
	public void execute(Runnable[] tasks) {
		synchronized (taskQueue) {
			for (Runnable task : tasks)
				try {
					taskQueue.put(task);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			taskQueue.notifyAll();
			// taskQueue.notify();
		}

	}

	@Override
	public void execute(List<Runnable> tasks) {
		synchronized (taskQueue) {
			for (Runnable task : tasks)
				try {
					taskQueue.put(task);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			taskQueue.notifyAll();
			// taskQueue.notify();
		}
	}

	// jdk 提供两种方式获取线程池
	public static ThreadPool getThreadPool() {
		return getThreadPool(ThreadPoolManager.workerNum);
	}

	public static ThreadPool getThreadPool(int workerNum) {
		if (workerNum <= 0) {
			workerNum = ThreadPoolManager.workerNum;
		}
		if (threadPool == null) {
			threadPool = new ThreadPoolManager(workerNum);
		}
		return threadPool;
	}

	@Override
	public int getExecuteTaskNumber() {
		return executeTaskNumber;
	}

	@Override
	public int getWaitTaskNumber() {
		return taskQueue.size();
	}

	@Override
	public int getWorkThreadNumber() {
		return workerNum;
	}

	// 关键线程池的销毁问题
	@Override
	public void destroy() {
		while (!taskQueue.isEmpty()) {
			try {
				// 不断的检测任务队列是否全部执行
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		for (int i = 0; i < workerNum; i++) {
			workThrads[i].stopWorker();
			workThrads[i] = null;
		}
		threadPool = null;
		taskQueue.clear();
	}

	@Override
	public String toString() {
		return "当前工作线程数量为:" + workerNum + "  已经完成任务数:" + executeTaskNumber + "  等待任务数:" + getWaitTaskNumber();
	}

	/**
	 * 内部类，工作线程
	 */
	private class WorkThread extends Thread {
		// 该工作线程是否有效，用于结束该工作线程
		private boolean isRunning = true;

		/*
		 * 关键所在啊，如果任务队列不空，则取出任务执行，若任务队列空，则等待
		 */
		@Override
		public void run() {
			// 接收队列当中的任务对象 任务对象Runnable类型
			Runnable r = null;
			while (isRunning) {
				// 队列同步机制synchronized
				synchronized (taskQueue) {
					while (isRunning && taskQueue.isEmpty()) {// 队列为空
						try {
							taskQueue.wait(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!taskQueue.isEmpty())
						try {
							// 取出任务
							r = taskQueue.take();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

				}
				if (r != null) {
					((java.lang.Runnable) r).run();// 执行任务
				}
				executeTaskNumber++;
				r = null;
			}
		}

		// 停止工作，让该线程自然执行完run方法，自然结束
		public void stopWorker() {
			isRunning = false;
		}
	}
}
