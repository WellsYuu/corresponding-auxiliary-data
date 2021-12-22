package com.tuling;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

public class Producer {
    public static void main(String[] args) {
        //生成Producer
        DefaultMQProducer producer = new DefaultMQProducer("wukongGroup");
        //配置Producer
        producer.setNamesrvAddr("192.168.0.12:9876;192.168.0.13:9876");
        producer.setInstanceName(UUID.randomUUID().toString());
        //启动Producer
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            return;
        }
        //生产消息
        String str = "悟空来了" + UUID.randomUUID().toString();
        Message msg = new Message("wukong", str.getBytes());
        try {
            producer.send(msg);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        //停止Producer
        producer.shutdown();
        System.out.print("[----------]Succeed\n");
    }
}