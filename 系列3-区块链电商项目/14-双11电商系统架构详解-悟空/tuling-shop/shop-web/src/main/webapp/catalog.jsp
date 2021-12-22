<%@page import="com.jiagouedu.services.front.catalog.bean.Catalog"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Collections"%>
<%@page import="com.jiagouedu.services.front.news.bean.News"%>
<%@page import="java.util.List"%>
<%@page import="com.jiagouedu.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<style type="text/css">
.menu .items ul li .on{
	-webkit-transform: perspective(700px) translateY(0px) translateZ(0px) rotateX(0deg);
	-moz-transform: perspective(700px) translateY(0px) translateZ(0px) rotateX(0deg);
	-ms-transform: perspective(700px) translateY(0px) translateZ(0px) rotateX(0deg);
	-o-transform: perspective(700px) translateY(0px) translateZ(0px) rotateX(0deg);
	transform: perspective(700px) translateY(0px) translateZ(0px) rotateX(0deg);
	-webkit-transition:all 600ms ease-in-out 0s;
	-moz-transition:all 600ms ease-in-out 0s;
	-ms-transition:all 600ms ease-in-out 0s;
	-o-transition:all 600ms ease-in-out 0s;
	transition:all 600ms ease-in-out 0s;
	-webkit-backface-visibility:hidden;
	-moz-backface-visibility:hidden;
	-ms-backface-visibility:hidden;
	-o-backface-visibility:hidden;
	backface-visibility:hidden;
	-webkit-transform-origin: 50% 100% 0;
	-moz-transform-origin: 50% 100% 0;
	-ms-transform-origin: 50% 100% 0;
	-o-transform-origin: 50% 100% 0;
	transform-origin: 50% 100% 0;
	line-height:25px;
	height:25px;
}
.menu .items ul li .scroll {
	-webkit-transform: perspective(700px) translateY(0px) translateZ(-21px) rotateX(-90deg);
	-moz-transform: perspective(700px) translateY(0px) translateZ(-21px) rotateX(-90deg);
	-ms-transform: perspective(700px) translateY(0px) translateZ(-21px) rotateX(-90deg);
	-o-transform: perspective(700px) translateY(0px) translateZ(-21px) rotateX(-90deg);
	box-shadow: 0 2px 2px #AAAAAA;
	height:0px;
}
</style>
	
<!-- <div class="wrap"> -->
<div id="menu" class="menu" style="border: 2px solid #f40;width: 100%;">
    <s:if test="'user_centers'==#session.selectMenu">
    	<div class="items">
	        <ul>
	        	<h4 class="title">
			        <a style="text-decoration: none;"><strong>用户中心</strong></a>
			        <s class="btn_group bright">
			            <a class="bleft" title="扩展视图"></a>
			            <a class="bright" title="精简视图"></a>
			        </s>
			    </h4>
	            <li class="list-item0">
	                <h5><a>订单</a></h5>
	            </li>
	            <li class="list-item0">
	                <h5><a>个人资料</a></h5>
	            </li>
	            <li class="list-item0">
	                <h5><a>配送地址</a></h5>
	            </li>
	      </ul>
	  </div>
    </s:if>
    <s:else>
	    <div class="items" style="width: 100%;">
	        <ul>
	        	<h4 class="title">
			        <a style="text-decoration: none;"><strong>商品分类</strong></a>
			        <s class="btn_group bright">
			            <a class="bleft" title="扩展视图"></a>
			            <a class="bright" title="精简视图"></a>
			        </s>
			    </h4>
	        	<s:iterator value="#application.catalogs" status="i" var="superType">
		            <li class="list-item0">
		                <h5><s:property escape="false" value="name" /></h5>
		                <s:iterator value="children" status="i2" var="smallType">
	                		<a href="<%=request.getContextPath() %>/catalog/<s:property escape="false" value="code" />.html"><s:property escape="false" value="name" />/</a>
		                </s:iterator>
		            </li>
	        	</s:iterator>
	      </ul>
	  </div>
    </s:else>
  
</div>
