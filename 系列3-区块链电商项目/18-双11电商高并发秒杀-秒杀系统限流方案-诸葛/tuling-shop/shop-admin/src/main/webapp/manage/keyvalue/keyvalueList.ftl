<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="键值对管理">
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
	<form action="${basepath}/manage/keyvalue" method="post">
				<table class="table table-bordered">
					<tr>
						<td style="text-align: right;">键</td>
						<td style="text-align: left;">
							<input type="text" name="key1" id="e.key1" value="${e.key1!""}">
						</td>
						<td style="text-align: right;">值</td>
						<td style="text-align: left;">
                            <input type="text" name="value" id="e.value" value="${e.value!""}">
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
								<i class="icon-search icon-white"></i> 查询
							</button>
							<a href="${basepath}/manage/keyvalue/toAdd" class="btn btn-success">
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
						<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
						<th style="display: none;">编号</th>
						<th >键</th>
						<th >值</th>
						<th >操作</th>
					</tr>
					<#list pager.list as item>
						<tr>
							<td><input type="checkbox" name="ids"
								value="${item.id}" /></td>
							<td style="display: none;">&nbsp;${item.id}</td>
							<td>&nbsp;${item.key1!""}</td>
							<td>&nbsp;${item.value!""}</td>
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