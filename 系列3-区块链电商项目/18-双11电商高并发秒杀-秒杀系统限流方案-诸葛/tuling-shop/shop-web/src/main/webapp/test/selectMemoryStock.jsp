<%@page import="com.jiagouedu.services.front.product.bean.ProductStockInfo"%>
<%@page import="java.util.concurrent.ConcurrentMap"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="com.jiagouedu.services.front.news.bean.News"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/resource/common_css.jsp"%>
<style type="text/css">
.centerImageCss{
	width: 560px;
	height: 180px;
}
</style>
</head>

<body style="text-align: center;">
	<table class="table table-bordered" style="width: 90%;margin: auto;">
		<tr>
			<td>商品ID</td>
			<td>商品库存数</td>
			<td>库存是否有所改变</td>
		</tr>
		<%
			Map<String, ProductStockInfo> productStockMap = SystemManager.getInstance().getProductStockMap();
			request.setAttribute("productStockMap", productStockMap);
		%>
		<c:if test="${productStockMap!=null}">
			<tr>
				<td colspan="3">
						商品数：<%=productStockMap.size() %>
				</td>
			</tr>
			<c:forEach items="${productStockMap}" var="entry">  
				<tr>
			       	<td><c:out value="${entry.value.id}" />  </td>
			    	<td><c:out value="${entry.value.stock}" /></td>
			    	<td><c:out value="${entry.value.changeStock}" /></td>
		    	</tr>
			</c:forEach>  
		</c:if>
	</table>
</body>
</html>
