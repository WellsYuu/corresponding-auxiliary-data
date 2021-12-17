<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="配送方式">
	<form action="${basepath}/manage/express" method="post">
		<table class="table table-bordered">
			<tr>
				<td colspan="8">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					
					<a href="${basepath}/manage/express/toAdd" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
							
<!-- 						<i class="icon-remove-sign icon-white"></i> 删除 -->
					<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
						<i class="icon-remove-sign icon-white"></i> 删除
					</button>
							
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl">
					</div>
				</td>
			</tr>
		</table>
		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">快递编码</th>
				<th nowrap="nowrap">名称</th>
				<th nowrap="nowrap">费用</th>
				<th nowrap="nowrap">顺序</th>
				<th style="width: 115px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.code!""}</td>
					<td nowrap="nowrap">&nbsp;${item.name!""}</td>
					<td nowrap="nowrap">&nbsp;${item.fee!""}</td>
					<td nowrap="nowrap">&nbsp;${item.order1!""}</td>
					<td nowrap="nowrap">
						<a href="toEdit?id=${item.id!""}">编辑</a>
					</td>
				</tr></#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl">
				</td>
			</tr>
		</table>
	</form>

</@page.pageBase>