package threadpool;

import java.util.concurrent.Executors;

/**
 * 描述：     TODO
 */
public class ForLoop {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Task());
            thread.start();
        }
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }

}
