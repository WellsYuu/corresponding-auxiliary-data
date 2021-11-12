package com.enjoy.create.prototype;

/**
 * 声明此类可以被clone
 */
public class Sheep implements Cloneable {

    private int age;
    private String sex;
    private Admin admin;

    public Sheep(int age, String sex, Admin admin) {
        super();
        this.age = age;
        this.sex = sex;
        this.admin = admin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Sheep [age=" + age + ", sex=" + sex + ", admin=" + admin + "]";
    }

    //调用Object的clone方法
    public Sheep clone() {
        Sheep sheep = null;
        try {
            sheep =  (Sheep) super.clone();
            sheep.admin = this.admin.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sheep;
    }

}

