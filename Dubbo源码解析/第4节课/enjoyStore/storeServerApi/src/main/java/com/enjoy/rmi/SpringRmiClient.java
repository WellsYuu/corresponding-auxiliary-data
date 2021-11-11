package com.enjoy.rmi;

import com.enjoy.service.RmiEnjoyService;
import com.enjoy.service.VipUserService;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class SpringRmiClient {
    private static String preUrl = "rmi://127.0.0.1:1919/";

    public static void main(String[] args) {
        Object o = getProxy(RmiEnjoyService.class,   preUrl+RmiEnjoyService.class.getName());
        RmiEnjoyService proxy1 = (RmiEnjoyService) o;
        System.out.println(proxy1.process("111"));

        o = getProxy(VipUserService.class,preUrl+VipUserService.class.getName());
        VipUserService proxy2 = (VipUserService) o;
        System.out.println(proxy2.getVipDetail("111"));
    }

    /**
     * 创建代理对象
     * @param clz
     * @param url
     * @return
     */
    public static Object getProxy(Class clz,String url) {
        //创建远程代理
        RmiProxyFactoryBean rpfb = new RmiProxyFactoryBean();

        //可以根据需要动态设定rmi的ip地址和端口----
        // url对象的中转对象里的 目标是论证
        rpfb.setServiceUrl(url);

        //设置访问接口
        rpfb.setServiceInterface(clz);

        //设置结束，让rmi开始链接远程的服务
        rpfb.afterPropertiesSet();

        //获取链接后的返回结果
        return rpfb.getObject();
    }

}

