package com.tuling.apm.model;

import java.util.ArrayList;

public class JdbcStatistics extends BaseStatistics {
	public Long begin;
	public Long end;
	public Long usrTime;
	// jdbc url
	public String jdbcUrl;
	// sql 语句
	public String sql;
	// 数据库名称
	public String databaseName;
	
	public String error;
	public String errorType;
	// 是否经过预处理
	public String preman;

	public ArrayList<ParamValues> params=new ArrayList();


	public JdbcStatistics() {

	}

	public static class ParamValues{
		public ParamValues(int index, Object value) {
			this.index = index;
			this.value = value;
		}

		public int index;
		public Object value;
	}
}