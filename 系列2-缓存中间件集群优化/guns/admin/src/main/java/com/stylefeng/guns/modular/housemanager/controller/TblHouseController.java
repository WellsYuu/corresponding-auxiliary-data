package com.stylefeng.guns.modular.housemanager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.config.properties.XMemcachedProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.page.PageInfoBT;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.housemanager.cache.HouseCacheDAO;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.TblHouse;
import com.stylefeng.guns.modular.housemanager.service.ITblHouseService;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 房屋管理控制器
 *
 * @author fengshuonan
 * @Date 2017-12-30 18:15:15
 */
@Controller
@RequestMapping("/tblHouse")
public class TblHouseController extends BaseController {

    private String PREFIX = "/housemanager/tblHouse/";

    @Autowired
    private ITblHouseService tblHouseService;

    @Autowired
    private XMemcachedProperties xMemcachedProperties;

    @Autowired
    private HouseCacheDAO houseCacheDAO;
    /**
     * 跳转到房屋管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tblHouse.html";
    }

    /**
     * 跳转到添加房屋管理
     */
    @RequestMapping("/tblHouse_add")
    public String tblHouseAdd() {
        return PREFIX + "tblHouse_add.html";
    }

    /**
     * 跳转到修改房屋管理
     */
    @RequestMapping("/tblHouse_update/{tblHouseId}")
    public String tblHouseUpdate(@PathVariable Integer tblHouseId, Model model) {
        TblHouse tblHouse = tblHouseService.selectById(tblHouseId);
        model.addAttribute("item",tblHouse);
        LogObjectHolder.me().set(tblHouse);
        return PREFIX + "tblHouse_edit.html";
    }

    /**
     * 获取房屋管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) throws Exception {
        // 判断是否开启缓存
        if(xMemcachedProperties.isOpenCache()){
            // 如果开启缓存，则使用cachedao获取数据
            // 是否按条件查询
            if(ToolUtil.isEmpty(condition)){
                List<TblHouse> tblHouses = houseCacheDAO.getHouses();
                return tblHouses;
            }else{
                EntityWrapper<TblHouse> entityWrapper = new EntityWrapper<>();
                entityWrapper.like("house_user","%"+condition+"%");
                return tblHouseService.selectList(entityWrapper);
            }
        }else{
            // 如果未开启，则使用标准的service访问
            if(ToolUtil.isEmpty(condition)){
//          return tblHouseService.selectList(null);
                // 加入物理分页
                Page<TblHouse> page = new PageFactory<TblHouse>().defaultPage();
                page = tblHouseService.selectPage(page);
                PageInfoBT<TblHouse> pageInfoBT = this.packForBT(page);

                return pageInfoBT;
            }else{
                EntityWrapper<TblHouse> entityWrapper = new EntityWrapper<>();
                entityWrapper.like("house_user","%"+condition+"%");
//          return tblHouseService.selectList(entityWrapper);
                // 加入物理分页
                Page<TblHouse> page = new PageFactory<TblHouse>().defaultPage();
                page = tblHouseService.selectPage(page,entityWrapper);
                PageInfoBT<TblHouse> pageInfoBT = this.packForBT(page);

                return pageInfoBT;
            }
        }
    }

    /**
     * 新增房屋管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TblHouse tblHouse) throws Exception {
        if(xMemcachedProperties.isOpenCache()){
            houseCacheDAO.addHouse(tblHouse);
        }else{
            tblHouseService.insert(tblHouse);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除房屋管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tblHouseId) throws Exception {
        if(xMemcachedProperties.isOpenCache()){
            houseCacheDAO.delHouse(tblHouseId);
        }else{
            tblHouseService.deleteById(tblHouseId);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 修改房屋管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TblHouse tblHouse) throws Exception {
        if(xMemcachedProperties.isOpenCache()){
            houseCacheDAO.updateHouse(tblHouse);
        }else{
            tblHouseService.updateById(tblHouse);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 房屋管理详情
     */
    @RequestMapping(value = "/detail/{tblHouseId}")
    @ResponseBody
    public Object detail(@PathVariable("tblHouseId") Integer tblHouseId) throws Exception {
        if(xMemcachedProperties.isOpenCache()){
            return houseCacheDAO.getById(tblHouseId);
        }else{
            return tblHouseService.selectById(tblHouseId);
        }
    }
}
