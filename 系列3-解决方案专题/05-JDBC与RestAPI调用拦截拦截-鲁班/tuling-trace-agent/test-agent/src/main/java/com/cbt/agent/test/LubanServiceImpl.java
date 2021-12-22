package com.cbt.agent.test;/**
 * Created by Administrator on 2018/6/19.
 */

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/19
 **/
public class LubanServiceImpl {
    public LubanServiceImpl() {

        System.out.println("abc");
    }

    public void hello(String arg1, String arg2) {
        String p1 = "";
        String p2 = "";
        String p3 = "";
        String p4 = p1 + p2 + p3;
    }


    public void hello$agent(String arg1, String arg2) {
        {
            long begin = System.currentTimeMillis();
            try {
                hello(arg1, arg2);
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(end - begin);
            }
        }
    }
}
