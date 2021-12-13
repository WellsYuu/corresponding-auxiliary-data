package com.gupaoedu.jdbc.framework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

class EntityOperation<T> {
	private Logger LOG = Logger.getLogger(this.getClass());
	
	//实体类对应的class字节码
	public Class<T> entityClass;
	//属性字段关系映射
	public final Map<String, FieldMapping> mappings;
	//结果映射
	public final RowMapper<T> rowMapper;
	
	//实体类对应的表名
	public final String tableName; 
	//所有列名
	public String allColumn = "*";
	//主键字段
	public Field pkField;
	//主键列名
	public String pkColum;
	
	/**
	 * 构建一个对实体类操作的反射工具类，每个实体类都对一个工具类实例
	 * @param clazz
	 * @param pk
	 */
	public EntityOperation(Class<T> clazz){
		this.entityClass = clazz;
		Table table = entityClass.getAnnotation(Table.class);
	    if (table != null) {
	       this.tableName = table.name();
	    } else {
	    		this.tableName =  entityClass.getSimpleName();
	    }
	    //只扫描写个了get和set方法的属性
	    Map<String, Method> getters = EntityClassUtils.findPublicGetters(entityClass);
	    Map<String, Method> setters = EntityClassUtils.findPublicSetters(entityClass);
	    //将get和set对应的属性找出来
	    Field[] fields = EntityClassUtils.findFields(entityClass);
	    //将属性名数据库表中的字段名一一对应上
	    this.mappings = getFieldMappings(getters, setters, fields);
	    //将主键字段保存下来，方便之后的删除和修改操作
	    FieldMapping pk = getPkFromMapping(mappings);
	    if(pk != null){
		    	this.pkField = pk.field;
		    	this.pkColum = pk.columnName;
	    }else{
	    		LOG.debug("未找到主键");
	    }
	    //将主键字段找出来
	    this.allColumn = this.mappings.keySet().toString().replace("[", "").replace("]","").replaceAll(" ","");
	    //创建一个结果集映射关系
	    this.rowMapper = createRowMapper();
	}
	
	/**
	 * 将resultSet中的字段名和实体类中的字段名一一对应
	 * @return
	 */
	RowMapper<T> createRowMapper() {
        return new RowMapper<T>() {
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                		//先创建实例
                    T t = entityClass.newInstance();
                    //迭代结果集中所有的列名
                    ResultSetMetaData meta = rs.getMetaData();
                    int columns = meta.getColumnCount();
                    String columnName;
                    //将列名和字段名关系对应上
                    for (int i = 1; i <= columns; i++) {
                        Object value = rs.getObject(i);
                        columnName = meta.getColumnName(i);
                        putFieldValue(t,columnName,value);
                    }
                    return t;
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
	
	/**
	 * 往字段中设值
	 * @param t
	 * @param columnName
	 * @param value
	 */
	protected void putFieldValue(T t, String columnName, Object value) {
		 if (value != null) {
            FieldMapping pm = this.mappings.get(columnName.toUpperCase());
            if (pm != null) {
                try {
					pm.set(t, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	/**
	 * 从映射关系中找出主键
	 * @param mappings
	 * @return
	 */
	private FieldMapping getPkFromMapping(Map<String, FieldMapping> mappings){
		for(Entry<String, FieldMapping> entry : mappings.entrySet()){
			//如果为主键字段
	        if(entry.getValue().id){
	        		return entry.getValue();
	        }
		}
		return null;
	}
	
	/**
	 * 获取属性和字段的映射关系
	 * @param getters
	 * @param setters
	 * @param fields
	 * @return
	 */
	private Map<String, FieldMapping> getFieldMappings(Map<String, Method> getters, Map<String, Method> setters, Field[] fields) {
		if(getters.size() == 0){ LOG.error("没有扫描到任何getter"); }
		if(setters.size() == 0){ LOG.error("没有扫描到任何setters"); }
		Map<String, FieldMapping> mappings = new HashMap<String, FieldMapping>();
        String name;
        for (Field field : fields) {
        		//添加了@Transient注解忽略
            if (field.isAnnotationPresent(Transient.class))  continue;
            name = field.getName();
            Method setter = setters.get(name);
            Method getter = getters.get(name);
            //没有设置getter和setter则忽略
            if (setter == null || getter == null){ continue;  }
            FieldMapping mapping = new FieldMapping(getter, setter, field);
            mappings.put(mapping.columnName.toUpperCase(), mapping);
        }
        if(mappings.size() == 0){
        		LOG.error("没有扫描到任何映射关系");
        }
        return mappings;
    }
	
	/**
	 * 将结果集转换为实体类
	 * @param rs
	 * @return
	 */
	public T parse(ResultSet rs) {
		T t = null;
		if (null == rs) {
			return null;
		}
		Object value = null;
		try {
			t = (T) entityClass.newInstance();
			for (String columnName : mappings.keySet()) {
				try {
					value = rs.getObject(columnName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				putFieldValue(t,columnName,value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return t;
	}

	/**
	 * 将实体类转换为Map对象
	 * @param t
	 * @return
	 */
	public Map<String, Object> parse(T t) {
		Map<String, Object> _map = new HashMap<String, Object>();
		try {
			
			for (String columnName : mappings.keySet()) {
				Object value = mappings.get(columnName).getter.invoke(t);
				if (value == null)
					continue;
				_map.put(columnName, value);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _map;
	}

	public void println(T t) {
		try {
			for (String columnName : mappings.keySet()) {
				Object value = mappings.get(columnName).getter.invoke(t);
				if (value == null)
					continue;
				System.out.println(columnName + " = " + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

/**
 * 
 * 定义为属性和字段的映射关系
 * @author tom
 *
 */
class FieldMapping {
	private Logger LOG = Logger.getLogger(this.getClass());
    final boolean insertable;	//是否可插入
    final boolean updatable;		//是否可修改
    final String columnName;		//对应的列名
    final boolean id;			//对应的ID
    final Method getter;			//对应的getter方法
    final Method setter;			//对应的setter方法
	final Class enumClass;		//如果是枚举类，将枚举的字节码保存下来
    final String fieldName;		//对应的字段名称
    final Field field;			//对应的反射字段

    public FieldMapping(Method getter, Method setter, Field field) {
        this.getter = getter;
        this.setter = setter;
        //如果是枚举类型，则将其标记下来
        this.enumClass = getter.getReturnType().isEnum() ? getter.getReturnType() : null;
        Column column = field.getAnnotation(Column.class);
        //可插入
        this.insertable = column == null || column.insertable();
        //是否可修改
        this.updatable = column == null || column.updatable();
       //如果添加了@Column注解，则使用用户自定义列名，否则，默认为属性名
        this.columnName = column == null ? field.getName() : ("".equals(column.name()) ? field.getName() : column.name());
        //如果添加了@Id
        this.id = field.isAnnotationPresent(Id.class);
        //字段名称
        this.fieldName = field.getName();
        //保存字段
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    Object get(Object target) throws Exception {
        Object r = getter.invoke(target);
        return enumClass == null ? r : Enum.valueOf(enumClass, (String) r);
    }

    @SuppressWarnings("unchecked")
    void set(Object target, Object value) throws Exception {
        if (enumClass != null && value != null) {
            value = Enum.valueOf(enumClass, (String) value);
        }
        //BeanUtils.setProperty(target, fieldName, value);
        try {
        	 if(value != null){
             	 setter.invoke(target, setter.getParameterTypes()[0].cast(value));
             }
		} catch (Exception e) {
			e.printStackTrace();
			/**
			 * 出错原因如果是boolean字段 mysql字段类型 设置tinyint(1)
			 */
			LOG.error(fieldName + "--" + value);
		}
      
    }
}
