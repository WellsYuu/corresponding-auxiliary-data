package com.stylefeng.guns.modular.housemanager.cache;

import com.stylefeng.guns.common.persistence.model.TblHouse;

import java.util.List;

public interface HouseCacheDAO {

    void addHouse(TblHouse tblHouse) throws Exception;

    void updateHouse(TblHouse tblHouse) throws Exception;

    void delHouse(Integer uuid) throws Exception;

    List<TblHouse> getHouses() throws Exception;

    TblHouse getById(Integer uuid) throws Exception;

}
