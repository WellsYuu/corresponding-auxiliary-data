package com.tuling.apm.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Tommy on 2018/3/8.
 */
public class ApmContextTest {


    public static void main(String[] args) throws Exception {
        ApmContextTest test = new ApmContextTest();
        test.serviceTest();
        test.sqlTest();
    }

    public void serviceTest() {
        TestServiceImpl service = new TestServiceImpl();
        service.getUser("111", "hanmei");
    }

    public void sqlTest() throws Exception {
        String name = "com.mysql.jdbc.Driver";
        Class.forName(name);
        Connection conn = DriverManager
                .getConnection(
                        "jdbc:mysql://192.168.0.147:3306/tuling?useUnicode=true&amp;characterEncoding=UTF8 ",
                        "root", "123456");
        PreparedStatement statment = conn
                .prepareStatement("select * from myTable");
        statment.executeQuery();
        statment.close();
        conn.close();
    }

}
