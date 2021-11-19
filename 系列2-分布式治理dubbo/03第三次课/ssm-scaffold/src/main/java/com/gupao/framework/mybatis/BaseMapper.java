package com.gupao.framework.mybatis;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * <p>
 * 这个 Mapper 支持 id 泛型
 * </p>
 *
 * @author qingyin
 * @Date 2016-08-20
 */
public interface BaseMapper<T, I> {

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    int insert(T entity);

    /**
     * <p>
     * 插入一条记录（选择字段， null 字段不插入）
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    int insertSelective(T entity);


    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象列表
     * @return int
     */
    int insertBatch(List<T> entityList);


    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    int deleteById(I id);


    /**
     * <p>
     * 根据 columnMap 条件，删除记录
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    int deleteByMap(Map<String, Object> columnMap);


    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    int deleteSelective(T entity);


    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    int deleteBatchIds(List<I> idList);


    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    int updateById(T entity);


    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     *
     * @param entity 实体对象
     */
    int updateSelectiveById(T entity);


    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象列表
     * @return int
     */
    int updateBatchById(List<T> entityList);


    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return T
     */
    T selectById(I id);


    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return List<T>
     */
    List<T> selectBatchIds(List<I> idList);


    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return List<T>
     */
    List<T> selectByMap(Map<String, Object> columnMap);


    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return T
     */
    T selectOne(T entity);


    /**
     * <p>
     * 根据 entity 条件，查询总记录数
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    int selectCount(T entity);

    int deleteByPrimaryKey(I id);

    T selectByPrimaryKey(I id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);
}
