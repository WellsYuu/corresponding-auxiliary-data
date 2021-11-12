package com.xiangxue.ch03.deencrpt;


import java.io.File;

import com.xiangxue.ch03.deencrpt.service.DemoXorEncrpt;
import com.xiangxue.ch03.deencrpt.service.IDemoEncryptUtil;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/31
 * 创建时间: 11:29
 */
public class DemoCustomClassLoader extends ClassLoader{

    private final String name;
    private String basePath;
    private final static String FILE_EXT = ".class";

    public DemoCustomClassLoader(String name) {
        super();
        this.name = name;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private byte[] loadClassData(String name){
        byte[] data = null;
        IDemoEncryptUtil demoEncryptUtil = new DemoXorEncrpt();
        try {
            data = demoEncryptUtil.decrypt(new File(basePath+name+FILE_EXT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        if(name.indexOf("java.")<5&&name.indexOf("java.")>-1){
//            return super.loadClass(name);
//        }
//        byte[] data = this.loadClassData(name);
//        if (data == null){
//            return super.loadClass(name);
//        }
//        return this.defineClass(name,data,0,data.length);
//    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name,data,0,data.length);
    }
}
