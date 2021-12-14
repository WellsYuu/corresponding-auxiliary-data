package com.tuling.mybatis.test;

import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * Created by Tommy on 2017/10/19.
 */
public class MyBatisTest {

    public void proTest() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream input = getClass().getResourceAsStream("/mybatis-context-config.xml");
        Properties pro = new Properties();
        pro.setProperty("jdbc.username", "tuling");
        SqlSessionFactory devSession = builder.build(input, pro);
        SqlSessionFactory testsqlSession = builder.build(input, "test", pro);
        devSession.openSession().selectList("");

        // spring propertites
    }


    /**
     * 1、新建一个会话工厂构建器 2、 基于配置文件转换成 Configuration 3、获取一个会话 （request 请求 ） 4、执行查询语句
     */
    @Test
    public void queryTest() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream input = getClass().getResourceAsStream("/mybatis-config2.xml");
        Properties pro = new Properties();
        pro.setProperty("jdbc.username", "tuling");
        ;
        SqlSessionFactory factory = builder.build(input, pro);
        factory.getConfiguration();
        SqlSession session = factory.openSession();
        Object object = session.selectOne("com.tuling.mybatis.test.selectUser", 750732);
        System.out.println(object instanceof UuserInfo);

    }


    @Test
    public void parseConfiguration() {
        Configuration config = new Configuration();
        InputStream inputStream = getClass().getResourceAsStream("/userInfo.xml");
        XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, config, "/userInfo.xml",
                config.getSqlFragments());
        builder.parse();
        config.getMappedStatement("com.tuling.mybatis.test.selectUser");

    }


}
