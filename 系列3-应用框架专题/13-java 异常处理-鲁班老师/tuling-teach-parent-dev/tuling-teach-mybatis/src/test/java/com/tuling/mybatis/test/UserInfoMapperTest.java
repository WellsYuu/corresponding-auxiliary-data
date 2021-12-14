package com.tuling.mybatis.test;

import com.tuling.dao.UserInfoMapper;
import com.tuling.dao.model.UserInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Tommy on 2017/10/22.
 */
public class UserInfoMapperTest {

    @Test
    public void getUserTest() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream input = getClass().getResourceAsStream("/mybatis-context-config.xml");
        Properties pro = new Properties();
        pro.setProperty("jdbc.username", "tuling");
        SqlSessionFactory devSession = builder.build(input, pro);
        UserInfoMapper mapper= devSession.openSession().getMapper(UserInfoMapper.class);
        UserInfo userinfo = mapper.selectByPrimaryKey(750737);
        Assert.assertNotNull(userinfo);
    }
}
