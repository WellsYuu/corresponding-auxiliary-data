<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
<style type="text/css">
.centerImageCss{
	width: 560px;
	height: 180px;
}
.simpleOrderReport{
font-weight: 700;font-size: 16px;color: #f50;
}
</style>
<@menu.menu selectMenu=""/>
	<div class="container">
		<div class="row">
			<div class="col-xs-3">
				<@accountMenu.accountMenu currentMenu="orders"/>
			</div>
			
			<div class="col-xs-9">
				<div class="row">
					<#if pager.list?? && pager.pagerSize gt 0>
						<div class="panel panel-default">
							<div class="panel-heading"><span class="glyphicon glyphicon-th"></span>我的订单列表</div>
							<div class="panel-body">
								<#if orderSimpleReport??>
									<#if orderSimpleReport.orderCompleteCount!=0>
										<span class="glyphicon glyphicon-ok"></span>&nbsp;<span class="simpleOrderReport">${orderSimpleReport.orderCompleteCount!""}</span>个交易完成.
									</#if>
									<#if orderSimpleReport.orderCancelCount!=0>
										<span class="glyphicon glyphicon-remove"></span>&nbsp;<span class="simpleOrderReport">${orderSimpleReport.orderCancelCount!""}</span>个取消.
									</#if>
									<#if orderSimpleReport.orderWaitPayCount!=0>
										<span class="glyphicon glyphicon-time"></span>&nbsp;<span class="simpleOrderReport">${orderSimpleReport.orderWaitPayCount!""}</span>个等待付款.
									</#if>
								<#else>
									无任何订单数据！
								</#if>
							</div>
							<table class="table">
<!-- 							<table class="table table-bordered"> -->
								<tr>
									<td colspan="21"><span class="text-primary">【确认收货】操作只能登陆支付宝来进行。</span></td>
								</tr>
								<tr>
									<th style="text-align: left;">
	<!-- 									<input type="checkbox" id="firstCheckbox" />&nbsp;&nbsp; -->
										商品</th>
									<th style="text-align: center;" nowrap="nowrap">数量</th>
									<th style="text-align: center;" nowrap="nowrap">单价</th>
									<th style="text-align: center;" nowrap="nowrap">订单状态</th>
									<th style="text-align: center;width: 100px;">操作</th>
								</tr>
								<#list pager.list as item>
									<tr class="warning" id="del${item.id!""}">
										<td colspan="11">
											<div class="row">
												<div class="col-xs-3">
	<#--<%-- 												<input type="checkbox" name="ids" value="${item.id!""}" /> --%>-->
													订单号:${item.id!""}
												</div>
												<div class="col-xs-3">
													成交时间:${item.createdate!""}
												</div>
												<div class="col-xs-3">
													合计:<b class="simpleOrderReport">${item.amount!""}</b>
												</div>
												<div class="col-xs-3">
													<#if item.score!=0>
														<span class="label label-default">获赠：${item.score!""}个积分</span>
													</#if>
												</div>
											</div>
										</td>
									</tr>
									<#list item.orders as item>
										<tr id="delete${item.id!""}">
											<td>&nbsp;
												<div style="width:50px;height: 50px;border: 0px solid;float: left;margin-left: 20px;">
													<a href="${basepath}/product/${item.productID!""}.html" target="_blank" title="${item.productName!""}">
														<img style="width: 100%;height: 100%;border: 0px;" alt="" src="${systemSetting().imageRootPath}${item.picture!""}" onerror="nofind()"/>
													</a>
												</div>
												<div style="float: left;">&nbsp;${item.productName!""}</div>
											</td>
											<td style="text-align: center;">&nbsp;${item.productNumber!""}</td>
											<td style="text-align: center;">&nbsp;${item.price!""}</td>
											<#if item_index == 0>
												<td style="text-align: center;border-left:1px solid #ddd;vertical-align: middle;" rowspan="${item.quantity!""}">
													
													<#if item.paystatus?? && item.paystatus=="y">
														<#if item.status?? && item.status =="init">
															等待发货
														<#elseif item.status?? && item.status =="pass">
															等待发货
														<#elseif item.status?? && item.status =="send">
															已发货
														<#elseif item.status?? && item.status =="sign">
															已签收
														<#else>
															交易完成
														</#if>
													<#elseif item.status?? && item.status =="cancel">
														已取消
													<#else>
														等待付款
													</#if>
													<br>
													<#if item.status?? && item.status =="file">
														<#if item.isComment?? && item.isComment =="y">
															已评价<br>
														</#if>
													</#if>
												</td>
											</#if>
											
											
											<#if item_index == 0>
												<td style="text-align: center;border-left:1px solid #ddd;vertical-align: middle;" rowspan="${item.quantity!""}">

												<#if item.paystatus?? && item.paystatus=="y">
													<#if item.status?? && item.status =="init">
                                                        <!-- 等待发货 -->
													<#elseif item.status?? && item.status =="pass">
                                                        <!-- 等待发货 -->
													<#elseif item.status?? && item.status =="send">
                                                        <a target="_blank" href="http://www.alipay.com" class="btn btn-primary btn-sm">确认收货</a>
													<#elseif item.status?? && item.status =="sign">
                                                        <!-- 已签收 -->
													<#else>
                                                        <!-- 交易完成 -->
													</#if>
												<#elseif item.status?? && item.status =="cancel">
                                                    <!-- 已取消 -->
												<#else>
                                                   	<a target="_blank" href="${basepath}/order/toPay?id=${item.id!""}" class="btn btn-danger btn-sm">付款</a>
												</#if>
                                                <br>
                                                <#if item.status?? && (item.status =="cancel" || item.status=="file")>
													<#if item.closedComment?? && item.closedComment =="y">
													<!-- 已评价 -->
													<#else>
														<a target="_blank" href="${basepath}/order/rate?orderid=${item.id!""}" class="btn btn-danger btn-sm">我来评价</a><br>
													</#if>
                                                </#if>
                                                <a target="_blank" href="${basepath}/order/${item.id!""}">订单详情</a>
                                                <br>
                                                <#if item.status?? && (item.status !="send" &&item.status !="sign")>
                                               	 <a  href="javascript:void(0)" onclick="return deleteOrder(${item.id!""})">删除订单</a>
                                               </#if>
                                             <#--   href="${basepath}/order/deletes?ids=${item.id!""}"-->
												<br>
                                                <#if item.status?? && (item.status =="send" || item.status=="sign")>
                                                <a target="_blank" href="http://www.kuaidi100.com/chaxun?com=${item.expressCompanyName!""}&nu=${item.expressNo!""}">物流详情</a>
                                                </#if>
												</td>
											</#if>
										</tr>
									</#list>
								</#list><#--pager.list-->
							</table>
						</div>
						
						<div style="text-align: right;">
							<#include "/pager.ftl"/>
						</div>
					<#else>
						<!-- 订单列表为空 -->
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<ol class="breadcrumb">
									  <li class="active">我的订单</li>
									</ol>
								</div>
							</div>
							
							<hr>
							
							<div class="row">
								<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
									<div class="panel panel-default">
							              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
								              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
								              		<span class="glyphicon glyphicon-user"></span>亲，你还没有任何订单信息！赶紧去下个单吧。
								              </div>
							              </div>
									</div>
									<hr>
								</div>
							</div>
							
						</div>
			
						<!--  
							<div class="bs-callout bs-callout-danger author" 
							style="text-align: left;font-size: 34px;margin: 2px 0px;vertical-align: middle;
							margin: auto;margin-top:50px;">
								<span class="glyphicon glyphicon-exclamation-sign" style="font-size: 34px;"></span>
								<span style="font-size: 16px;">还没有任何订单信息！赶紧去下个单吧。</span>
							</div>-->
					</#if>
				</div>
			</div>
		</div>
	</div>
	<script>
	function deleteOrder(orderid){
	if(confirm("确认删除该订单？")){
		<#if RequestParameters["pager.offset"]?exists>
		window.location.href="${basepath}/order/deletes?ids="+orderid+"&pager.offset=${RequestParameters["pager.offset"]}";
		<#else>
		window.location.href="${basepath}/order/deletes?ids="+orderid;
		</#if>
	}
	return false;
	}
	</script>
</@html.htmlBase>