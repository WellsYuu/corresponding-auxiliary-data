package com.gupao.vip.michael.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UserServer extends User{

    public static void main(String[] args) {
        UserServer userServer=new UserServer();
        userServer.setAge(18);

        User_Skeleton skel=new User_Skeleton(userServer);

        skel.start();
    }

}
