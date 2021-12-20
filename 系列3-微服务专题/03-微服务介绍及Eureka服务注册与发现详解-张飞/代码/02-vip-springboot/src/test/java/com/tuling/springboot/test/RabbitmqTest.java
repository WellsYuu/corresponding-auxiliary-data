package com.tuling.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuling.springboot.SpringbootStart;
import com.tuling.springboot.rabbitmq.Sender;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootStart.class)
public class RabbitmqTest {
    @Autowired
    private Sender sender;
    @Test
    public void testRabbitmq() throws Exception {
        sender.send();
    }
}