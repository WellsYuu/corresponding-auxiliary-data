package com.gupao.vip.michael.saverule;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface HelloWorldWs {

    @WebMethod
    String sayHello(String name);
}
