package com.tuling.test.connectionPool;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;

/**
 * Created by Tommy on 2017/10/25.
 */
public class DruidTest {

    private ClassPathXmlApplicationContext context;

    @Before
    public void init() {
         context=new ClassPathXmlApplicationContext("spring.xml");
        context.start();

    }

    @Test
    public void addTest() {
        JdbcTemplate template = context.getBean(JdbcTemplate.class);
        template.queryForList("SELECT * FROM user_info");
    }
}
