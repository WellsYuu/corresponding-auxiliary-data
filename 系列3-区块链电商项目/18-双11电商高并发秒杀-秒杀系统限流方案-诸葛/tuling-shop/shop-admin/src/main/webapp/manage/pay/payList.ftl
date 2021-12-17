<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="支付管理">
	<form action="${basepath}/manage/pay" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td colspan="6">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
<#--<%-- 					<s:submit method="toAdd" value="添加" cssClass="btn btn-success" /> --%>-->
<#--<%-- 					<s:submit method="deletes" onclick="return deleteSelect();" value="删除" cssClass="btn btn-danger" /> --%>-->
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th style="display: none;">编号</th>
				<th >名称</th>
				<th >代码</th>
				<th >卖家账号</th>
				<th >排序</th>
				<th >状态</th>
				<th width="50px">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td style="display: none;">&nbsp;${item.id!""}</td>
					<td>&nbsp;${item.name!""}</td>
					<td>&nbsp;${item.code!""}</td>
					<td>&nbsp;${item.seller!""}</td>
					<td>&nbsp;${item.order1!""}</td>
					<td>&nbsp;
						<#if item.status??&&item.status=="y">
							<img src="${basepath}/resource/images/action_check.gif">
						<#else>
							<img src="${basepath}/resource/images/action_delete.gif">
						</#if>
					</td>
					<td><a href="toEdit?id=${item.id}">编辑</a></td>
				</tr>
			</#list>

			<tr>
				<td colspan="17" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>

</@page.pageBase>