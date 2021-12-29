package com.tl.controller;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import com.tl.bean.User;
import com.tl.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {
        @Autowired
    private UserServiceImpl userService;

   @RequestMapping("/show")
    public List<User> show(int age){
        return userService.getAll(age);

    }


    @RequestMapping("/index")
    public List<String> index(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("鲁班");
        arrayList.add("悟空");
        arrayList.add("白起");
        arrayList.add("张飞");
        return arrayList;
    }



    @RequestMapping("/index2")
    /***
     * 返回试图 但是我们要告诉他不要给我返回试图 应该返回jason对象
     */
    public Map<String,Object> index2(){
        Map<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("鲁班","大保健");
        stringObjectHashMap.put("悟空","lol");
        stringObjectHashMap.put("张飞","大陆版刘畊宏");

        return stringObjectHashMap;
    }




}
