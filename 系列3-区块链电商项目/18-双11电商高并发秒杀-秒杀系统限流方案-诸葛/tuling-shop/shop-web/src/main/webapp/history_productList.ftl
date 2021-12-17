<!-- 浏览过的商品历史列表，仅限于当前session中存储 -->
<div class="row" >
	<h4 class="topCss">浏览过的商品</h4>
</div>
<#if history_product_map??>
<#list history_product_map?values as item>
	<div class="row">
		<div class="col-xs-3">
			<a href="${basepath}/product/${item.id!""}.html" target="_blank" title="${item.name!""}">
				<img class="lazy" style="border: 0px;display: block;margin: auto;width: 50px;height: 50px;" 
									src="${systemSetting().defaultProductImg}"
									data-original="${systemSetting().imageRootPath}${item.picture!""}" />
			</a>
		</div>
		<div class="col-xs-9">
			<h4>
				<div class="left_product">
					<a title="${item.name!""}" href="${basepath}/product/${item.id!""}.html" target="_blank">
						${item.name!""}
					</a>
				</div>
			</h4>
			<div class="row">
				<div class="col-xs-6">
					<b style="font-weight: bold;color: #cc0000;">
						￥${item.nowPrice!""}
					</b>
				</div>
				<div class="col-xs-6">
					<b style="text-decoration: line-through;font-weight: normal;font-size: 11px;color: #a5a5a5;">
						￥${item.price!""}
					</b>
				</div>
			</div>
		</div>
	</div>
	<br>
</#list>
</#if>