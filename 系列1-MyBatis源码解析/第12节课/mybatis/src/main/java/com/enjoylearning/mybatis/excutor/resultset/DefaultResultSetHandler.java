package com.enjoylearning.mybatis.excutor.resultset;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoylearning.mybatis.config.MappedStatement;
import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.reflection.ReflectionUtil;

public class DefaultResultSetHandler implements ResultSetHandler {

	private MappedStatement mappedStament;
	
	
	
	
	public DefaultResultSetHandler(MappedStatement mappedStament) {
		super();
		this.mappedStament = mappedStament;
	}




	@Override
	public <E> List<E> handleResultSets(ResultSet resultSet)
			throws SQLException {
		List<E> ret = new ArrayList<E>();
		while (resultSet.next()) {
			Class<?> entityClass;
			try {
				entityClass = Class.forName(mappedStament.getResultType());
				E entity = (E) entityClass.newInstance();
				Field[] declaredFields = entityClass.getDeclaredFields();
				for (int i = 0; i < declaredFields.length; i++) {
					if(declaredFields[i].getType().getSimpleName().equals("String")){
//						ReflectionUtil.setPropToBean(entity, declaredFields[0].getName(), resultSet.getString(resultSet.findColumn(declaredFields[0].getName())));
						ReflectionUtil.setPropToBean(entity, declaredFields[i].getName(), resultSet.getString(declaredFields[i].getName()));
					}else if(declaredFields[i].getType().getSimpleName().equals("Integer")){
						ReflectionUtil.setPropToBean(entity, declaredFields[i].getName(), resultSet.getInt(declaredFields[i].getName()));
						
					}
				}
				ret.add(entity);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

}
