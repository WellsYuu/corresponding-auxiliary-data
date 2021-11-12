package cn.enjoy.product.listener;

import cn.enjoy.product.zk.ServiceRegister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;

/**
 * Created by VULCAN on 2018/7/28.
 */


public class InitListener implements ServletContextListener {

    @Value("${server.port}")
    private int port;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            ServiceRegister.register(hostAddress,port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
