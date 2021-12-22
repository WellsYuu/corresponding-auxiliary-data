<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@page import="com.jiagouedu.services.front.product.bean.Product"%>
<%@page import="com.jiagouedu.services.front.product.ProductService"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="com.jiagouedu.services.front.news.bean.News"%>
<%@page import="com.jiagouedu.core.FrontContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

<link rel="shortcut icon" href="<%=SystemManager.getInstance().getSystemSetting().getShortcuticon()%>">
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
				<%
					//request.setAttribute("_question", SystemManager.question);
				%>
				<s:form action="/question/submitQuestion.html" namespace="/" theme="simple" id="form">
				<div class="panel panel-default">
					    <div class="panel-heading"><s:property value="e.title" escape="false"/></div>
					  		<div class="panel-body">
					    	<p><s:property value="e.title2" escape="false"/></p>
					    </div>
							<s:iterator value="e.questionnaireItemMap" status="i" var="item">
								<ul class="list-group">
									<li class="list-group-item">
										<s:property value="#i.index+1" escape="false"/>
										<s:property escape="false" value="value.subject" /><br>
										
										<div style="margin-left: 20px;">
											<div style="display: table-row;">
												<s:if test="value.display.equals(\"inline\")">
													<s:iterator value="value.optionList" status="ii" var="option">
														<div class="pull-left" style="margin-right: 30px;">
															<s:if test="!value.type.equals(\"text\")">
																<s:property escape="false" value="#ii.index+1" />)、
															</s:if>
															<s:if test="value.type.equals(\"radio\")">
																<input type="radio" name="<s:property escape="false" value="value.id" />"/>
															</s:if>
															<s:elseif test="value.type.equals(\"checkbox\")">
																<input type="checkbox" name="<s:property escape="false" value="value.id" />"/>
															</s:elseif>
															<s:else>
																<s:textarea name="value.id"></s:textarea>
															</s:else>
															
															<s:if test="!value.type.equals(\"text\")">
																<s:property escape="false" value="option" />
															</s:if>
														</div>
													</s:iterator><br>
												</s:if>
												<s:else>
													<s:iterator value="value.optionList" status="ii" var="option">
														<s:if test="!value.type.equals(\"text\")">
															<s:property escape="false" value="#ii.index+1" />)、
														</s:if>
														<s:if test="value.type.equals(\"radio\")">
															<input type="radio" name="<s:property escape="false" value="value.id" />"/>
														</s:if>
														<s:elseif test="value.type.equals(\"checkbox\")">
															<input type="checkbox" name="<s:property escape="false" value="value.id" />"/>
														</s:elseif>
														<s:else>
															<s:textarea name="value.id"></s:textarea>
														</s:else>
														
														<s:if test="!value.type.equals(\"text\")">
															<s:property escape="false" value="option" /><br>
														</s:if>
													</s:iterator>
												</s:else>
											</div>
										</div>
									</li>
								</ul>
							</s:iterator>
							
							<div class="panel-footer" style="text-align: center;">
								<s:submit method="submitQuestion" action="questionnaire"  onclick="return onSubmit(this);" 
								value="提交问卷" cssClass="btn btn-warning btn-sm"/>
							</div>
				</div>
				</s:form>
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
