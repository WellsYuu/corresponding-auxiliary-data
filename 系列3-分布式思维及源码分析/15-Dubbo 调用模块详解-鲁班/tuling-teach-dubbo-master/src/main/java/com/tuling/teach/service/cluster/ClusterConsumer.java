package com.tuling.teach.service.cluster;

import com.tuling.teach.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClusterConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "cluster-consumer.xml", ClusterConsumer.class);
        context.start();
        ClusterDemoService demoService = (ClusterDemoService) context.getBean("clusterDemoService"); // obtain proxy object for remote invocation

        /*failover
        failfast
        failsafe
        failback
        forking
        broadcast*/
        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            String cmd = new String(b, 0, szie).trim();
            try {
                if (cmd.equals("failover")) {
                    System.out.println(demoService.failover("失败切换"));
                } else if (cmd.equals("failfast")) {
                    System.out.println(demoService.failfast("快速失败"));
                } else if (cmd.equals("failsafe")) {
                    System.out.println(demoService.failsafe("失败安全"));
                } else if (cmd.equals("failback")) {
                    System.out.println(demoService.failback("失败重试"));
                } else if (cmd.equals("forking")) {
                    System.out.println(demoService.forking("并行调用"));
                } else if (cmd.equals("broadcast")) {
                    System.out.println(demoService.broadcast("广播调用"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}