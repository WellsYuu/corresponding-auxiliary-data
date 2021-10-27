package cn.enjoyedu.ch02.serializable.msgpack;

import org.msgpack.annotation.Message;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：实体类
 */
@Message//MessagePack提供的注解，表明这是一个需要序列化的实体类
public class User {
    private String id;
    private String userName;
    private int age;
    private UserContact userContact;

    public User(String userName, int age, String id) {
        this.userName = userName;
        this.age = age;
        this.id = id;
    }

    public User() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                ", userContact=" + userContact +
                '}';
    }
}
