<#macro accountMenu currentMenu="account">
<ul class="list-group">
	<a href="${basepath}/account/account" class="list-group-item ${(currentMenu=="account")?string('active', '')}">
		<span class="glyphicon glyphicon-user"></span>&nbsp;个人资料
	</a>
	<a href="${basepath}/account/topwd" class="list-group-item ${(currentMenu=="topwd")?string('active', '')}">
		<span class="glyphicon glyphicon-screenshot"></span>&nbsp;修改密码
	</a>
	<a href="${basepath}/account/orders" class="list-group-item ${(currentMenu=="orders")?string('active', '')}">
		<span class="glyphicon glyphicon-th"></span>&nbsp;我的订单
	</a>
	<a href="${basepath}/account/score" class="list-group-item ${(currentMenu=="score")?string('active', '')}">
		<span class="glyphicon glyphicon-asterisk"></span>&nbsp;我的积分
	</a>
	<a href="${basepath}/account/address" class="list-group-item ${(currentMenu=="address")?string('active', '')}">
		<span class="glyphicon glyphicon-send"></span>&nbsp;配送地址
	</a>
	<a href="${basepath}/account/favorite" class="list-group-item ${(currentMenu=="favorite")?string('active', '')}">
		<span class="glyphicon glyphicon-tags"></span>&nbsp;收藏夹
	</a>
<#--<%--     <a href="${basepath}/account/letters.html" class="list-group-item <%=getCss("letters", aa)%>">系统信件<span class="badge"><s:property value="#session.WEB_USER_INFO.notReadLetters"/></span></a> --%>-->
</ul>
</#macro>