package com.gupao.vip.mic.webservice;

import javax.xml.ws.Endpoint;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Bootstrap {

    public static void main(String[] args) {

        Endpoint.publish("http://localhost:8080/vip/hello",new SayHelloImpl());

        System.out.println("publish success");
    }
}
