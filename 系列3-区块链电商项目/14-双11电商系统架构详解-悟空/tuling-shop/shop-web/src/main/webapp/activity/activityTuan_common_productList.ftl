<#if productList??>
<!-- 首页不同商品的展示 -->
	<div class="row" class="col-xs-12" style="padding: 5px;">
		<div class="alert alert-success" style="margin-bottom: 5px;margin-top: 5px;">
			团购活动
<!-- 			</a> -->
		</div>
	</div>
<div class="row" style="border:0px solid red;">
		<#list productList as item>
			<div class="col-xs-3" style="padding: 5px;text-align: center;">
				<div class="thumbnail" style="width: 100%; display: block;margin-bottom: 10px;">
					<div style="height: 200px;border: 0px solid;">
						<a href="${basepath}/product/<s:property escape="false" value="id" />.html" target="_blank">
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
									<span class="label label-default">团购价:
										<b style="font-weight: bold;">
											${item.tuanPrice!""}
										</b>
									</span>
								<#else>
									<span class="label label-danger">团购价:
										<b style="font-weight: bold;">
											${item.tuanPrice!""}
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
					
					<!-- 参与的团购人数 -->
					<#if item.hasBuyGroupPerson??&&item.hasBuyGroupPerson!="0">
						<div class="row">
							<div class="col-xs-12">
								${item.hasBuyGroupPerson!""}人团购
							</div>
						</div>
					</#if>
					
				</div>
			</div>
		</#list>
	</div>

</#if>