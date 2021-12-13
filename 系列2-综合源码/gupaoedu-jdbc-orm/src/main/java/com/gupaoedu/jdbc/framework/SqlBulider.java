package com.gupaoedu.jdbc.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SQL语句构造器
 * @author tanyongde
 *
 */
public class SqlBulider {
	
	/**
	 * 构建单条插入的SQL
	 * @param tableName
	 * @param params
	 * @return
	 */
	public String buliderForInsert(String tableName,Map<String,Object> params){
		if(null == tableName || tableName.trim().length() == 0 || params == null || params.isEmpty()){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(tableName);
		
		StringBuffer sbKey = new StringBuffer();
		StringBuffer sbValue = new StringBuffer();
		
		sbKey.append("(");
		sbValue.append("(");
		//添加参数
		Set<String> set = params.keySet();
		int index = 0;
		for (String key : set) {
			sbKey.append(key);
			sbValue.append(" :").append(key);
			if(index != set.size() - 1){
				sbKey.append(",");
				sbValue.append(",");
			}
			index++;
		}
		sbKey.append(")");
		sbValue.append(")");
		
		sb.append(sbKey).append("VALUES").append(sbValue);
		
		return sb.toString();
	}
	
	/**
	 * 构造批量插入的SQL
	 * @param tableName
	 * @param fm
	 * @param params
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public String buliderForInsertAll(String tableName,Map<String, FieldMapping> fm,List<?> params,Object [] values) throws Exception{
		if(null == tableName || tableName.trim().length() == 0 || params == null || params.isEmpty()){
			return "";
		}
		List<String> allColumn = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(tableName);
		StringBuffer valstr = new StringBuffer();
		for (int j = 0; j < params.size(); j ++) {
			if(j > 0 && j < params.size()){ valstr.append(","); }
			valstr.append("(");
			int k = 0;
			for (FieldMapping p : fm.values()) {
				if(!allColumn.contains(p.columnName)){ allColumn.add(p.columnName); }
				values[(j * fm.size()) + k] = p.getter.invoke(params.get(j));
				if(k > 0 && k < fm.size()){ valstr.append(","); }
				valstr.append("?");
				k ++;
			}
			valstr.append(")");
		}
		sb.append("(" + allColumn.toString().replaceAll("\\[|\\]", "") + ") values ").append(valstr.toString());
		return sb.toString();
	}
	
	
	/**
	 * 构造修改的SQL语句
	 * @param tableName
	 * @param pkName
	 * @param params
	 * @return
	 */
	public String buliderForUpdate(String tableName,String pkName,Map<String,Object> params){
		if(null == tableName || tableName.trim().length() == 0 || params == null || params.isEmpty()){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("update ").append(tableName).append(" set ");
		//添加参数
		Set<String> set = params.keySet();
		int index = 0;
		for (String key : set) {
			 sb.append(key).append(" = :").append(key);
			 if(index != set.size() - 1){
				 sb.append(",");
			 }
			 index++;
		}
		sb.append(" where ").append(pkName).append(" = :").append(pkName) ;
		
		return sb.toString();
	}
	
	
}
