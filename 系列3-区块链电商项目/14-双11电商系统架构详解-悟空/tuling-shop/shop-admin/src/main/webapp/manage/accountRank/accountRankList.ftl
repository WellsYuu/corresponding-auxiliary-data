<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="等级管理">
	<form action="${basepath}/manage/accountRank" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td colspan="8">
<#--<%-- 					<s:submit method="selectList" value="查询" cssClass="btn btn-primary"/> --%>-->
					<a href="selectList" class="btn btn-primary">
						<i class="icon-search icon-white"></i> 查询
					</a>
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
				<th nowrap="nowrap">code</th>
				<th nowrap="nowrap">等级名称</th>
				<th nowrap="nowrap">积分范围</th>
				<th style="width: 115px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.code!""}</td>
					<td nowrap="nowrap">&nbsp;${item.name!""}</td>
					<td nowrap="nowrap">&nbsp;${item.minScore!""}~${item.maxScore!""}</td>
					<td nowrap="nowrap">
						<a href="toEdit?id=${item.id}">编辑</a>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$(function() {
		function c1(f) {
			$(":checkbox").each(function() {
				$(this).attr("checked", f);
			});
		}
		$("#firstCheckbox").click(function() {
			if ($(this).attr("checked")) {
				c1(true);
			} else {
				c1(false);
			}
		});

	});
	function deleteSelect() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定删除选择的记录?");
	}
</script>
</@page.pageBase>