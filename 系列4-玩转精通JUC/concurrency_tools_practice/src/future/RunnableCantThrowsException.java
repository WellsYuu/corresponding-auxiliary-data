package future;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 描述：     在run方法中无法抛出checked Exception
 */
public class RunnableCantThrowsException {

    public void ddd() throws Exception {
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


    }
}
