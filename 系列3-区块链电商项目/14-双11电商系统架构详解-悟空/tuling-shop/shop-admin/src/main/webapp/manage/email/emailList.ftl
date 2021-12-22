<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="发送邮件列表">
	<form action="${basepath}/manage/email" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td style="text-align: right;" nowrap="nowrap">账号</td>
				<td style="text-align: left;"><input type="text"  value="${e.account!""}" name="account"  class="search-query input-small"
						id="account" /></td>
				<td style="text-align: right;" nowrap="nowrap">发送状态</td>
				<td style="text-align: left;">

                    <#assign map = {'':'','y':'发送成功','n':'发送失败'}>
                    <select id="sendStatus" name="sendStatus" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if e.sendStatus?? && e.sendStatus==key>selected="selected" </#if>>${map[key]}</option>
                        </#list>
                    </select>
				</td>
			</tr>
			<tr>
				<td colspan="28">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
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
				<th nowrap="nowrap">帐号</th>
				<th nowrap="nowrap">模板名称</th>
				<th nowrap="nowrap">创建时间</th>
				<th nowrap="nowrap">发送状态</th>
				<th nowrap="nowrap">链接状态</th>
<!-- 				<th nowrap="nowrap">url</th> -->
<!-- 				<th style="width: 115px;">操作</th> -->
			</tr>
            <#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;
						<a target="_blank" href="${basepath}/manage/account/show?account=${item.account}">${item.account!""}
						</a>
					</td>
					<td nowrap="nowrap">&nbsp;
						${item.notifyTemplateName!""}
					</td>
					<td nowrap="nowrap">&nbsp;${item.createdate!""}</td>
					<td nowrap="nowrap">&nbsp;
						<#if item.sendStatus?? && item.sendStatus=="y">
							<img alt="发送成功" src="${basepath}/resource/images/action_check.gif">发送成功
						<#elseif item.sendStatus?? && item.sendStatus=="n">
							<img alt="发送失败" src="${basepath}/resource/images/action_delete.gif">发送失败
						<#else>
							发送中...
						</#if>
					</td>
					<td nowrap="nowrap">&nbsp;
						<#if item.status?? && item.status=="y">
							<img alt="已失效" src="${basepath}/resource/images/action_delete.gif">已失效
						<#else>
							<img alt="未失效" src="${basepath}/resource/images/action_check.gif">未失效
						</#if>
					</td>
				</tr>
            </#list>
			<tr>
				<td colspan="16" style="text-align: center;">
                    <#include "/manage/system/pager.ftl"></td>
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