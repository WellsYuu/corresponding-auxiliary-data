<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="订单管理">
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
	function updateInBlackList() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定将选择的记录拉入新闻黑名单吗?");
	}
</script>
	<form action="${basepath}/manage/order" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td>订单号</td>
				<td><input type="text" value="${e.id!""}" name="id" class="search-query input-small"/></td>
				<td>订单状态</td>
				<td>
					<#assign map = {'':'','init':'未审核','pass':'已审核','send':'已发货','sign':'已签收','cancel':'已取消','file':'已归档'}>
                    <select id="status" name="status" class="search-query input-medium">
						<#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select></td>
				<td>用户账号</td>
				<td><input type="text"  value="${e.account!""}" name="account" class="search-query input-small"/></td>
			</tr>
			<tr>
                <td>支付状态</td>
                <td>
					<#assign map = {'':'','n':'未支付','y':'完全支付'}>
                    <select id="paystatus" name="paystatus" class="search-query input-medium">
						<#list map?keys as key>
                            <option value="${key}" <#if e.paystatus?? && e.paystatus==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
                </td>
                <td>时间范围</td>
                <td><input id="d4311" class="Wdate search-query input-small" style="width:80px" type="text" name="startDate"
                           value="${e.startDate!""}"
                           onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
                    ~
                    <input id="d4312" class="Wdate search-query input-small" style="width:80px" type="text" name="endDate"
                           value="${e.endDate!""}"
                           onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
                </td>
				<td>退款状态</td>
				<td colspan="21">
					<#assign map = {'':'','WAIT_SELLER_AGREE':'等待卖家同意退款','WAIT_BUYER_RETURN_GOODS':'卖家同意退款，等待买家退货','WAIT_SELLER_CONFIRM_GOODS':'买家已退货，等待卖家收到退货','REFUND_SUCCESS':'退款成功，交易关闭'}>
                    <select id="refundStatus" name="refundStatus" class="search-query input-medium">
						<#list map?keys as key>
                            <option value="${key}" <#if e.refundStatus?? && e.refundStatus==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select></td>
			</tr>
			<tr>
				<td colspan="14">
<#--<%-- 					<s:submit action="order" method="selectList" value="查询" cssClass="btn btn-primary" />  --%>-->
<#--<%-- 						<s:a method="selectList" cssClass="btn btn-primary"> --%>-->
<#--<!-- 							<i class="icon-search icon-white"></i> 查询 &ndash;&gt;-->
<#--<%-- 						</s:a> --%>-->
						<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
<#--<%-- 					<s:submit action="order"  method="importXls" value="导出" cssClass="btn btn-success" />  --%>-->
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
				
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th>订单号</th>
				<th>订单总金额</th>
				<th>商品总金额</th>
				<th>配送费</th>
				<th>数量</th>
				<th>会员</th>
<!-- 				<th>折扣</th> -->
				<th>创建日期</th>
				<th>订单状态</th>
				<th>支付状态</th>
				<th width="60px">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td>
						${item.id!""}
						<#if item.lowStocks?? && item.lowStocks=="y"><font color="red">【缺货】</font></#if>
					</td>
					<td>${item.amount!""}
						<#if item.updateAmount?? && item.updateAmount=="y"><font color="red">【修】</font></#if>
					</td>
					<td>${item.ptotal!""}</td>
					<td>${item.fee!""}</td>
					<td align="center">${item.quantity!""}</td>
					<td><a target="_blank" href="${basepath}/manage/account/show?account=${item.account!""}">${item.account!""}</a></td>
<#--<%-- 					<td>${item.rebate!""}</td> --%>-->
					<td>${item.createdate!""}</td>
					<td>${item.statusStr!""}
						<#if item.status?? && item.status=="cancel">
							<img src="${basepath}/resource/images/action_delete.gif">
						<#elseif  item.status?? && item.status=="file">
							<img src="${basepath}/resource/images/action_check.gif">
						<#elseif  item.status?? && item.status=="init">
							<img src="${basepath}/resource/images/action_add.gif">
						</#if>
					</td>
					<td>${item.paystatusStr!""}
						<#if item.paystatus?? && item.paystatus=="y">
							<img src="${basepath}/resource/images/action_check.gif">
						<#elseif  item.paystatus?? && item.paystatus=="n">
							<img src="${basepath}/resource/images/action_add.gif">
						</#if>
					</td>
					<td><a href="toEdit?id=${item.id}">编辑</a>|
					<a target="_blank" href="toPrint?id=${item.id}">打印</a>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="55" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
		
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			图标含义：<BR>
			<img alt="新增" src="${basepath}/resource/images/action_add.gif">：未审核、未支付
			<img alt="已上架" src="${basepath}/resource/images/action_check.gif">：已归档
			<img alt="已下架" src="${basepath}/resource/images/action_delete.gif">：已取消
		</div>

	</form>
</@page.pageBase>