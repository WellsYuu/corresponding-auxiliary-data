package com.gupao.vip.mic.dubbo.jms;

import org.apache.activemq.broker.BrokerService;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DefineBrokerServer {

    public static void main(String[] args) {
        BrokerService brokerService=new BrokerService();
        try {
            brokerService.setUseJmx(true);
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
