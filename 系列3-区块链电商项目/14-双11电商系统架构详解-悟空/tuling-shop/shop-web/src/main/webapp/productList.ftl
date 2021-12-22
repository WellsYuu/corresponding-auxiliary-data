<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
<style type="text/css">
img{border: 0px;}

.thumbnail_css{
	border-color: #f40;
}
.attr_css{
	font-size: 100%;
	float: left;
}
.left_product{
	font-size: 12px;max-height: 35px;overflow: hidden;text-overflow: ellipsis;-o-text-overflow: ellipsis;
}

.left_title {
	display: block;
	/* width: 280px; */
	overflow: hidden;
	white-space: nowrap;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}

img.err-product {
<#if systemSetting().defaultProductImg??>
background: url(${systemSetting().defaultProductImg}) no-repeat 50% 50%;
</#if>


.lazy {
  display: none;
}

</style>
<script type="text/javascript">
function defaultProductImg(){ 
	var img=event.srcElement; 
	img.src="${systemSetting().defaultProductImg}";
	img.onerror=null; //控制不要一直跳动 
}
</script>
	<@menu.menu selectMenu=mainCatalogCode/>

	<div class="container">
	
		<div class="row">
			<div class="col-xs-3" style="margin-top: -15px;">
				<#include "/catalog_superMenu.ftl">
				</br>
				<#include "/productlist_left_picScroll.ftl">
			</div>
			<div class="col-xs-9">
				<!-- 导航栏 -->
				<div class="row">
					<#if e.mainCatalogName??>
						<div style="border: 0px solid;text-align: left;">
							<div>
								<ol class="breadcrumb" style="margin-bottom: 0px;">
								  <li class="active"><a href="${basepath}/catalog/${mainCatalogCode}.html">${e.mainCatalogName!""}</a></li>
								  <#if e.childrenCatalogName??>
									  <li class="active"><a href="#">${e.childrenCatalogName!""}</a></li>
								  </#if>
								</ol>
							</div>
						</div>
					</#if>
				</div>
				
				<!-- 条件搜索栏 -->
				<div class="row" style="margin: 10px 0px;">
					<div class="col-xs-12">
						<#if catalogChildren??>
							<div>
								<span style="margin:5px;font-weight: bold;">分类</span>
								<#list catalogChildren as item>
									<span class="label ${(catalogCode?? && item.code==catalogCode)?string("label-success","label-info2")}" style="margin:5px;font-size:100%;">
										<a href="${basepath}/catalog/${item.code!""}.html">${item.name!""}</a>
									</span>
								</#list>
							</div>
						</#if>
						
						<#if attrs??>
							
							<div class="panel panel-default" style="margin:10px 0px;">
					              <div class="panel-body" style="font-weight: normal;text-align: center;">
					              	  
<#--<%-- 					              	    <span style="margin:5px;font-weight: bold;">属性</span> --%>-->
										<div style="padding-left: 0px;">
											<#list attrs as item>
												<div class="row" style="margin-bottom: 5px;">
													<div class="col-xs-2" style="text-align: right;">
														${item.name!""}：
													</div>
													<div class="col-xs-10" style="text-align: left;margin-left: -20px;">
														<#if item.attrList??>
														<#list item.attrList as item>
															<#if e.attrID??&&item.id==e.attrID>
																<span class="label label-success attr_css">
																		<a href="${basepath}/catalog/attr/${item.id!""}.html?orderBy=${item.orderBy!0}">${item.name!""}</a>
																</span>
															<#else>
																<span class="label label-info2 attr_css">
																		<a href="${basepath}/catalog/attr/${item.id!""}.html?orderBy=${item.orderBy!0}">${item.name!""}</a>
																</span>
															</#if>
														</#list>
														</#if>
														<br>
													</div>
												</div>
											</#list>
					              	  
						              </div>
					              </div>
							</div>
								
							
						</#if>
					</div>
				</div>
		
				<!-- 排序栏 -->
				<#if productList?? && productList?size gt 0>
					<div class="row" style="margin: 0px;">
						<div class="col-xs-12">
							<span class="attr_css" style="margin:5px;font-weight: bold;">排序</span>
								<span class="label ${(e.orderBy??&&e.orderBy==1)?string('label-success','')} attr_css" style="margin:5px;">
									<a href="${basepath}/catalog/${catalogCode!""}.html?orderBy=1&attrID=${e.attrID!""}">热门</a>
								</span>

								<span class="label ${(e.orderBy??&&e.orderBy==2)?string('label-success','')}  attr_css" style="margin:5px;">
									<a href="${basepath}/catalog/${catalogCode!""}.html?orderBy=2&attrID=${e.attrID!""}">价格</a>
								</span>

								<span class="label ${(e.orderBy??&&e.orderBy==3)?string('label-success','')}  attr_css" style="margin:5px;">
									<a href="${basepath}/catalog/${catalogCode!""}.html?orderBy=3&attrID=${e.attrID!""}">最新</a>
								</span>
						</div>
					</div>
					<div ><hr style="clear: both;"></div>
				</#if>
				
				
				<div class="row">
					<!-- 商品展示 -->
					<div >
						<#if productList?? && productList?size gt 0>
						<#list productList as item>
						<div class="col-xs-3" style="padding: 5px;text-align: center;">
							<div class="thumbnail" style="width: 100%; display: block;">
								<div style="height: 200px;border: 0px solid;text-align: center;">
									<a href="${basepath}/product/${item.id!""}.html" target="_blank">
										<img class="lazy" style="border: 0px;display: block;margin: auto;max-height: 100%;max-width: 100%;"  
										border="0" src="${systemSetting().defaultProductImg!""}"
										data-original="${systemSetting().imageRootPath}${item.picture!""}">
									</a>
								</div>
								<div style="height: 40px;text-align: center;">
									<div class="col-xs-12 left_product">
										<div class="row">
											<a style="cursor: pointer;margin: auto;" href="${basepath}/product/${item.id!""}.html" target="_blank"
											title="${item.name!""}">
												${item.name!""}
											</a>
										</div>
									</div>
								</div>
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
						</#list>
						</div>
						<#else>
                                抱歉，没有找到<font color='#f40'>${key!""}</font>相关的宝贝!
                                <br>
                                <div class="row">
                                    <div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
                                        <div class="panel panel-default">
                                            <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
                                                <div class="panel-body" style="font-size: 16px;font-weight: normal;">
                                                    <span class="glyphicon glyphicon-ok"></span>
                                                    <span class="text-success">您可以尝试换一个关键词或者换一个分类。</span>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                    </div>
                                </div>
						</#if>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-xs-12" style="border: 0px solid;text-align: right;">
						<#if productList??>
							<#include "pager.ftl"/>
						</#if>
					</div>
				</div>
		
			</div>
		</div>
	</div>
<script type="text/javascript">
$(function() {
	//商品鼠标移动效果
	$("div[class=thumbnail]").hover(function() {
		$(this).addClass("thumbnail_css");
	}, function() {
		$(this).removeClass("thumbnail_css");
	});
});
</script>
</@html.htmlBase>