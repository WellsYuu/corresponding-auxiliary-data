package com.imooc.jiangzh.user.vo;

import java.io.Serializable;

public class UserModel implements Serializable{

    private int uuid;
    private String userName;
    private int age;

    public UserModel(){}

    public UserModel(int uuid, String userName, int age) {
        this.uuid = uuid;
        this.userName = userName;
        this.age = age;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uuid=" + uuid +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
