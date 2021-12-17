<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="订单管理">
<style>
.simpleOrderReport{
	font-weight: 700;font-size: 16px;color: #f50;
}
</style>
<form action="${basepath}/manage/order" method="post" theme="simple" name="form" id="form">
	<input type="hidden" value="${e.id!""}" name="id"/>
		<div id="buttons" style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
		<div id="updateMsg"><font color='red'>${e.updateMsg!""}</font></div>
			<#if e.paystatus??&&e.paystatus=="y">
				<#if e.status??&&e.status=="init">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn btn-primary"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
				<#elseif e.status??&&e.status=="pass">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn btn-primary"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
				<#elseif  e.status??&&e.status=="send">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" disabled="disabled"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
				<#elseif  e.status??&&e.status=="sign">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn btn-primary"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" disabled="disabled"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
				<#elseif e.status??&&e.status=="cancel">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn btn-primary" disabled="disabled"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" disabled="disabled"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
				<#elseif e.status??&&e.status=="file">
					<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
						value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
						value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
						value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
					<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
						value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" disabled="disabled"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
			</#if>
			<#elseif e.status??&&e.status=="cancel">
				<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
					value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
					value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
					value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
					value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
				
				<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
				<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
				<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);" disabled="disabled"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
			<#else>
				<a href="updateOrderStatus?id=${e.id!""}&status=pass" onclick="return onSubmit(this);"
					value="已审核" class="btn" disabled="disabled"><@i18n.message key="order_status_pass"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=send" onclick="return onSubmit(this);"
					value="已发货" class="btn" disabled="disabled"><@i18n.message key="order_status_send"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=sign" onclick="return onSubmit(this);"
					value="已签收" class="btn" disabled="disabled"><@i18n.message key="order_status_sign"/></a>
				<a href="updateOrderStatus?id=${e.id!""}&status=file" onclick="return onSubmit(this);"
					value="已归档" class="btn" disabled="disabled"><@i18n.message key="order_status_file"/></a>
				
				<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning"/>
				<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning"/>
				<a href="updateOrderStatus?id=${e.id!""}&status=cancel" onclick="return onSubmit(this);"
						value="取消订单" class="btn btn-danger"><@i18n.message key="order_status_cancel"/></a>
			</#if>
		</div>

	<div id="addPayDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>添加支付记录</h4>
				</td>
			</tr>
			<tr>
				<td>支付方式</td>
				<td>
					<select name="paymethod" id="paymethod">
						<option value="zfb">支付宝</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td >
					<input name="orderpay.payamount" id="payamount">
				</td>
				
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="remark" id="remark" value="后台添加"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" method="insertOrderpay" onclick="selectList(this);" class="btn btn-primary">确认</button>
					<input id="cancelPayBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="updatePayMoneryDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>修改订单总金额</h4>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td>
					<input name="amount" id="amount">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="updatePayMoneryRemark" id="updatePayMoneryRemark" placeholder="修改订单金额备注"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
			
					<button method="updatePayMonery" class="btn btn-primary" onclick="subFrom(this);">
						 确认
					</button>
					<input id="cancelUpdatePayMoneryBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">订单信息</a></li>
			<li><a href="#tabs-2">订单明细<#if e.lowStocks??&&e.lowStocks=="y"><font color="red">【缺货】</font></#if></a></li>
			<li><a href="#tabs-3">订单日志</a></li>
		</ul>
		<div id="tabs-1">
			<#if e.refundStatusStr??>
				<div class="alert alert-danger" style="margin-bottom: 2px;">
					<strong>退款状态：</strong>${e.refundStatusStr!""}(${e.refundStatus!""})
					<br>
					【请立刻去<a href="http://www.alipay.com" target="_blank">支付宝</a>处理此订单的退款事宜】
				</div>
			</#if>
			
			<div class="alert alert-info" style="margin-bottom: 2px;">
				<strong>订单信息</strong>
				<#if e.score gt 0>
					<span class="badge badge-success" style="margin-left:20px;">赠送${e.score!""}个积分点</span>
				</#if>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<#if e.amountExchangeScore gt 0>
					<span class="badge badge-default" style="margin-left:20px;">消耗掉会员${e.amountExchangeScore!""}个积分点</span>
				</#if>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<#if e.hasGift>
					<span class="badge badge-success" style="margin-left:20px;">【订单含赠品】</span>
				</#if>
			</div>
			<table class="table table-bordered">
				<tr>
					<th>订单编号</th>
					<th>数量</th>
					<th>创建日期</th>
					<th>订单状态</th>
					<th>支付状态</th>
					<th>订单总金额</th>
					<th>商品总金额</th>
					<th>总配送费</th>
<!-- 					<th>支付方式</th> -->
<!-- 					<th>订单备注</th> -->
					<th>收货人信息</th>
					<th>收货人地址</th>
					<th>物流信息</th>
				</tr>
				<tr>
					<td>&nbsp;${e.id!""}</td>
					<td>&nbsp;${e.quantity!""}</td>
					<td>&nbsp;${e.createdate!""}</td>
					<td>
						<#if e.status?? && (e.status=="sign" || e.status="file")>
							<span class="badge badge-success">${e.statusStr!""}</span>
						<#else>
							<span class="badge badge-important">${e.statusStr!""}</span>
						</#if>
					</td>
					<td>
						<#if e.paystatus??&&e.paystatus=="y">
							<span class="badge badge-success">${e.paystatusStr!""}</span>
						<#else>
							<span class="badge badge-important">${e.paystatusStr!""}</span>
						</#if>
					</td>
					<td>&nbsp;<span class="simpleOrderReport">${e.amount!""}</span>
						<#if e.updateAmount??&&e.updateAmount=="y"><font color="red">【修】</font></#if>
					</td>
					<td>&nbsp;${e.ptotal!""}</td>
					<td>&nbsp;${e.fee!""}</td>
<#--<%-- 					<td>&nbsp;${e.payType!""}</td> --%>-->
<#--<%-- 					<td>&nbsp;${e.remark!""}</td> --%>-->
					
					<td>
						姓名：${e.ordership.shipname!""}<br>
						性别：${e.ordership.sex!""}<br>
						手机：${e.ordership.phone!""}<br>
						座机：${e.ordership.tel!""}<br>
					</td>
					<td style="width: 200px;">
						省份：${e.ordership.province!""}<br>
						城市：${e.ordership.city!""}<br>
						区域：${e.ordership.area!""}<br>
						详细地址：${e.ordership.shipaddress!""}<br>
						邮编：${e.ordership.zip!""}<br>
						备注：${e.ordership.remark!""}<br>
						
						<#if e.status??&&e.status=="init">
							<a class="btn btn-primary" href="${basepath}/manage/order/selectOrdership?orderid=${e.id!""}">修改收货人配送信息</a>
						<#else>
							<a class="btn btn-primary" href="${basepath}/manage/order/selectOrdership?orderid=${e.id!""}" disabled="disabled" onclick="javascript:return false;">修改收货人配送信息</a>
						</#if>
					</td>
					<td style="width: 150px;">
						${e.expressCode!""}<br>
						${e.expressCompanyName!""}<br>
						物流单号：${e.expressNo!""}<br>
					</td>
				</tr>
				<#if e.otherRequirement??>
					<tr>
						<td colspan="20">附加要求:${e.otherRequirement!""}</td>
					</tr>
				</#if>
			</table>
			
			<div class="alert alert-success" style="margin-bottom: 2px;">
				<strong>订单支付记录</strong>
			</div>
			<table class="table table-bordered">
				<tr>
					<th width="50px">序号</th>
					<th nowrap="nowrap">商户订单号</th>
					<th nowrap="nowrap">支付方式</th>
					<th nowrap="nowrap">支付金额</th>
					<th nowrap="nowrap">支付时间</th>
					<th nowrap="nowrap">支付状态</th>
					<th nowrap="nowrap">支付宝交易号</th>
					<th nowrap="nowrap">备注</th>
				</tr>
				<#list e.orderpayList as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td>&nbsp;${item.id!""}</td>
						<td>${item.paymethod!""}
							<#if item.paymethod??&&item.paymethod=="n">
								<@KV.map key="orderpay_paymethod_n"/>
							<#elseif item.paymethod??&&item.paymethod=="y">
								<@KV.map key="orderpay_paymethod_y"/>
							</#if>
						</td>
						<td>&nbsp;${item.payamount!""}</td>
						<td>&nbsp;${item.createtime!""}</td>
						<td>
							<#if item.paystatus?? && item.paystatus=="n">
								<@KV.map key="orderpay_paystatus_n"/>
							<#elseif item.paystatus?? && item.paystatus=="y">
								<span class="badge badge-success"><@KV.map key="orderpay_paystatus_y"/></span>
							</#if>
						</td>
						<td>&nbsp;${item.tradeNo!""}</td>
						<td>&nbsp;${item.remark!""}</td>
					</tr>
				</#list>
			</table>
			
		</div>
		
		<div id="tabs-2">
			<table class="table table-bordered">
				<tr style="background-color: #dff0d8">
					<th width="50px">序号</th>
					<th>商品编号</th>
					<th>商品名称</th>
					<th>购买的商品规格</th>
<!-- 					<th>赠送积分</th> -->
					<th>数量</th>
					<th>单价</th>
<!-- 					<th>配送费</th> -->
					<th>小计</th>
				</tr>
				<#list e.orderdetail as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td nowrap="nowrap">
							<a target="_blank" style="text-decoration: underline;" href="${basepath}/manage/product/toEdit?id=${item.productID!""}">
								${item.productID!""}
							</a>
							<#if item.lowStocks?? && item.lowStocks=="y"><font color="red">【缺货】</font></#if>
						</td>
						<td><a target="_blank" style="text-decoration: underline;" href="${systemSetting().www}/product/${item.productID!""}.html">${item.productName!""}</a>
							<br>
							<#if item.giftID??>
								<a target="_blank" style="text-decoration: underline;" href="${basepath}/manage/gift/show?id=${item.giftID!""}">
									【查看赠品】
								</a>
							</#if>
						</td>
						<td>&nbsp;${item.specInfo!""}</td>
<#--<%-- 						<td>&nbsp;${item.score!""}</td> --%>-->
						<td>&nbsp;${item.number!""}</td>
						<td>&nbsp;￥${item.price!""}</td>
<#--<%-- 						<td>&nbsp;￥${item.fee!""}</td> --%>-->
						<td>&nbsp;￥${item.total0!""}</td>
						</td>
					</tr>
				</#list>
			</table>
		</div>
		
		<div id="tabs-3">
			<table class="table table-bordered">
				<tr style="background-color: #dff0d8">
					<th width="50px">序号</th>
					<th>操作人</th>
					<th>操作人类型</th>
					<th>时间</th>
					<th>日志</th>
				</tr>
				<#list e.orderlogs as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td nowrap="nowrap">&nbsp;
							<#if item.accountType??&&item.accountType=="w">
								<a target="_blank" href="${basepath}/manage/account/show?account=${item.account!""}">${item.account!""}</a>
							<#elseif item.accountType??&&item.accountType=="m">
								<a target="_blank" href="${basepath}/manage/user/show?account=${item.account!""}">${item.account!""}</a>
							<#elseif item.accountType??&&item.accountType=="p">
								第三方支付系统
							<#else>
								未知
							</#if>
						</td>
						<td>&nbsp;
							<#if item.accountType??&&item.accountType=="w">
								客户
							<#elseif item.accountType??&&item.accountType=="m">
								${systemSetting().systemCode}(系统)
							<#elseif item.accountType??&&item.accountType=="p">
								支付宝
							<#else>
								未知
							</#if>
						</td>
						<td>&nbsp;${item.createdate!""}</td>
						<td>&nbsp;${item.content!""}</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</form>

<script>
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	$("#cancelPayBtn").click(function(){
		$("#addPayDiv").slideUp();
		$("#addPayBtn").show();
		//$("#buttons").find("input[type=button]").each(function(){
			//$(this).attr("disabled","");
		//});
		return false;
	});
	$("#cancelUpdatePayMoneryBtn").click(function(){
		$("#updatePayMoneryDiv").slideUp();
		$("#updatePayMoneryBtn").show();
		return false;
	});
});
function addPayFunc(){
	$("#addPayDiv").slideDown();
	$("#addPayBtn").hide();
	//$("#buttons").find("input[type=button]").each(function(){
		//$(this).attr("disabled","disabled");
	//});
	return false;
}
function updatePayMoneryFunc(){
	$("#updatePayMoneryDiv").slideDown();
	$("#updatePayMoneryBtn").hide();
	return false;
}
function onSubmit(obj){
	//if(confirm("确认本次操作?")){
		//document.form1.action = "order!"+$(obj).attr("method")+".action";
		//document.form1.submit();
	//}
	if($(obj).attr("disabled")=='disabled'){
		return false;
	}
	return confirm("确认本次操作?");
}

//查询
function subFrom(obj){
	var payamount = $("#payamount").val();
	if(payamount=="" || payamount == null) {
		$("#payamount").val(0);
	}
	var _form = $("form");
	_form.attr("action",$(obj).attr("method"));
	_form.submit();
}
</script>

</@page.pageBase>