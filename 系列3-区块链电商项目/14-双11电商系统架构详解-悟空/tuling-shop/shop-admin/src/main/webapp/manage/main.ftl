<#--该页面废弃-->
<#import "/manage/tpl/htmlBase.ftl" as html />
<@html.htmlBase>
<#--<%-->
		<#--Map<String,Object> map = ActionContext.getContext().getSession();-->
		<#--User u = (User)map.get(ManageContainer.manage_session_user_info);-->
		<#--if(u==null){-->
			<#--out.println("u="+u);-->
			<#--response.sendRedirect("user!loginOut.action");-->
			<#--return;-->
		<#--}-->
		<#--//out.print(u.getNickname()+"("+u.getUsername()+")");-->
	<#--%>-->
	
<frameset cols="210,*" >
	<frame src="${basepath}/forward.action?p=/manage/system/left" name="leftFrame" noresize="noresize"/>
<#--<%-- 	<frame src="${basepath}/manage/system/right.jsp" name="rightFrame" /> --%>-->
	<frame src="${basepath}/manage/user/initManageIndex" name="rightFrame" />
</frameset>
</@html.htmlBase>
