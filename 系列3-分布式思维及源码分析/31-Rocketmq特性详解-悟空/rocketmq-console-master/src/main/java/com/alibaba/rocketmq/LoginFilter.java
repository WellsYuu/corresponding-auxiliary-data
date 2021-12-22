package com.alibaba.rocketmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class LoginFilter implements Filter {
	
	private List<String> whiteList = new ArrayList<>();
	/**
	 * 单用户登录时间
	 */
	public static volatile DateTime loginTime = DateTime.now().minusDays(1);
	public static volatile String user = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		whiteList.add("/");//登录页面
		whiteList.add("/doLogin");//登录action
		whiteList.add("/rocketmq/nsaddr");//namesrv静态服务器地址
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response; 
		String uri = req.getRequestURI();
		if(whiteList.contains(uri)) {
			chain.doFilter(request, response);
		}
		else {
			DateTime now = DateTime.now();
			String currentUser = null;
			Cookie[] cookies = req.getCookies();//这样便可以获取一个cookie数组
			for(Cookie cookie : cookies){
			    if("user".equals(cookie.getName())) {
			    	currentUser = cookie.getValue();
			    	break;
			    }
			    
			}
			if(StringUtils.isBlank(currentUser) || //没有登录用户
					!currentUser.equals(user)){ // 当前用户和登录用户不匹配
				resp.sendRedirect("/");  
				return;
			}
			if(loginTime.plusMinutes(30).isBefore(now)) {//登录30分钟强行退出
				loginTime = DateTime.now().minusDays(1);
				user = null;
				resp.sendRedirect("/");  
				return;
			}
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
