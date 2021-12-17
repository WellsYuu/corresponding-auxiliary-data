<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
	<@menu.menu selectMenu=""/>
	<form action="#" method="post" theme="simple">
		<div class="container" style="min-height: 200px;">
			<div class="row">
				<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
					<span class="label label-success" style="font-size:100%;">
						1.查看购物车
					</span>
					&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
					<span class="label label-default" style="font-size:100%;">
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
			<hr>
			<div class="row">
				<div class="col-xs-12" style="border: 0px solid;text-align: left;">
					<#if productList?? && productList?size gt 0>
						<h3 style="font-weight: bold;">购物车中的商品</h3>
						<hr style="margin: 0px;margin-bottom: 10px;">
						<table id="result_table" class="table table-bordered">
							<tr style="background-color: #dff0d8">
								<th width="400px">商品名称</th>
								<th >单价(元)</th>
								<th >数量</th>
	<!-- 							<th >优惠方式(元)</th> -->
								<th >小计(元)</th>
								<th >操作</th>
							</tr>
							<#list productList as item>
								<tr>
									<td style="display: none;">&nbsp;${item.id!""}</td>
									<td>&nbsp;<a target="_blank" href="${basepath}/product/${item.id!""}.html">${item.name!""}</a>
										<#if item.exchangeScore!=0>
											<p>
												<span id="totalExchangeScoreSpan" class="label label-default">兑换积分:${item.exchangeScore!""}
												</span>
											</p>
										</#if>
										
										<#if item.buySpecInfo??>
											<br>商品规格：${item.buySpecInfo.specColor!""},
											${item.buySpecInfo.specSize!""}
										</#if>
									</td>
									<td>&nbsp;
										<#if item.exchangeScore!=0>
											<span style="text-decoration: line-through;">
										<#else>
											<span>
										</#if>
											${item.nowPrice!""}
										</span>
									</td>
									<td>
										<span onclick="subFunc(this,true)" style="cursor: pointer;"><img src="${basepath}/resource/images/minimize.png" style="vertical-align: middle;"/></span>
										<input style="text-align: center;" name="inputBuyNum" value="${item.buyCount!""}" size="4" maxlength="4" pid="${item.id!""}"/>
<!-- 										<a name="stockErrorTips" href="#" data-toggle="tooltip" data-placement="right" title=""></a> -->
										
										<a name="stockErrorTips" productid="${item.id!""}" href="#" data-toggle="tooltip" title="" data-placement="right" ></a>
										
										<span onclick="addFunc(this,true)" style="cursor: pointer;"><img src="${basepath}/resource/images/maximize.png" style="vertical-align: middle;"/></span>
									</td>
									<td total0="total0">&nbsp;
										${item.total0!""}
									</td>
									<td><a href="#" onclick="javascript:deleteFromCart('${item.id!""}')">删除</a></td>
								</tr>
							</#list>
						</table>
					
						<div style="border: 0px solid;float: right;margin-right: 20px;">
							<div class="row">
								<h4>合计：<span style="color: red;font-weight: bold;" id="totalPayMonery">${cartInfo.amount!""}</span>
								</h4>
							</div>
							
							<div class="row">
								<#if cartInfo.totalExchangeScore!=0>
									<h4>所需积分：<span style="color: red;font-weight: bold;" id="totalExchangeScore">${cartInfo.totalExchangeScore!""}</span>
									</h4>
								</#if>
							</div>
							
							<div class="row">
								<a href="${basepath}/order/confirmOrder.html" data-toggle="show" data-placement="top" class="btn btn-success" id="confirmOrderBtn" onclick="return confirmOrder()" disabled="disabled">
									<span class="glyphicon glyphicon-ok"></span>提交订单
								</a>
							</div>
						</div>
					<#else>
<!-- 						<div class="alert alert-info">您的购物车是空的，立即去商城逛逛...</div> -->
						<div class="bs-callout bs-callout-danger author" style="text-align: left;font-size: 22px;margin: 2px 0px;">
							<span class="glyphicon glyphicon-info-sign"></span>&nbsp;您的购物车是空的，赶紧去看看有什么好宝贝吧...
						</div>
					</#if>
					
				</div>
			</div>
		</div>
	</form>
<form action="${basepath}/cart/delete" method="POST" id="formDelete">
	<input type="hidden" name="id">
</form>
<script src="${basepath}/resource/js/product.js"></script>
<script type="text/javascript">
$(function() {
	$("div[address=address]").click(function(){
		$("div[address=address]").removeClass("alert-info");
		
		$(this).addClass("alert-info");
		$(this).find("input[type=radio]").attr("checked",true);
	});
	
	$("#confirmOrderBtn").removeAttr("disabled");
});
function deleteFromCart(productId){
	if(productId){
		$("#formDelete :hidden[name=id]").val(productId);
		$("#formDelete").submit();
	}
}

//提交订单事件
function confirmOrder(){
	var submitFlg = true;
	
	//如果存在错误，则直接抖动
	$("input[name='inputBuyNum']").each(function(){
		var _tips_obj = $(this).parent().find("a[name=stockErrorTips]");
		//if(_tips_obj.is(":visible")){
		console.log(_tips_obj.attr("data-original-title"));
		var _tipsTitle = _tips_obj.attr("data-original-title");
		if(_tipsTitle && _tipsTitle!=''){
			_tips_obj.tooltip('show');
			submitFlg = false;
		}
	});
	console.log("submitFlg="+submitFlg);
	
	//ajax检查购物车中商品的数量是否合法
	var aaa=checkStockLastTime();
	console.log("aaa="+aaa);
	if(!aaa){console.log("not ok");
		return false;
	}
	//submitFlg = false;
	return submitFlg;
}
	

</script>
</@html.htmlBase>