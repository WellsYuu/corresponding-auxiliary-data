package com.gupao.vip.mic.dubbo.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Repository
public class OrderDaoImpl implements OrderDao{
    @Autowired
    JdbcTemplate orderJdbcTemplate;

    @Override
    public void insertOrder() {
        orderJdbcTemplate.execute("insert order(status,price,order_time) values(1,10,now())");
    }
}
