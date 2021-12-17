package com.tl;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-members.xml"});
        context.start();
        // obtain proxy object for remote invocation
        IOrderService demoService = (IOrderService) context.getBean("orderService");
        // execute remote invocation
        Orders orders= demoService.queryOrders(2018);
        // show the result
        System.out.println(orders);
    }
}