<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="FrontCache"%>
<%@page import="SystemManager"%>
<%@page import="java.net.URL"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
  ~ Copyright 2021-2022 the original author or authors
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<%
WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
FrontCache frontCache = (FrontCache) app.getBean("frontCache");

String method = request.getParameter("method");
out.println("method="+method);

try{
	if(StringUtils.isBlank(method)){
		frontCache.loadAllCache();
		out.println("加载数据成功！");

	}else if(method.equals("activity")){
		
		frontCache.loadActivityMap();
		frontCache.loadActivityProductList();
		frontCache.loadActivityScoreProductList();
		frontCache.loadActivityTuanProductList();
		out.println("加载数据成功！");
	}else if(method.equals("loadIndexImgs")){
		
		frontCache.loadIndexImgs();
		out.println("加载数据成功！");
	}else if(method.equals("loadAdvertList")){
		
		frontCache.loadAdvertList();
		out.println("加载数据成功！");
	}else if(method.equals("loadNotifyTemplate")){
		
		frontCache.loadNotifyTemplate();
		out.println("加载数据成功！");
	}else if(method.equals("loadProductStock")){
		
		frontCache.loadProductStock();
		out.println("加载数据成功！");
	}else if(method.equals("hotquery")){
		
		frontCache.loadHotquery();
		out.println("加载数据成功！");
	}
}catch(Exception e){
	e.printStackTrace();
}

%>
