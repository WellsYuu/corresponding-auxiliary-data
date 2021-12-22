package com.alibaba.rocketmq.action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.rocketmq.common.MixAll;

@Controller
public class ConfigureAction {
	/**
	 * 1:客户端可以根据url动态更新nameserver配置，此处提供。<br/>
	 * 2:客户端默认访问访问路径：http://jmenv.tbsite.net:8080/rocketmq/nsaddr <br/>
	 * 其中：<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;a:jmenv.tbsite.net可以在客户端通过设置系统属性rocketmq.namesrv.domain改变<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;b:原则上nsaddr可以在客户端通过设置系统属性rocketmq.namesrv.domain.subgroup改变，但我们不变，就对应此处的url<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;c:8080端口，不能改变，这限制了我们的应用，但是不改rocketmq源码情况下没有办法<br/>
	 */
    @RequestMapping(value = "/rocketmq/nsaddr", method = { RequestMethod.GET, RequestMethod.POST })
    public String consumerProgress(HttpServletResponse response) {
    	PrintWriter writer = null;
    	try {
    		writer = response.getWriter();
    		String nameServer = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY);
    		writer.write(nameServer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
    	return null;
    }
    
}
