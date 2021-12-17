<#macro activity title queryType productList>
<style>

.fdsfsdf{
	-moz-box-shadow:5px 5px 5px #ccc;              
    -webkit-box-shadow:5px 5px 5px #ccc;           
    box-shadow:5px 5px 5px #ccc;    
}
</style>
<!-- 首页不同商品的展示 -->
<#if productList??>
	<div class="row" class="col-xs-12" style="padding: 5px;">
		<div class="alert alert-success" style="margin-bottom: 5px;margin-top: 5px;display: none;">
			<#if queryType=="r">
				<span class="glyphicon glyphicon-arrow-down"></span>
			<#elseif queryType=="d">
				<span class="glyphicon glyphicon-flash"></span>
			<#elseif queryType=="s">
				<span class="glyphicon glyphicon-plus"></span>
			</#if>
			&nbsp;${title!""}
<!-- 			</a> -->
		</div>
		<div class="page-header fdsfsdf" style="border-bottom: 2px solid #e33a3d;margin: 20px 0 20px;">
		  <h5>
		<#if queryType=="r">
				<span class="glyphicon glyphicon-arrow-down"></span>
		<#elseif queryType=="d">
				<span class="glyphicon glyphicon-flash"></span>
		<#elseif queryType=="s">
				<span class="glyphicon glyphicon-plus"></span>
			</#if>
		  <strong>${title!""}</strong></h5>
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
								<a style="cursor: pointer;" href="${basepath}/product/${item.id!''}.html" target="_blank"
								title="${item.name!""}">
									${item.name!""}
								</a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<#if queryType="d">
								<span class="badge pull-left">折扣价
									<b style="font-weight: bold;">
										￥${item.finalPrice!""}
									</b>
								</span>
							<#elseif queryType=="r">
								<span class="label ${item.expire?string("label-default","label-danger")}">促销价
									<b style="font-weight: bold;">
										￥${item.finalPrice!""}
									</b>
								</span>
							<#elseif queryType=="s">
								<b style="font-weight: bold;">
									￥${item.nowPrice!""}
								</b>
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
							
								<#if queryType=="d">
									还剩<div style="display: inline;" timer="activityEndDateTime" activityEndDateTime="${item.activityEndDateTime!""}"></div>
									<span class="badge pull-right" style="background-color:red;">${item.discountFormat!""}折</span>
								<#elseif queryType=="r">
									还剩<div style="display: inline;" timer="activityEndDateTime" activityEndDateTime="${item.activityEndDateTime!""}"></div>
								<#elseif queryType=="s">
									还剩<div style="display: inline;" timer="activityEndDateTime" activityEndDateTime="${item.activityEndDateTime!""}"></div>
									<span class="label label-success">双倍积分</span>
								</#if>
								
							</#if>
						</div>
					</div>
					
				</div>
			</div>
		</#list>
	</div>

<#else>
<!-- 	此活动暂未发布商品！敬请期待！ -->
</#if>
</#macro>