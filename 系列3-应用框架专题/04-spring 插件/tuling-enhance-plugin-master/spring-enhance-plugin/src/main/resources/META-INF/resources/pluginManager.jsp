<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>已安装插件</h3>
	<table border="1">
		<tr>
			<th>插件名称</th>
			<th>版本</th>
			<th>启用</th>
			<th>操作3</th>
		</tr>
		<c:forEach items="${havePlugins}" var="plugin">
			<tr>
				<td>${plugin.name}</td>
				<td>${plugin.version}</td>
				<td>
				<c:if test="${plugin.active == true}">
						<input type="checkbox" checked="checked" onchange="active(this,${plugin.id})" value="" />
				</c:if> 
				<c:if test="${plugin.active == false}">
						<input type="checkbox" checked="checked" onchange="active(this,${plugin.id})" value="" />
				</c:if>
				
				</td>
				
				<td>
					<a href="#">更新</a> |<a href="#">卸载</a>| <a href="#">控制台</a>
				</td>
			
			</tr>
		</c:forEach>
	</table>
</body>

<script type="text/javascript">
	function active(checkBox,id){
		var check=$(checkBox).prop('checked');
		<!--TODO 需要加上 项目上下文 -->
		if(check){
			$.get("/plugin?action=active&id="+id,function(data,status){
			   	 	alert(data);
			  });
		}else{
			$.get("/plugin?action=disable&id="+id,function(data,status){
		   	 	alert(data);
		  });
		}
	}
</script>
</html>