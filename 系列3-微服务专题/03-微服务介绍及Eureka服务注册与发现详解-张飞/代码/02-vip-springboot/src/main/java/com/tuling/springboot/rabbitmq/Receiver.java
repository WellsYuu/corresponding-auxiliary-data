package com.tuling.springboot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "first")
public class Receiver {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("receive msg : " + msg);
    }
}