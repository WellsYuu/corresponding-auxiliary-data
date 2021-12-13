package com.gupaoedu.common.listeners;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class SystemLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}
	
	
}
