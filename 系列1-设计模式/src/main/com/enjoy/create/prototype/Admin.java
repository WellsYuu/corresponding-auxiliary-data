package com.enjoy.create.prototype;

/**
 * Created by Peter on 10/9 009.
 */
public class Admin implements Cloneable{

    private int age;
    private String sex;
    public Admin(int age, String sex) {
        super();
        this.age = age;
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Admin clone() {
        try {
            return (Admin) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Admin [age=" + age + ", sex=" + sex  + "]";
    }

}

