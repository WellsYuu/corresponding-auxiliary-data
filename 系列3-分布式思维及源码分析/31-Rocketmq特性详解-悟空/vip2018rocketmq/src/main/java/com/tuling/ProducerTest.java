package com.tuling;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

public class ProducerTest {
    private static DefaultMQProducer producer = null;

    public static void main(String[] args) {
        System.out.print("[----------]Start\n");
        int pro_count = 1;
        if (args.length > 0) {
            pro_count = Integer.parseInt(args[0]);
        }
        boolean result = false;
        try {
            ProducerStart();
            for (int i = 0; i < pro_count; i++) {
                String msg = "hello rocketmq!----" + UUID.randomUUID().toString();
                SendMessage("qch_20170706", msg);
                System.out.print(msg + "\n");
            }
        }finally {
            producer.shutdown();
        }
        System.out.print("[----------]Succeed\n");
    }

    private static boolean ProducerStart() {
        producer = new DefaultMQProducer("pro_qch_test");
        producer.setNamesrvAddr("192.168.6.3:9876;192.168.6.4:9876");
        producer.setInstanceName(UUID.randomUUID().toString());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean SendMessage(String topic, String str) {
        Message msg = new Message(topic, str.getBytes());
        try {
            producer.send(msg);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}