package com.tuling.apm.test;

/**
 * Created by Tommy on 2018/3/8.
 */
public class ApmContextTest {


    public static void main(String[] args) {
        TestServiceImpl service=new TestServiceImpl();
        service.getUser("111","hanmei");
    }

}
