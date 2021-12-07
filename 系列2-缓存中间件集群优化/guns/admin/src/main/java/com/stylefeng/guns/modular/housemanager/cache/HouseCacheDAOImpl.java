package com.stylefeng.guns.modular.housemanager.cache;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.common.persistence.model.TblHouse;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.housemanager.service.ITblHouseService;
import io.swagger.models.auth.In;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class HouseCacheDAOImpl implements HouseCacheDAO{

    private static final String INDEX_NAME="indexUuids";
    @Autowired
    private MemcachedClient memcachedClient;

    @Autowired
    private ITblHouseService tblHouseService;

    /*
      思路一：
        1、indexUuids : 1,2,3,4,5,6,7
        2、
            house:1 -> json
            house:2 -> json
            house:3 -> json
            house:4 -> json
            house:6 -> json

        思路二：
          list<TblHouse> -> json -> key: indexObjects
     */
    @Override
    public void addHouse(TblHouse tblHouse) throws Exception {
        // 先修改数据库
        boolean isSuccess = tblHouseService.insert(tblHouse);
        if(isSuccess){
            // 转换为JSON实现
            String tblHouseStr = JSON.toJSONString(tblHouse);

            // 如果成功，则修改缓存
            // 判断indexUuids存不存在 ""  "   "
              String uuids = memcachedClient.get(INDEX_NAME);
              if(uuids!=null && uuids.trim().length()>0){
                  // 如果存在，修改indexUuids
                  memcachedClient.append(INDEX_NAME,","+tblHouse.getId());
                  memcachedClient.set(""+tblHouse.getId(),0,tblHouseStr);
              }else{
                  // 如果不存在， 初始化一个indexUuids -> tblhouse.uuid
                  memcachedClient.set(INDEX_NAME,0,""+tblHouse.getId());
                  memcachedClient.set(""+tblHouse.getId(),0,tblHouseStr);
              }

        }else{
            // TODO -> 可以选择返回或抛例外
        }
    }

    @Override
    public void updateHouse(TblHouse tblHouse) throws Exception {
        // 先修改数据库
        boolean isSuccess = tblHouseService.updateById(tblHouse);
        if(isSuccess){
            // 转换为JSON实现
            String tblHouseStr = JSON.toJSONString(tblHouse);

            // 如果成功，则修改缓存
            // 判断indexUuids存不存在 ""  "   "
            String uuids = memcachedClient.get(INDEX_NAME);
            if(uuids!=null && uuids.trim().length()>0){
                memcachedClient.replace(""+tblHouse.getId(),0,tblHouseStr);
            }else{
                // 如果不存在， 初始化一个indexUuids -> tblhouse.uuid
                memcachedClient.set(INDEX_NAME,0,""+tblHouse.getId());
                memcachedClient.replace(""+tblHouse.getId(),0,tblHouseStr);
            }

        }else{
            // TODO -> 可以选择返回或抛例外
        }
    }

    @Override
    public void delHouse(Integer uuid) throws Exception {
        boolean isSuccess = tblHouseService.deleteById(uuid);
        String uuidStr = ""+uuid;
        if(isSuccess){
            // 修改indexUuids   101,3,4,5,101,202,2,404
            String uuids = memcachedClient.get(INDEX_NAME);
            if(!ToolUtil.isEmpty(uuids)) {
                // 将字符串转换为集合，循环寻找匹配值，然后删除,将结果放入缓存
                // 将uuids作为字符串处理  uuid=,2, replace ,
                // 如果为首位 -> uuid, -> subString
                if(uuids.startsWith(uuid+",")){
                    uuids = uuids.substring(uuidStr.length()+1);
                }else if(uuids.endsWith(","+uuid)){
                    // 如果是结尾 -> ,uuid -> sub
                    int endIndex = uuids.length() - (uuidStr.length()+1);
                    uuids = uuids.substring(0,endIndex);
                }else{
                    // 如果是中间 -> ,uuid, -> replace -> ,
                    uuids = uuids.replace(","+uuid+",",",");
                }
                // 更新索引列表
                memcachedClient.set(INDEX_NAME,0,uuids);
            }
            // 删除对应的数据
            memcachedClient.delete(uuidStr);
        }else{
            // TODO -> 可以选择返回或抛例外
        }


    }


    @Override
    public List<TblHouse> getHouses() throws Exception {
        // 101,1,2,3,4,5,7,202,404
        List<String> keys = new ArrayList<>();
        List<TblHouse> tblHouses = new ArrayList<>();

        String uuids = memcachedClient.get(INDEX_NAME);
        // 组织所有key集合
        keys = Arrays.asList(uuids.split(","));

        // 这是一个反例
//        Map<String,Object> maps = memcachedClient.get(keys);
//        for(Object obj : maps.values()){
//            tblHouses.add((TblHouse) obj);
//        }

        // 正常的操作形式
        Map<String,String> maps = memcachedClient.get(keys);
        for(String tblHouseJson : maps.values()){
            TblHouse tblHouse = JSON.parseObject(tblHouseJson,TblHouse.class);
            tblHouses.add(tblHouse);
        }

        return tblHouses;
    }

    @Override
    public TblHouse getById(Integer uuid) throws Exception {

        String tblHouseJSON = memcachedClient.get(""+uuid);

        TblHouse tblHouse = JSON.parseObject(tblHouseJSON,TblHouse.class);

        return tblHouse;
    }

}
