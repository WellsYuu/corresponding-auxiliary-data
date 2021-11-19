package com.gupao.framework.service;

import com.gupao.framework.common.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 顶级 Service
 * </p>
 * 
 * @author qingyin
 * @Date 2016-08-20
 */
public interface IService<T, I> {

	/**
	 * <p>
	 * 插入一条记录
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return boolean
	 */
	boolean insert(T entity);

	/**
	 * <p>
	 * 插入一条记录（选择字段， null 字段不插入）
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return boolean
	 */
	boolean insertSelective(T entity);

	/**
	 * <p>
	 * 插入（批量），该方法不适合 Oracle
	 * </p>
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return boolean
	 */
	boolean insertBatch(List<T> entityList);

	/**
	 * <p>
	 * 根据 ID 删除
	 * </p>
	 * 
	 * @param id
	 *            主键ID
	 * @return boolean
	 */
	boolean deleteById(I id);

	/**
	 * <p>
	 * 根据 columnMap 条件，删除记录
	 * </p>
	 * 
	 * @param columnMap
	 *            表字段 map 对象
	 * @return boolean
	 */
	boolean deleteByMap(Map<String, Object> columnMap);

	/**
	 * <p>
	 * 根据 entity 条件，删除记录
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return boolean
	 */
	boolean deleteSelective(T entity);

	/**
	 * <p>
	 * 删除（根据ID 批量删除）
	 * </p>
	 * 
	 * @param idList
	 *            主键ID列表
	 * @return boolean
	 */
	boolean deleteBatchIds(List<I> idList);

	/**
	 * <p>
	 * 根据 ID 修改
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return boolean
	 */
	boolean updateById(T entity);

	/**
	 * <p>
	 * 根据 ID 选择修改
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return boolean
	 */
	boolean updateSelectiveById(T entity);

	/**
	 * <p>
	 * 根据ID 批量更新
	 * </p>
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return boolean
	 */
	boolean updateBatchById(List<T> entityList);

	/**
	 * <p>
	 * 根据 ID 查询
	 * </p>
	 * 
	 * @param id
	 *            主键ID
	 * @return T
	 */
	T selectById(I id);

	/**
	 * <p>
	 * 查询（根据ID 批量查询）
	 * </p>
	 * 
	 * @param idList
	 *            主键ID列表
	 * @return List<T>
	 */
	List<T> selectBatchIds(List<I> idList);

	/**
	 * <p>
	 * 查询（根据 columnMap 条件）
	 * </p>
	 * 
	 * @param columnMap
	 *            表字段 map 对象
	 * @return List<T>
	 */
	List<T> selectByMap(Map<String, Object> columnMap);

	/**
	 * <p>
	 * 根据 entity 条件，查询一条记录
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return T
	 */
	T selectOne(T entity);

	/**
	 * <p>
	 * 根据 entity 条件，查询总记录数
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return int
	 */
	int selectCount(T entity);


	int deleteByPrimaryKey(I id);

	T selectByPrimaryKey(I id);

	int updateByPrimaryKeySelective(T entity);

	int updateByPrimaryKey(T entity);
}
