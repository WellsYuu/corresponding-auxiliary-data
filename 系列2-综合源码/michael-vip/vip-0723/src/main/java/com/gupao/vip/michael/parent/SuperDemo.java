package com.gupao.vip.michael.parent;

import com.gupao.vip.michael.Person;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SuperDemo {

    public static void main(String[] args) {
        SerializePerson();

        User user=DeSerializePerson();

        System.out.println(user);
    }

    private static void SerializePerson(){
        try {
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(new File("person")));
            User person=new User();
            person.setAge(18);
            oo.writeObject(person);

            System.out.println("序列化成功");

            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static User DeSerializePerson(){
        ObjectInputStream ois= null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("person")));
            User user=(User)ois.readObject();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
