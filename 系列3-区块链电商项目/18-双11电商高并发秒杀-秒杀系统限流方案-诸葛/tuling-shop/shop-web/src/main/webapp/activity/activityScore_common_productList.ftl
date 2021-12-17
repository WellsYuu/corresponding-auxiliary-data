<style>

.fdsfsdf{
	-moz-box-shadow:5px 5px 5px #ccc;              
    -webkit-box-shadow:5px 5px 5px #ccc;           
    box-shadow:5px 5px 5px #ccc;    
/*     border: 1px solid #ccc; */
}
</style>
<!-- 首页不同商品的展示 -->
<#if productList??>

	<div class="row" class="col-xs-12" style="padding: 5px;">
		<div class="alert alert-success" style="margin-bottom: 5px;margin-top: 5px;display: none;">
			<#if true>
				<span class="glyphicon glyphicon-time"></span>
			</#if>
			${"积分商城"}
<!-- 			</a> -->
		</div>
		
		<div class="page-header fdsfsdf" style="border-bottom: 2px solid #e33a3d;margin: 20px 0 20px;">
		  <h5>
		  <#if true>
				<span class="glyphicon glyphicon-time"></span>
			</#if>
		  <strong>积分商城</strong></h5>
		</div>
		
	</div>
<div class="row" style="border:0px solid red;">
	<#list productList as item>
			<div class="col-xs-3" style="padding: 5px;text-align: center;">
				<div class="thumbnail" style="width: 100%; display: block;margin-bottom: 10px;">
					<div style="height: 200px;border: 0px solid;">
						<a href="${basepath}/product/${item.id!""}.html" target="_blank">
							<img class="lazy" style="border: 0px;display: block;margin: auto;max-height: 100%;max-width: 100%;"  
							border="0" src="${systemSetting().defaultProductImg}"
							data-original="${systemSetting().imageRootPath}${item.picture!""}">
						</a>
					</div>
					<div style="height: 40px;">
						<div class="col-xs-12 left_product">
							<div class="row">
								<a style="cursor: pointer;" href="${basepath}/product/${item.id!""}.html" target="_blank"
								title="${item.name!""}">
									${item.name!""}
								</a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<#if true>
								<#if item.expire>
									<span class="label label-default">兑换积分:
										<b style="font-weight: bold;">
											${item.exchangeScore!""}
										</b>
									</span>
								<#else>
									<span class="label label-danger">兑换积分:
										<b style="font-weight: bold;">
											${item.exchangeScore!""}
										</b>
									</span>
								</#if>
							</#if>
						</div>
						<div class="col-xs-6" style="text-align: right;">
							<b style="text-decoration: line-through;font-weight: normal;font-size: 11px;color: #a5a5a5;">
								￥${item.price!""}
							</b>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12">
							<#if item.expire>
								活动已到期
							<#else>
							
								还剩<div style="display: inline;" timer="activityEndDateTime" activityEndDateTime="${item.activityEndDateTime!""}"></div>
								
							</#if>
						</div>
					</div>
					
				</div>
			</div>
	</#list>
	</div>

</#if>