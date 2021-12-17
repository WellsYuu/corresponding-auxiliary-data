package com.tuling.javaagent;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * Created by Tommy on 2018/3/6.
 */
public class C3p0Demo {
    ComboPooledDataSource dataSource;
    public C3p0Demo(){
        dataSource = new ComboPooledDataSource("mysql");

    }

    public void exec(String sql) throws SQLException {
        Connection conn = dataSource.getConnection();
        boolean b = conn.createStatement().execute(sql);
        conn.close();
    }

    public static void main(String[] args) throws IOException {
        C3p0Demo s=new C3p0Demo();
        while (true) {
            byte[] bytes = new byte[1024];
            int size = System.in.read(bytes);
            String sql = new String(bytes, 0, size);
            System.out.println(sql);
            try {
                s.exec(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
