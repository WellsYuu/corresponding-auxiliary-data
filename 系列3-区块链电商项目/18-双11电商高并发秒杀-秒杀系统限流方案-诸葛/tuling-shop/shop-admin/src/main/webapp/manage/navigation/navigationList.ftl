<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="友情链接">
	<form action="${basepath}/manage/navigation" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td colspan="6">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					
					<a href="toAdd" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
					
					<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
						<i class="icon-remove-sign icon-white"></i> 删除
					</button>
					
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                        <#include "/manage/system/pager.ftl" >
					</div>
				</td>
			</tr>
		</table>
		
		<div class="alert alert-info" style="margin-bottom: 2px;text-align: left;">友情链接会自动显示到门户的最底部。友情链接的地址不要以“http://”开头。</div>
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th style="display: none;">编号</th>
				<th >名称</th>
				<th >链接</th>
				<th >打开方式</th>
				<th >位置</th>
				<th >顺序</th>
				<th nowrap="nowrap">操作</th>
			</tr>
            <#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td style="display: none;">&nbsp;${item.id!""}</td>
					<td>&nbsp;<a href="http://${item.http!""}" target="_blank">${item.name!""}</a></td>
					<td>&nbsp;${item.http!""}</td>
					<td>&nbsp;${item.target!""}</td>
					<td>&nbsp;${item.position!""}</td>
					<td>&nbsp;${item.order1!""}</td>
					<td><a href="toEdit?id=${item.id}">编辑</a></td>
				</tr>
            </#list>

			<tr>
				<td colspan="71" style="text-align: center;">
                <#include "/manage/system/pager.ftl" ></td>
			</tr>
		</table>

	</form>
</@page.pageBase>