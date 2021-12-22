package com.tuling;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.UUID;

public class PushConsumerTest {
    private static int count = 0;

    public static void main(String[] args) {
        System.out.print("Push Consumer main start!\n");
        count = 0;
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("con_group_1");
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setConsumeMessageBatchMaxSize(32);
        consumer.setNamesrvAddr("192.168.6.3:9876;192.168.6.4:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.print("list count=" + list.size() + "\n");
                for(MessageExt me : list) {
                    count ++;
                    System.out.print("count=" + count + ", msg=" + new String(me.getBody()) + "\n");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.subscribe("qch_20170706", "*");
            consumer.start();
            System.out.print("Push Consumer Started!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
