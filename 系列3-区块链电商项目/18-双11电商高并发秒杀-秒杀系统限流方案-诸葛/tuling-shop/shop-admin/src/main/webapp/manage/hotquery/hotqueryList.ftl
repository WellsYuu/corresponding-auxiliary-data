<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="热门查询管理">
<style type="text/css">
.titleCss {
	background-color: #e6e6e6;
	border: solid 1px #e6e6e6;
	position: relative;
	margin: -1px 0 0 0;
	line-height: 32px;
	text-align: left;
}

.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}

.liCss {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	height: 30px;
	text-align: left;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
	<form action="${basepath}/manage/hotquery" namespace="/manage" method="post" theme="simple" id="form" name="form">
		<input type="hidden" value="${e.type!""}" name="type"/>
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered">
			<tr>
				<td colspan="16">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
						
					<a href="toAdd?type=${e.type!""}" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
						
					<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
						<i class="icon-remove-sign icon-white"></i> 删除
					</button>
						
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                        <#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>

		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20px"><input type="checkbox" id="firstCheckbox" /></th>
				<th width="100px">ID</th>
				<th>热门查询关键字</th>
				<th>链接</th>
				<th width="60px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td >${item.id!""}</td>
					<td class="aCss">
					  <a href="toEdit?id=${item.id!""}" >${item.key1!""}</a>
					</td>
					<td>&nbsp;<a target="_blank" href="${item.url!""}">${item.url!""}</a></td>
					<td>
						<a href="toEdit?id=${item.id!""}">编辑</a>
					</td>
				</tr>
            </#list>

			<tr>
				<td colspan="17" style="text-align: center;font-size: 12px;"><#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
		
	</form>
	

</@page.pageBase>