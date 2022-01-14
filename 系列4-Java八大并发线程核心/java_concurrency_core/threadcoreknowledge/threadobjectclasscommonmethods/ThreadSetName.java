package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 描述：     修改线程名字
 */
public class ThreadSetName {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.setName("11");
        System.out.println(thread.getName());
    }
}
