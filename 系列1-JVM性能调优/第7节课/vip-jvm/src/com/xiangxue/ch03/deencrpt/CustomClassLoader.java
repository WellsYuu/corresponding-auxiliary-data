package com.xiangxue.ch03.deencrpt;


import java.io.File;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：自定义的类加载器
 */
public class CustomClassLoader extends ClassLoader{

    private final String name;
    private String basePath;
    private final static String FILE_EXT = ".class";

    public CustomClassLoader(String name) {
        super();
        this.name = name;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    //实际解密
    private byte[] loadClassData(String name){
    	int x = 0 ;
        byte[] data = null;
        XorEncrpt demoEncryptUtil = new XorEncrpt();
        // use x;
        int y = 1;
        try {
        	String tempName = name.replaceAll("\\.","\\\\");
            data = demoEncryptUtil.decrypt(new File(basePath+tempName+FILE_EXT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name,data,0,data.length);
    }
}
