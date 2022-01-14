<%@page import="java.io.PrintWriter"%>
<%@page import="com.jiagouedu.services.front.advert.bean.Advert"%>
<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--
  ~ Copyright 2021-2022 the original author or authors.
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

<!-- 登陆页面广告 -->
<%
Advert advert_login_page = SystemManager.getAdvertMap().get("advert_register_page");
response.setContentType("text/html");
if(advert_login_page==null){
	out.println("出租广告");
}else{
	if(advert_login_page.getUseImagesRandom().equals(Advert.advert_useImagesRandom_y)){
		String randomImg = "<img src=\""+SystemManager.getInstance().getImageRandom()+"\"/>";
		System.out.println("randomImg = " + randomImg);
		out.println(randomImg);
	}else{
		out.println(advert_login_page.getHtml());
	}
}
%>
