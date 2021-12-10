package com.gupaoedu.vip.jdbc.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.jdbc.demo.entity.Member;

public class JdbcTest {

	
	public static void main(String[] args) {
		
		//原生的JDBC如何操作？
		try {
			
			
			//被封装成了DataSource，放入到了连接池
			//目的是为了提高程序响应速度
			//1、加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			//2、建立连接
			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.105:3306/gupaoedu_db","root","123456");
		
			
			
			//3、创建语句集
			PreparedStatement pstm = con.prepareStatement("select * from t_member");
			//4、执行
			ResultSet rs = pstm.executeQuery();
			
			
			
			//封装了，做成一个ORM的过程
			//Object Relation Mapping 对象关系映射
			//自动变成一个我们显而易见的普通的自己写的Java对象（实体类）
			
			
			int len = rs.getMetaData().getColumnCount();
			//5、获取结果集
			List<Object> result = new ArrayList<Object>();
			while(rs.next()){
				
				Class clazz = Member.class;
				Object obj = clazz.newInstance();
				for (int i = 1 ; i <= len; i ++) {
					String columnName = rs.getMetaData().getColumnName(i);
					Field field = clazz.getDeclaredField(columnName);
					
					//前提条件是属性名和字段名一致（约定）
					field.setAccessible(true);
					Object type = field.getType();
					if(type == Long.class){
						field.set(obj, rs.getLong(columnName));
					}else if(type == String.class){
						field.set(obj, rs.getString(columnName));
					}
				}
				
				result.add(obj);
			}
			System.out.println(JSON.toJSON(result));
			rs.close();
			pstm.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
