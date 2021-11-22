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
public class UserDaoImpl implements UserDao{

    @Autowired
    JdbcTemplate userJdbcTemplate;

    public void updateUser() {
        userJdbcTemplate.execute
                ("update user set name='mic',mobile='1378088888'," +
                        "sex='male' where id=4)");
    }
}
