package com.gupao.vip.michael.parent;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SuperUser{

    private static final long serialVersionUID = 6244837929799767391L;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "age=" + age +
                '}';
    }
}
