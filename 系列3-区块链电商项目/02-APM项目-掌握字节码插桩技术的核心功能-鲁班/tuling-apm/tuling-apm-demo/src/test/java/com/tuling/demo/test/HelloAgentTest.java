package com.tuling.demo.test;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Tommy on 2018/3/6.
 */
public class HelloAgentTest {
    @Ignore
    @Test
    public void helloTest(){
        System.out.println("hello word!");
        new UserServiceImpl().getUser();
    }
}
