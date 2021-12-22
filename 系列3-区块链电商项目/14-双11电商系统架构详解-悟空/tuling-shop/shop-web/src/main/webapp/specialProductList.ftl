<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
<style type="text/css">
img{border: 0px;}

.thumbnail_css{
	border-color: red;
}
.attr_css{
	font-size: 100%;
	float: left;
}
.left_product{
	font-size: 12px;max-height: 35px;overflow: hidden;text-overflow: ellipsis;-o-text-overflow: ellipsis;
}
.lazy {
  display: none;
}
</style>
<script type="text/javascript">
function defaultProductImg(){ 
	var img=event.srcElement; 
	img.src="${systemSetting().defaultProductImg!""}";
	img.onerror=null; //控制不要一直跳动 
}
</script>
	<@menu.menu selectMenu=""/>
	<div class="container">
	
		<div class="row">
			
			<div class="col-xs-3" style="margin-top: -15px;">
					<#include "/catalog_superMenu.ftl"/>
					</br>
					<#include "/productlist_left_picScroll.ftl"/>
			</div>
			
			<div class="col-xs-9">
				<!-- 导航栏 -->
				<div class="row">
					<#assign map={"hot":"热门","sale":"特价","newest":"新品"}/>
					<div>
						<span style="margin:5px;font-weight: bold;">类型：</span>
						<#list map?keys as key>
							<span class="label ${(code??&&key==code)?string("label-success","")}" style="margin-right:5px;font-size:100%;">
								<a href="${basepath}/special/${key}.html">${map[key]}</a>
							</span>
						</#list>
					</div>
					<hr>
				</div>

				<#if pager.list??>
				<div class="row">
					<#list pager.list as item>
						<div class="col-xs-3" style="padding: 5px;text-align: center;">
							<div class="thumbnail" style="width: 100%; display: block;">
								<div style="height: 150px;border: 0px solid;">
									<a href="${basepath}/product/${item.id!""}.html" target="_blank">
										
										<img class="lazy" style="border: 0px;display: block;margin: auto;max-height: 100%;max-width: 100;"  
										border="0" src="${systemSetting().defaultProductImg!""}"
										data-original="${systemSetting().imageRootPath!""}${item.picture!""}">
										
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
								<div class="row" style="display: none;">
									<div class="col-xs-12">
										<#if item.isnew?? && item.isnew=="1">
											<div>1天前上架</div>
										<#else>
											<div>活动已结束!</div>
										</#if>
										<#if item.sale?? && item.sale=="1">
											<div>还剩余12小时结束</div>
										<#else>
											<div>活动已结束!</div>
										</#if>
									</div>
								</div>
							</div>
						</div>
					</#list>
				</div>
				<br style="clear: both;">
				<div style="text-align: right;">
					<#include "/pager.ftl"/>
				</div>
				<#else>

                <div class="row">
					没有找到<font color='red'>${key!""}</font>相关的宝贝!
                </div>
				</#if>

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