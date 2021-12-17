package com.tuling.apm.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tuling.apm.ApmContext;
import com.tuling.apm.collects.JdbcCommonCollects;

public class JdbcCollectTest {
	private ApmContext context;
	private MockInstrumentation instrumentation;
	private JdbcCommonCollects jdbcCollect;

	@Ignore
	@Before
	public void init() {
		instrumentation = new MockInstrumentation();
		context = new ApmContext(null, instrumentation);
		jdbcCollect = new JdbcCommonCollects(context, instrumentation);
	}

	@Ignore
	@Test
	public void sqlTest() throws Exception {
		String name = "com.mysql.jdbc.NonRegisteringDriver";
		byte[] classBytes = jdbcCollect.transform(
				ServiceCollectTest.class.getClassLoader(), name);
		ClassPool pool = new ClassPool();
		pool.insertClassPath(new ByteArrayClassPath(name, classBytes));
		pool.get(name).toClass();
		Class.forName(name);
		Connection conn = DriverManager
				.getConnection(
						"jdbc:mysql://47.104.25.237:3899/jeeshop?useUnicode=true&;characterEncoding=UTF8",
						"cbt_dev", "vwuch4cp");
		PreparedStatement statment = conn
				.prepareStatement("select * from t_area");
		statment.executeQuery();
		statment.close();
		conn.close();

	}
}
