import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写3个线程，按顺序分别输出ABC
 * 2017/08/31
 * @author Tom
 */
public class PrintABCForThread {

		private static Lock lock = new ReentrantLock();	//通过JDK5中的Lock锁来保证线程的访问的互斥
		private static int state = 0;	//记录线程执行顺序
		private static final int COUNT = 3;	//总线程数
		private static final String[] values = new String[]{"A","B","C"};	//按顺序输出ABC的值
       
        static class PrintA extends Thread {
            @Override
            public void run() {
                try {
                	int index = 0;	//输出顺序
                    lock.lock();
                    while (state % COUNT == index) {	//多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.println(Thread.currentThread().getName() + ":" + values[index]);
                        state ++;
                    }
                } finally {
                    lock.unlock();	//lock()和unlock()操作结合try/catch使用
                }
            }
        }
       
        static class PrintB extends Thread {
            @Override
            public void run() {
                try {
                	int index = 1;	//输出顺序
                    lock.lock();
                    while (state % COUNT == index) {	//多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.println(Thread.currentThread().getName() + ":" + values[index]);
                        state ++;
                    }
                } finally {
                    lock.unlock();	//lock()和unlock()操作结合try/catch使用
                }
            }
        }
        
        static class PrintC extends Thread {
            @Override
            public void run() {
                try {
                	int index = 2;	//输出顺序
                    lock.lock();
                    while (state % COUNT == index) {	//多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                        System.out.println(Thread.currentThread().getName() + ":" + values[index]);
                        state ++;
                    }
                } finally {
                    lock.unlock();	//lock()和unlock()操作结合try/catch使用
                }
            }
        }
       
        public static void main(String[] args) {
            new PrintA().start();
            new PrintB().start();
            new PrintC().start();
        }
}
