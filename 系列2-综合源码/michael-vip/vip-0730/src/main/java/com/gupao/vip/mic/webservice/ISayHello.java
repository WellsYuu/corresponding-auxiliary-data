package com.gupao.vip.mic.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@WebService //SE和SEI的实现类
public interface ISayHello {

    @WebMethod //SEI中的方法
    String sayHello(String name);
}
