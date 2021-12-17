<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="到货通知">
<form action="${basepath}/manage/emailNotifyProduct" namespace="/manage" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td style="text-align: right;" nowrap="nowrap">账号</td>
				<td style="text-align: left;"><input type="text"  value="${e.account!""}" name="account"  class="search-query input-small"
						id="account" /></td>
				<td style="text-align: right;" nowrap="nowrap">接收到货的邮箱</td>
				<td style="text-align: left;"><input type="text"  value="${e.receiveEmail!""}" name="receiveEmail"  class="input-small"
						id="receiveEmail" /></td>
				<td style="text-align: right;" nowrap="nowrap">是否已发送通知</td>
				<td style="text-align: left;">

					<#assign map = {'':'','y':'是','n':'否'}>
                    <select id="status" name="status" class="input-medium">
						<#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
			</tr>
			<tr>
				<td colspan="28">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
							
					<button method="autoNotify" class="btn btn-success" onclick="submitNotValid2222(this)">
						<i class="icon-ok icon-white"></i> 触发通知
					</button>
					
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
		
		<div class="alert alert-info">
			发送失败3次的系统将不再尝试发送!
		</div>
		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">帐号</th>
				<th nowrap="nowrap">接收到货的邮箱</th>
				<th nowrap="nowrap">商品ID</th>
				<th nowrap="nowrap">商品名称</th>
				<th nowrap="nowrap">申请时间</th>
				<th nowrap="nowrap">通知时间</th>
				<th nowrap="nowrap">是否已发送通知</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">
						<a target="_blank" href="${basepath}/manage/account/show?account=${item.account!""}">${item.account!""}
						</a>
					</td>
					<td nowrap="nowrap">&nbsp;${item.receiveEmail!""}</td>
					<td nowrap="nowrap">&nbsp;
						<a href="${basepath}/manage/product/toEdit?id=${item.productID!""}" target="_blank">
							${item.productID!""}
						</a>
					</td>
					<td nowrap="nowrap">&nbsp;
						<a href="${systemSetting().www}/product/${item.productID!""}.html" target="_blank" title="${item.productName!""}">
						${item.productName!""}</a>
					</td>
					<td nowrap="nowrap">&nbsp;${item.createdate!""}</td>
					<td nowrap="nowrap">&nbsp;${item.notifydate!""}</td>
					<td nowrap="nowrap" style="text-align: center;">
						<#if item.status?? && item.status=="y">
							<img alt="显示" src="${basepath}/resource/images/action_check.gif">
						<#else>
							<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
							(失败:${item.sendFailureCount!""}次)
						</#if>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;"><#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$(function() {
	});
	function deleteSelect() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定删除选择的记录?");
	}
</script>
</@page.pageBase>