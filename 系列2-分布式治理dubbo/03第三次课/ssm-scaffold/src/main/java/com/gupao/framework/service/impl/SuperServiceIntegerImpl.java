package com.gupao.framework.service.impl;

import com.gupao.framework.mybatis.AutoMapper;
import com.gupao.framework.mybatis.AutoMapperInteger;

/**
 * <p>
 * 主键 Long 类型 IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ）
 * </p>
 * 
 * @author qingyin
 */
public class SuperServiceIntegerImpl<M extends AutoMapperInteger<T>, T> extends ServiceImpl<M, T, Integer> {

}
