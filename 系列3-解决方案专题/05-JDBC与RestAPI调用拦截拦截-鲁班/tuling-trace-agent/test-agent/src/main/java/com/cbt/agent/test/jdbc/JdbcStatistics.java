package com.cbt.agent.test.jdbc;

import java.util.ArrayList;

public class JdbcStatistics extends BaseStatistics {
	public Long begin;// 时间戳
	public Long end;
	public Long useTime;
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

	@Override
	public String toString() {
		return "JdbcStatistics{" +
				"begin=" + begin +
				", end=" + end +
				", useTime=" + useTime +
				", jdbcUrl='" + jdbcUrl + '\'' +
				", sql='" + sql + '\'' +
				", databaseName='" + databaseName + '\'' +
				", error='" + error + '\'' +
				", errorType='" + errorType + '\'' +
				", preman='" + preman + '\'' +
				", params=" + params +
				'}';
	}
}