<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@page import="com.jiagouedu.services.front.product.bean.Product"%>
<%@page import="com.jiagouedu.services.front.product.ProductService"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="com.jiagouedu.services.front.news.bean.News"%>
<%@page import="com.jiagouedu.core.FrontContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<s:if test="e.title==null or e.title==''">
	<title><s:property value="e.name" escape="false"/></title>
</s:if>
<s:else>
	<title><s:property value="e.title" escape="false"/></title>
</s:else>

<s:if test="e.description==null or e.description==''">
	<meta name="description" content="<s:property value="e.name" escape="false"/>" />
</s:if>
<s:else>
	<meta name="description" content="<s:property value="e.description" escape="false"/>" />
</s:else>

<s:if test="e.keywords==null or e.keywords==''">
	<meta name="keywords"  content="<s:property value="e.name" escape="false"/>" />
</s:if>
<s:else>
	<meta name="keywords"  content="<s:property value="e.keywords" escape="false"/>" />
</s:else>

<link rel="shortcut icon" href="<%=request.getContextPath() %>/resource/images/favicon.png">
<%@ include file="/resource/common_css.jsp"%>
<style type="text/css">
.topCss {
	height: 28px;
	line-height: 28px;
	background-color: #f8f8f8;
	border-bottom: 1px solid #E6E6E6;
	padding-left: 9px;
	font-size: 14px;
	font-weight: bold;
	position: relative;
	margin-top: 0px;
}
</style>
<script>
function defaultProductImg(){ 
	var img=event.srcElement; 
	img.src="<%=SystemManager.getInstance().getSystemSetting().getDefaultProductImg() %>"; 
	img.onerror=null; //控制不要一直跳动 
}
</script>
</head>

<body>
	<div id="wrap">
		<%@ include file="indexMenu.jsp"%>
		<div class="container">

			<div class="row">
				<!-- 问卷题目列表 -->
				感谢您参与了本次问卷调查！您将有机会抽取奖品！
			</div>
		</div>
	</div>
	<%@ include file="foot.jsp"%>
<script>
$(function() {
});
</script>
</body>
</html>
