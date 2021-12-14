<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>插件站点:${site.name}</h3>
	<table border="1">
		<tr>
			<th>插件名称</th>
			<th>版本</th>
			<th>安装</th>
		</tr>
		<c:forEach items="${site.configs}" var="plugin">
			<tr>
				<td>${plugin.name}</td>
				<td>${plugin.version}</td>
				<td>
					<a href="#" onclick="install(${plugin.id})">安装</a>
				</td>
			
			</tr>
		</c:forEach>
	</table>
</body>

<script type="text/javascript">
	function install(id){
	<!--TODO 需要加上 项目上下文 -->
		$.get("/plugin?action=install&id="+id+"&url=${siteUrl}",function(data,status){
			   	 	alert(data);
		});
	}
</script>
</html>