package com.tuling.teach.connectionPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/10/25.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<UserInfo> getUserById(Integer id) {

        return template.query("SELECT * FROM user_info",   new BeanPropertyRowMapper(UserInfo.class));
    }
}
