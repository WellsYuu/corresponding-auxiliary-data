<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="角色管理">
<script type="text/javascript">
$(function(){
	$("#result_table tr").mouseover(function(){
		$(this).addClass("over");}).mouseout(function(){
		$(this).removeClass("over");});
	
	//全选、反选
	$("#checkAll").click(function() {
        $('input[type=checkbox]').attr("checked",$(this).attr("checked")?true:false); 
    });
});
function deleteSelect(){
	if($("input:checked").size()==0){
		return false;
	}
	return confirm("确定删除选择的记录?");
}
</script>

<form action="${basepath}/manage/role"  method="post">
			<table class="table table-bordered">
				<tr  >
					<td>
    <#if checkPrivilege("role/selectList")>
							<a href="${basepath}/manage/role/selectList" class="btn btn-primary">
								<i class="icon-search icon-white"></i> 查询
							</a>
        </#if>
                        <#if checkPrivilege("role/insert")>
							<a href="${basepath}/manage/role/toAdd" class="btn btn-success">
								<i class="icon-plus-sign icon-white"></i> 添加
							</a>
                        </#if>
						<#--<%if(PrivilegeUtil.check(request.getSession(), "role!deletes.action")){%>-->
<#--<%-- 							<s:submit method="deletes" onclick="return deleteSelect();" value="删除" cssClass="btn btn-danger"/> --%>-->
						<#--<%} %>-->

						<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                            <#include "/manage/system/pager.ftl"/>
						</div>
					</td>
				</tr>
			</table>
			
			<table class="table table-bordered table-hover">
				<tr style="background-color: #dff0d8">
					<th width="20">
						<input type="checkbox" id="checkAll"/>
					</th>
					<th  style="display: none;">rid</th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>数据库权限</th>
					<th>状态</th>
					<th width="50px">操作</th>
				</tr>
                <#list pager.list as item>
					<tr>
						<td><#if item.id!=1><input type="checkbox" name="ids" value="${item.id}/>"/></#if></td>
						<td  style="display: none;">&nbsp;${item.rid!""}</td>
						<td>&nbsp;${item.role_name!""}</td>
						<td>&nbsp;${item.role_desc!""}</td>
						<td>&nbsp;${item.role_dbPrivilege!""}</td>
						<td>&nbsp;
							<#if item.status=="y">
								<img alt="显示" src="${basepath}/resource/images/action_check.gif">
							<#else>
								<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
							</#if>
						</td>
						<td>
							<!-- 系统角色只能是超级管理员编辑 -->
                            <#if currentUser().username == "admin">
								<a href="${basepath}/manage/role/toEdit?id=${item.id}">编辑</a>
                            </#if>
						</td>
					</tr>
                </#list>
				<tr>
								<td colspan="15" style="text-align:center;">
                                    <#include "/manage/system/pager.ftl"/>
								</td>
							</tr>
			</table>
    </form>
</@page.pageBase>