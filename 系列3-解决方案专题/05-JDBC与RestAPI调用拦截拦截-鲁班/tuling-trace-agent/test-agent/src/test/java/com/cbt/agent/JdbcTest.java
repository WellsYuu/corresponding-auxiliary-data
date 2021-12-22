package com.cbt.agent;


/**
 * Created by Administrator on 2018/6/24.
 */

import com.cbt.agent.test.jdbc.JdbcCommonCollects;
import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/24
 **/
public class JdbcTest {
    private MockInstrumentation instrumentation;
    private JdbcCommonCollects jdbcCollect;

    @Ignore
    @Before
    public void init() {
        instrumentation = new MockInstrumentation();
        Properties pro = new Properties();
        jdbcCollect = new JdbcCommonCollects(instrumentation);
    }


    // mysql 驱动插桩
    @Ignore
    @Test
    public void mysqlDriverCut() throws Exception {
        String name = "com.mysql.jdbc.NonRegisteringDriver";
        byte[] classBytes = jdbcCollect.transform(
                JdbcTest.class.getClassLoader(), name);
        ClassPool pool = new ClassPool();
        pool.insertClassPath(new ByteArrayClassPath(name, classBytes));
        pool.get(name).toClass();

        pool.get(name).writeFile(System.getProperty("user.dir")+"\\target");
        Class.forName(name);
    }

    // mysql 测试
    @Test
    public void jdbcTest() throws Exception {
//        mysqlDriverCut();
        Connection conn = DriverManager
                .getConnection(
                        "jdbc:mysql://192.168.0.15:3306/tlshop?useUnicode=true&;characterEncoding=UTF8",
                        "root", "123456");
        PreparedStatement statment = conn
                .prepareStatement("select * from t_lable ");
        ResultSet r = statment.executeQuery();
        while (r.next()) {
            System.out.println(r.getString(1) + "     " + r.getString(2));
        }
        statment.close();
        conn.close();
    }
}
