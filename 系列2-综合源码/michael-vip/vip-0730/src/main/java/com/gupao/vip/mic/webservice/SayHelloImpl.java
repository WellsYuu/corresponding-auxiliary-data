package com.gupao.vip.mic.webservice;

import javax.jws.WebService;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */

@WebService
public class SayHelloImpl implements ISayHello{

    public String sayHello(String name) {
        System.out.println("call sayHello()");
        return "Hello ,"+name+",I'am 菲菲";
    }
}
