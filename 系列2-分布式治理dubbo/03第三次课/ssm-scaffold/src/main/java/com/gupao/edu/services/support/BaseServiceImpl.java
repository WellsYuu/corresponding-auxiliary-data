package com.gupao.edu.services.support;

import com.gupao.framework.mybatis.AutoMapper;
import com.gupao.framework.service.impl.SuperServiceImpl;

public class BaseServiceImpl<M extends AutoMapper<T>, T> extends SuperServiceImpl<M, T> {

}
