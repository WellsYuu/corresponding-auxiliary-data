package com.gupao.framework.service.impl;

import com.gupao.framework.common.pager.Pager;
import com.gupao.framework.mybatis.BaseMapper;
import com.gupao.framework.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ， I 是主键泛型 ）
 * </p>
 * 
 * @author qingyin
 * @Date 2016-08-20
 */
public class ServiceImpl<M extends BaseMapper<T, I>, T, I> implements IService<T, I> {

	@Autowired
	protected M baseMapper;

	/**
	 * 判断数据库操作是否成功
	 * 
	 * @param result
	 *            数据库操作返回影响条数
	 * @return boolean
	 */
	protected boolean retBool(int result) {
		return (result >= 1) ? true : false;
	}

	public boolean insert(T entity) {
		return retBool(baseMapper.insert(entity));
	}

	public boolean insertSelective(T entity) {
		return retBool(baseMapper.insertSelective(entity));
	}

	public boolean insertBatch(List<T> entityList) {
		return retBool(baseMapper.insertBatch(entityList));
	}

	public boolean deleteById(I id) {
		return retBool(baseMapper.deleteById(id));
	}

	public boolean deleteByMap(Map<String, Object> columnMap) {
		return retBool(baseMapper.deleteByMap(columnMap));
	}

	public boolean deleteSelective(T entity) {
		return retBool(baseMapper.deleteSelective(entity));
	}

	public boolean deleteBatchIds(List<I> idList) {
		return retBool(baseMapper.deleteBatchIds(idList));
	}

	public boolean updateById(T entity) {
		return retBool(baseMapper.updateById(entity));
	}

	public boolean updateSelectiveById(T entity) {
		return retBool(baseMapper.updateSelectiveById(entity));
	}

	public boolean updateBatchById(List<T> entityList) {
		return retBool(baseMapper.updateBatchById(entityList));
	}

	public T selectById(I id) {
		return baseMapper.selectById(id);
	}

	public List<T> selectBatchIds(List<I> idList) {
		return baseMapper.selectBatchIds(idList);
	}

	public List<T> selectByMap(Map<String, Object> columnMap) {
		return baseMapper.selectByMap(columnMap);
	}

	public T selectOne(T entity) {
		return baseMapper.selectOne(entity);
	}

	public int selectCount(T entity) {
		return baseMapper.selectCount(entity);
	}

	@Override
	public int deleteByPrimaryKey(I id) {
		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public T selectByPrimaryKey(I id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return baseMapper.updateByPrimaryKey(entity);
	}
}
