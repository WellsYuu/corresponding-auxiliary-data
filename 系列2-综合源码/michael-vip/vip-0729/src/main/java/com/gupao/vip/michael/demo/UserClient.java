package com.gupao.vip.michael.demo;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UserClient {

    public static void main(String[] args) throws IOException {
        User user=new User_Stub();

        int age=user.getAge();

        System.out.println(age);
    }
}
