package com.enjoylearning.mybatis.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.enjoylearning.mybatis.entity.TUser;

public class ReflectionUtil {
	
	public static void setPropToBean(Object bean,String propName,Object value){
		Field f;
		try {
			f = bean.getClass().getDeclaredField(propName);
	        f.setAccessible(true);
	        f.set(bean, value);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) {
		TUser user = new TUser();
		ReflectionUtil.setPropToBean(user, "com.enjoylearning.mybatis.entity.TUser.userName", "lison");
		System.out.println(user);
		
		Field[] declaredFields = user.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			System.out.println(field.getName());
			System.out.println(field.getType().getSimpleName());
		}
		
		
	}
	

}
