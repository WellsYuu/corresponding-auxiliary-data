<%@page import="java.io.PrintWriter"%>
<%@page import="com.jiagouedu.services.front.advert.bean.Advert"%>
<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
