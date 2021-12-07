package com.stylefeng.guns.modular.housemanager.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TblHouse;
import com.stylefeng.guns.common.persistence.dao.TblHouseMapper;
import com.stylefeng.guns.modular.housemanager.service.ITblHouseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 房屋管理表 服务实现类
 * </p>
 *
 * @author jiangzh
 * @since 2017-12-30
 */
@Service
@Transactional
public class TblHouseServiceImpl extends ServiceImpl<TblHouseMapper, TblHouse> implements ITblHouseService {

//    @Override
//    public boolean insert(TblHouse entity) {
//        // 完成一次插入操作
//        boolean flag = super.insert(entity);
//
//        int i = 6/0;
//
//        int id = entity.getId();
//        EntityWrapper entityWrapper = new EntityWrapper();
//        entityWrapper.eq("id",id);
//        entity.setHouseUser(entity.getHouseUser()+"Test");
//        flag = super.update(entity,entityWrapper);
//
//        return flag;
//    }

}
