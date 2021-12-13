package com.gupaoedu.common.listeners;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 系统初始化	
 * @author Tom
 *
 */
public class SystemLoaderListener extends ContextLoaderListener{

	private Logger log = Logger.getLogger(SystemLoaderListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent e) {
		super.contextInitialized(e);
	}

	
}
