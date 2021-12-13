package com.gupaoedu.jdbc.framework;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.core.common.Page;
import javax.core.common.utils.GenericsUtils;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class BaseDaoSupport<T extends Serializable,PK extends Serializable> {
	Logger LOG = Logger.getLogger(this.getClass());
	
	private DataSource dataSourceWrite;
//	private DataSource dataSourceReadOnly;
	
	private SimpleJdbcTemplate jdbcTemplateWrite;
	private SimpleJdbcTemplate jdbcTemplateReadOnly;
	
	private String tableName;
	private EntityOperation<T> eo;
	
	public BaseDaoSupport(){
		Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(), 0);
		eo = new EntityOperation<T>(entityClass);
		this.tableName = eo.tableName;
	}
	
	
	protected void setDataSourceWrite(DataSource dataSourceWrite) {
		this.dataSourceWrite = dataSourceWrite;
		jdbcTemplateWrite = new SimpleJdbcTemplate(dataSourceWrite);
	}

	protected void setDataSourceReadOnly(DataSource dataSourceReadOnly) {
//		this.dataSourceReadOnly = dataSourceReadOnly;
		jdbcTemplateReadOnly = new SimpleJdbcTemplate(dataSourceReadOnly);
	}
	
	protected SimpleJdbcTemplate getJdbcTemplateWrite() {
		return jdbcTemplateWrite;
	}

	protected SimpleJdbcTemplate getJdbcTemplateReadOnly() {
		return jdbcTemplateReadOnly;
	}

	/**
	 * 根据主键值，获得一个对象
	 * @param pk
	 * @return
	 */
	protected T get(PK pk) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual(this.eo.pkColum, pk);
		return selectUnique(queryRule);
	}
	
	/**
	 * 获取满足条件的记录数
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public long getCount(QueryRule queryRule) throws Exception {
		QueryRuleSqlBulider bulider = new QueryRuleSqlBulider(queryRule);
		String whereSql = bulider.getWhereSql();
		StringBuffer sql = new StringBuffer("select count(1) from " + this.getTableName());
		if(!(whereSql == null || whereSql.trim().length() == 0)){ sql.append(" where " + whereSql); }
		return this.jdbcTemplateReadOnly.queryForLong(sql.toString(), this.eo.rowMapper, bulider.getValues());
	}
	
	/**
	 * 根据查询条件获取一条唯一的记录
	 * @param queryRule
	 * @return
	 */
	protected T selectUnique(QueryRule queryRule)  throws Exception {
		List<T> r = this.select(queryRule);
		return r == null ? null : r.get(0);
	}
	
	/**
	 * 根据查询条件获得一个对象
	 * @param queryRule
	 * @return
	 */
	protected List<T> select(QueryRule queryRule){
		QueryRuleSqlBulider bulider = new QueryRuleSqlBulider(queryRule);
		String whereSql = bulider.getWhereSql();
		String orderSql = bulider.getOrderSql();
		StringBuffer sql = new StringBuffer("select " + this.eo.allColumn + " from " + this.getTableName());
		if(!(whereSql == null || whereSql.trim().length() == 0)){ sql.append(" where " + whereSql); }
		if(!(orderSql == null || orderSql.trim().length() == 0)){ sql.append(" order by " + orderSql); }
		return this.jdbcTemplateReadOnly.query(sql.toString(), this.eo.rowMapper, bulider.getValues());
	}
	
	/**
	 * 分页查询
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	protected Page<T> select(QueryRule queryRule,int pageNo,int pageSize) throws Exception{
		long count = this.getCount(queryRule);
		if (count == 0) {
			return new Page<T>();
		}
		long start = (pageNo - 1) * pageSize;
		
		// 有数据的情况下，继续查询
		QueryRuleSqlBulider bulider = new QueryRuleSqlBulider(queryRule);
		String whereSql = bulider.getWhereSql();
		String orderSql = bulider.getOrderSql();
		StringBuffer sql = new StringBuffer("select " + this.eo.allColumn + " from " + this.getTableName());
		if(!(whereSql == null || whereSql.trim().length() == 0)){ sql.append(" where " + whereSql); }
		if(!(orderSql == null || orderSql.trim().length() == 0)){ sql.append(" order by " + orderSql); }
		sql.append(" limit " + start + "," + pageSize);
		
		LOG.debug(sql);
		
		List<T> list = this.jdbcTemplateReadOnly.query(sql.toString(), this.eo.rowMapper, bulider.getValues());
		return new Page<T>(start, count, pageSize, list);
	}
	
	/**
	 * 根据sql语句查询一个结果集
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<Map<String,Object>> selectBySql(String sql,List<Object> params) throws Exception {
		return this.jdbcTemplateReadOnly.queryForList(sql, params.toArray());
	}
	
	/**
	 * 插入一条新的记录
	 * @param entity
	 * @return
	 */
	protected int insert(T entity) throws Exception {
		SqlBulider bulider = new SqlBulider();
		Map<String,Object> params = this.eo.parse(entity);
		String sql = bulider.buliderForInsert(this.getTableName(), params);
		return this.jdbcTemplateWrite.update(sql, params);
	}
	
	/**
	 * 批量插入
	 * @param entityList
	 * @return
	 */
	protected int insertAll(List<T> entityList) throws Exception {
		if(null == entityList || entityList.size() == 0){ return 0; }
		int count = 0 ,len = entityList.size(),step = 50000;
		int maxPage = (len % step == 0) ? (len / step) : (len / step + 1);
		SqlBulider bulider = new SqlBulider();
		for (int i = 1; i <= maxPage; i ++) {
			Page<T> page = pagination(entityList, i, step);
			Object[] values = new Object[this.eo.mappings.size() * page.getRows().size()];
			String sql = bulider.buliderForInsertAll(this.getTableName(), this.eo.mappings, page.getRows(),values);
			int result = this.jdbcTemplateWrite.update(sql, values);
			count += result;
		}
		return count;
	}
	
	/**
	 * 插入一条新的记录，并返回新插入记录的id
	 * @param entity
	 * @return
	 */
	protected PK insertAndReturnId(T entity) throws Exception {
		SqlBulider bulider = new SqlBulider();
		Map<String,Object> params = this.eo.parse(entity);
		final String sql = bulider.buliderForInsert(this.getTableName(), params);
		
		final List<Object> values = new ArrayList<Object>();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSourceWrite);
		  try {				 

			    jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(

						Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);


					for (int i = 0; i < values.size(); i++) {
						ps.setObject(i+1, values.get(i)==null?null:values.get(i));

					}
					return ps;
			    }

		   }, keyHolder);
		  } catch (DataAccessException e) {
			  LOG.error("error",e);
		  }


		if (keyHolder == null) { return null; }

		Map<String, Object> keys = keyHolder.getKeys();
		if (keys == null || keys.size() == 0 || keys.values().size() == 0) {
			return null;
		}
		Object key = keys.values().toArray()[0];
		if (key == null || !(key instanceof Serializable)) {
			return null;
		}
		if (key != null) {
			return (PK)this.eo.pkField.getType().cast(key);
		}else{
			return null;
		}
	}
	
	/**
	 * 更新一条记录
	 * @param entity
	 * @return
	 */
	protected int update(T entity) throws Exception {
		SqlBulider bulider = new SqlBulider();
		Map<String,Object> params = this.eo.parse(entity);
		String sql = bulider.buliderForUpdate(this.eo.tableName, this.eo.pkColum, params);
		return this.jdbcTemplateWrite.update(sql, params);
	}
	
	/**
	 * 根据主键更新一条记录，如果记录存在则覆盖，如果记录不存在则插入
	 * @param entity
	 * @return
	 */
	protected int replace(T entity) throws Exception {
		return 0;
	}
	
	/**
	 * 批量插入
	 * @param entityList
	 * @return
	 */
	protected int replaceAll(List<T> entityList) throws Exception {
		return 0;
	}
	
	/**
	 * 设置tableName
	 * @param tableName
	 */
	protected void setTableName(String tableName){
		this.tableName = null == tableName ? this.eo.tableName : tableName;
	}
	
	/**
	 * 获取tableName
	 * @return
	 */
	protected String getTableName(){
		return null == this.tableName ? this.eo.tableName : this.tableName;
	}
	
	/**
	 * 重置实体类操作对应的表名
	 */
	protected void restoreTableName(){
		this.tableName = this.eo.tableName;
	}
	
	
	/**
	 * 根据当前list进行相应的分页返回
	 * @param <T>
	 * @param objList
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	private Page<T> pagination(List<T> objList, int pageNo, int pageSize) throws Exception {
		List<T> objectArray = new ArrayList<T>(0);
		int startIndex = (pageNo - 1) * pageSize;
		int endIndex = pageNo * pageSize;
		if(endIndex >= objList.size()){
			endIndex = objList.size();
		}
		for (int i = startIndex; i < endIndex; i++) {
			objectArray.add(objList.get(i));
		}
		return new Page<T>(startIndex, objList.size(), pageSize, objectArray);
	}
}
