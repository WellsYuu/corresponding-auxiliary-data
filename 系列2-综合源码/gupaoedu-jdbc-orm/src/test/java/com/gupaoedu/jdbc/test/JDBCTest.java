package com.gupaoedu.jdbc.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.gupaoedu.jdbc.demo.entity.Member;


public class JDBCTest {
	
	public static void main(String[] args) {
		
		try{
			
			Class.forName("");
			Connection conn = DriverManager.getConnection("");
			
			//想尽各种奇招，解析成一个SQL语句，交给JDBC执行
			//HQL   避开数据库方言
			//请说普通话
			PreparedStatement pstm = conn.prepareStatement("select * from t_member");
			
			ResultSet rs = pstm.executeQuery();
			
			//游标自动往下移动
			while(rs.next()){
				//死代码，写死了
				rs.getString("列名");
				rs.getInt("");
				
				//用反射实现这个ORM的封装过程
				
			}
			
			
			//ORM框架
			//获取到RS的元数据
			//列名，列的字段类型，列的索引
			ResultSetMetaData rsmd =  rs.getMetaData();
			for(int i = 0; i < rsmd.getColumnCount(); i ++){
				String columnName = rsmd.getColumnName(i);
				int type = rsmd.getColumnType(i);
				
				//跟对象进行一个关联了
				//列名对应对象的属性名
				//type 对应属性的类型
				//Mapper
				
				//利用反射机制做一个动态映射
				
			}
			
			
			//反之，利用反射机制可以获取到对象的属性名称以及属性的类型
			Class clazz = Member.class;
			Field[] fs = clazz.getFields();//动态提取到实体类的所有的字段
			
			for (int i = 0; i < fs.length; i++) {
				fs[i].getType().getName();//获取属性所对应的类型
				//MySQL举例
				//int     int
				//long    bigint
				//String   varchar
				//boolean  tinyint
				
				
				//Mapper
			}
			
		}catch(Exception e){
			
		}
		
	}
	
}
