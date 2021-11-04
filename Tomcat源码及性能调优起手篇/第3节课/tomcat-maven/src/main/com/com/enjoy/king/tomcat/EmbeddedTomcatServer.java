package com.enjoy.king.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcatServer {
	/*
	 * 手写嵌入式Tomcat
	 * 享学课堂---king老师
	 */

	public static void main(String[] args) throws Exception{
		//把目录的绝对的路径获取到
		String classpath = System.getProperty("user.dir");
		System.out.println(classpath);
		//D:\workspace-tomcat\tomcat-maven
		//我们new一个Tomcat
		 Tomcat tomcat = new Tomcat();
		 
		 //插件是6或者6以前的
		 //Embbedded
		 
		 
		 //设置Tomcat的端口
		 //tomcat.setPort(9090);
		 Connector connector = tomcat.getConnector();
		 connector.setPort(9091);
		 //设置Host
		 Host host = tomcat.getHost();
		 //我们会根据xml配置文件来
		 host.setName("localhost");
		 host.setAppBase("webapps");
		 //前面的那个步骤只是把Tomcat起起来了，但是没啥东西
		 //要把class加载进来,把启动的工程加入进来了
		 Context context =tomcat.addContext(host, "/", classpath);

		 if(context instanceof StandardContext){
			 StandardContext standardContext = (StandardContext)context;
			 standardContext.setDefaultContextXml("D:/tools/apache-tomcat-8.0.52/conf/web.xml");
		     //我们要把Servlet设置进去
			 Wrapper wrapper =  tomcat.addServlet("/", "DemoServlet", new DemoServlet());
			 wrapper.addMapping("/king");
		 }
		 //Tomcat跑起来
		 tomcat.start();
		 //强制Tomcat server等待，避免main线程执行结束后关闭
		 tomcat.getServer().await();
		 
	}

}
