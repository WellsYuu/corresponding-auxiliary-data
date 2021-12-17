<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
<style type="text/css">
.totalPayMonery{
	color: red;font-weight: bold;font-size:22px;
}
</style>
<div id="wrap">
	<@menu.menu selectMenu=""/>
	<form action="${basepath}/order/pay.html" method="post" theme="simple" onsubmit="return submitOrder();">
		<div class="container">
			<div class="row">
				<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
					<span class="label label-default" style="font-size:100%;">
						1.查看购物车
					</span>
					&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
					<span class="label label-success" style="font-size:100%;">
						2.确认订单信息
					</span>
					&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
					<span class="label label-default" style="font-size:100%;">
						3.确认收货
					</span>
					&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
					<span class="label label-default" style="font-size:100%;">
						4.评价
					</span>
				</div>
			</div>
			<hr style="margin-bottom: 5px;">
			
			<div class="panel panel-primary">
				<div class="panel-heading">订单确认</div>
				
				<ul class="list-group">
					<li class="list-group-item">
						<a id="addressTips" href="#" data-toggle="tooltip" title="请选择收货地址！">
							<span class="glyphicon glyphicon-user"></span>&nbsp;请选择收货地址
						</a>
					</li>
					<li class="list-group-item">
						<#if myCart?? && myCart.productList?? && myCart.productList?size gt 0>
						<#if myCart?? && myCart.addressList?? && myCart.addressList?size gt 0>
							<div class="row">
								<div class="col-xs-12" style="line-height: 20px;" id="adressListDiv">
									<!--
									<div class="alert alert-danger fade in">
								        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								        <strong>提示：</strong>请选择收货地址!
								      </div>
									 -->
									<#list myCart.addressList as item>
										<#if myCart.defaultAddessID?? && item.id == myCart.defaultAddessID>
											<div address="address" class="col-xs-3 alert alert-info" style="border: 1px solid;text-align: left;margin-right: 10px;width: 200px;line-height: 20px;cursor: pointer;">
												${item.name!""},${item.phone!""}
												<input type="radio" name="selectAddressID" checked="checked"  value="${item.id!""}"/>
												<br>
												${item.address!""}<br>
											</div>
										<#else>
											<div address="address" class="col-xs-3 alert" style="border: 1px solid;text-align: left;margin-right: 10px;width: 200px;line-height: 20px;cursor: pointer;">
												${item.name!""},${item.phone!""}
												<input type="radio" name="selectAddressID" value="${item.id!""}"/>
												<br>
												${item.address!""}<br>
											</div>
										</#if>
									</#list>
								</div>
							</div>
						<#else>
							<#if currentAccount()??>
								<div class="bs-callout bs-callout-danger author" style="text-align: left;font-size: 14px;margin: 2px 0px;">
									暂时还没有收货地址！<a style="text-decoration: underline;" href="${basepath}/account/address">点此设置</a>
								</div>
							</#if>
						</#if>
					</#if>
					</li>
					<li class="list-group-item">
						<a href="#" data-toggle="tooltip" title="请选择配送方式！" id="expressTips">
							<span class="glyphicon glyphicon-send"></span>&nbsp;请选择配送方式
						</a>
					</li>
					<li class="list-group-item">
						<div class="row">
							<div class="col-xs-12">
								<!-- 
								<div class="alert alert-danger fade in">
							        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
							        <strong>提示：</strong>请选择配送方式!
							      </div>
								 -->
								<table class="table table-bordered table-hover" id="expressTable">
									<#list expressList as item>
										<tr style="cursor: pointer;">
											<td width="400px">
											<input type="radio" name="expressCode" value="${item.code!""}" fee="${item.fee!""}"/>
											${item.name!""}</td>
											<td>${item.fee!""}</td>
										</tr>
									</#list>
								</table>
							</div>
						</div>
					</li>
					<li class="list-group-item"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;购买到的商品</li>
					<li class="list-group-item">
						<div class="row">
							<div class="col-xs-12">
								<table class="table table-bordered table-hover">
									<tr>
										<th width="400px">商品名称</th>
										<th >单价(元)</th>
										<th >数量</th>
			<!-- 							<th >优惠方式(元)</th> -->
										<th >小计(元)</th>
									</tr>
									<#list myCart.productList as item>
										<tr>
											<td style="display: none;">&nbsp;${item.id!""}</td>
											<td>&nbsp;<a target="_blank" href="${basepath}/product/${item.id!""}.html">${item.name!""}</a>
												<a name="stockErrorTips" productid="${item.id!""}" href="#" data-toggle="tooltip" title="" data-placement="right" data-original-title="商品库存不足20个，"></a>
												<#if item.exchangeScore!=0>
													<p>
														<span id="totalExchangeScoreSpan" class="label label-default">兑换积分:${item.exchangeScore!""}
														</span>
													</p>
												</#if>
											</td>
											<td>
												<#if item.totalExchangeScore!=0>
													<span style="text-decoration: line-through;">
												<#else>
													<span>
												</#if>
													${item.nowPrice!""}
												</span>
											</td>
											<td>
												${item.buyCount!""}
											</td>
											<td>&nbsp;${item.total0!""}</td>
										</tr>
									</#list>
								</table>
							</div>
						</div>
					</li>
				</ul>
				
				<div class="panel-footer primary" align="right">
					<div class="row">
						<div class="col-xs-12">
							<input id="productTotalMonery" type="hidden" value="${myCart.amount!""}"/>
							合计：<span class="totalPayMonery" id="totalPayMonery">${myCart.amount!""}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<#if myCart.totalExchangeScore!=0>
								<h4>所需积分：<span style="color: red;font-weight: bold;" id="totalExchangeScore">${myCart.totalExchangeScore!""}</span>
								</h4>
							</#if>
						</div>
					</div>
							
					<div class="row">
						<div class="col-xs-6">
							<input name="otherRequirement" class="form-control" placeholder="此处您可以输入您的附加要求，以便我们提供更好的服务。" size="50" maxlength="50"/>
						</div>
						<div class="col-xs-6">
							<button type="submit" class="btn btn-success" value="提交订单" id="confirmOrderBtn" disabled="disabled" data-placement="left" >
								<span class="glyphicon glyphicon-ok"></span>提交订单
							</button>
						</div>
					</div>
				</div>
			</div>			
			
		</div>
	</form>
</div>

<script type="text/javascript">
$(function() {
	$("div[address=address]").click(function(){
		$("div[address=address]").removeClass("alert-info");
		
		$(this).addClass("alert-info");
		$(this).find("input[type=radio]").attr("checked",true);
	});
	
	$("#expressTable tr").each(function(){
		var _tr = $(this);
		_tr.click(function(){
			var _radio = _tr.find("input[type=radio]");
			console.log("选中的快递费用为="+_radio.attr("fee"));
			_radio.attr("checked",true);
			var _totalPayMonery = parseFloat($("#productTotalMonery").val())+parseFloat(_radio.attr("fee"));
			$("#totalPayMonery").text(_totalPayMonery.toFixed(2));
			
			$('#expressTips').tooltip('hide');
		});
	});
	
	$("#confirmOrderBtn").removeAttr("disabled");
});

function submitOrder(){
	console.log("提交订单...");
	//console.log($("#adressListDiv").find(":checked").size());
	//console.log($("#expressTable").find(":checked").size());
	var submitFlg = true;
	if($("#adressListDiv").find(":checked").size()==0){
		$('#addressTips').tooltip('show');
		submitFlg =false;
	}else{
		$('#addressTips').tooltip('hide');
	}
	if($("#expressTable").find(":checked").size()==0){
		$('#expressTips').tooltip('show');
		submitFlg =false;
	}else{
		$('#expressTips').tooltip('hide');
	}
	

	console.log("提交订单...submitFlg= " + submitFlg);
	if(!submitFlg){
		return false; 
	}
	
	var score = ${currentAccount().score!0};
	var totalExchangeScore = ${myCart.totalExchangeScore!0};
	if(score < totalExchangeScore ){
	  $('#confirmOrderBtn').attr("data-content","可用积分不足").popover("toggle");
		return false;
	}
	//ajax验证待提交支付的商品库存数量是否存在超卖或下架之类的情况
	//tips
	//$("a[name=stockErrorTips]").tooltip('show');
	var aaa=checkStockLastTime();
	console.log("aaa="+aaa);
	if(!aaa){console.log("not ok");
		return false;
	}
	console.log("ok");
	return true;
}

</script>
</@html.htmlBase>