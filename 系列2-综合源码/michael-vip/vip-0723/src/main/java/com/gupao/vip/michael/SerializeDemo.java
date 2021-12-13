package com.gupao.vip.michael;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SerializeDemo {

    public static void main(String[] args) {
        // 序列化操作
        SerializePerson();

        //反序列化操作
        Person person=DeSerializePerson();

        System.out.println(person);
    }

    private static void SerializePerson(){
        try {
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(new File("person")));
            Person person=new Person();
            person.setAge(18);
            person.setName("mic");
            oo.writeObject(person);
            oo.flush();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person1=(Person)ois.readObject();
            person.setName("mic1");
            oo.writeObject(person);
            oo.flush();
            System.out.println("序列化成功: "+new File("person").length());

            Person person2=(Person)ois.readObject();
            System.out.println(person1+"->"+person2);

            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Person DeSerializePerson(){
        ObjectInputStream ois= null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person=(Person)ois.readObject();
            return person;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
