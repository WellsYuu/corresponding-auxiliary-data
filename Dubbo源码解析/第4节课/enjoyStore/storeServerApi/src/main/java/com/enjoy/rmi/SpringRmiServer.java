package com.enjoy.rmi;

import com.enjoy.service.RmiEnjoyService;
import com.enjoy.service.VipUserService;
import com.enjoy.service.impl.RmiEnjoyServiceImpl;
import com.enjoy.service.impl.VipUserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;


//server端暴露中转对象
public class SpringRmiServer {
    private static RmiServiceExporter proxy;
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        //扫描整个server目录
        ctx.scan("com.enjoy.rmi");
        //刷新
        ctx.refresh();

        //中转对象 --- rmi对象
        proxy = ctx.getBean(RmiServiceExporter.class);

        //发布 --- key：RmiEnjoyService.class.getName()
        export(RmiEnjoyService.class,new RmiEnjoyServiceImpl(),RmiEnjoyService.class.getName());

        //发布 ---- --- key：RmiEnjoyService.class.getName()
        export(VipUserService.class,new VipUserServiceImpl(),VipUserService.class.getName());


    }


    /**
     * 设定中转对象，要去暴露的目标，组目标绑定一个url
     * @param itf
     * @param impl
     * @param url
     * @return
     */
    public static String export(Class itf,Object impl,String url){
        proxy.setServiceInterface(itf);
        proxy.setService(impl);
        proxy.setServiceName(url);

        try {
            proxy.afterPropertiesSet();//为什么要调这个，本bean初始完成
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        String exportUrl = "rmi://127.0.0.1:1919/"+url;
        System.out.println("publish url:"+exportUrl);
        return exportUrl;
    }





}

