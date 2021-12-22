package com.tl;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */

import com.tl.entity.Order;
import com.tl.entity.OrderItem;
import com.tl.repository.OrderItemRepository;
import com.tl.repository.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/mybatisShardingDatabaseAndTableContext.xml"})
public class Spring格式的分库分表 {


    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

   @Before
    public void init(){

        //创建表
        orderRepository.createIfNotExistsTable();
        orderItemRepository.createIfNotExistsTable();
        //清空表
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
    }
    @Test
    public void demo() {
        System.out.println("1.Insert------插入--------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(51);
            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);
            long orderId = order.getOrderId();

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(51);
            item.setStatus("INSERT_TEST");
            orderItemRepository.insert(item);

        }

    }
    @After
    public void select(){
        List<OrderItem> list= orderItemRepository.selectAll();
        for (OrderItem item:list){
            System.out.println(item);
        }

    }
}
