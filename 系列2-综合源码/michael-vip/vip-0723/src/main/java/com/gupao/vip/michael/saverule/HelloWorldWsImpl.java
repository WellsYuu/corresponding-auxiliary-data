package com.gupao.vip.michael.saverule;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@WebService
public class HelloWorldWsImpl implements HelloWorldWs{

    public String sayHello(String name) {
        return "Hello "+name+"";
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/demo/hello",new HelloWorldWsImpl());

        System.out.println("success");
    }
}
