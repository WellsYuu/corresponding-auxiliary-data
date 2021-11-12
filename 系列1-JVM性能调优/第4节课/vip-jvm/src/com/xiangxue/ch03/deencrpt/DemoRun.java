package com.xiangxue.ch03.deencrpt;

public class DemoRun {

    public static void main(String[] args) throws Exception {
        CustomClassLoader demoCustomClassLoader = new CustomClassLoader("My ClassLoader");
        demoCustomClassLoader.setBasePath("D:\\XiangXue\\vipLesson\\JVM\\code"
        		+ "\\src\\vip-jvm\\bin\\");
        Class<?> clazz = demoCustomClassLoader.findClass("com.xiangxue.ch03.deencrpt.DemoUser");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
        System.out.println(o);
    }
}
