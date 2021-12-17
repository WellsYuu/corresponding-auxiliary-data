
<!-- 首页不同商品的展示 -->
<#macro indexProduct queryType productList title>
	<div class="row" class="col-xs-12" style="padding: 5px;">
		<div class="alert alert-info" style="margin-bottom: 5px;margin-top: 5px;">
			${title}
			<a href="${basepath}/special/${queryType}.html" target="_blank">
				<span style="float:right">[更多]</span>
			</a>
		</div>
	</div>
<div class="row" style="border:0px solid red;">
		<#list productList as item>
			<div class="col-xs-3" style="padding: 5px;text-align: center;">
				<div class="thumbnail" style="width: 100%; display: block;margin-bottom: 10px;">
					<div style="height: 200px;border: 0px solid;">
						<a href="${basepath}/product/${item.id}.html" target="_blank">
							
							<img class="lazy err-product22" style="border: 0px;display: block;margin: auto;max-height: 100%;max-width: 100;"  
							border="0" src="${systemSetting().defaultProductImg!""}"
							data-original="${systemSetting().imageRootPath!""}/${item.picture!""}">
						</a>
					</div>
					<div style="height: 40px;">
						<div class="col-xs-12 left_product">
							<div class="row">
								<a style="cursor: pointer;" href="${basepath}/product/${item.id}.html" target="_blank"
								title="${item.name!""}">
									${item.name!""}
								</a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<b style="font-weight: bold;color: #cc0000;">
								￥${item.nowPrice!"0.00"}
							</b>
						</div>
						<div class="col-xs-6">
							<b style="text-decoration: line-through;font-weight: normal;font-size: 11px;color: #a5a5a5;">
								￥${item.price!"0.00"}
							</b>
						</div>
					</div>
				</div>
			</div>
		</#list>
	</div>

</#macro>
