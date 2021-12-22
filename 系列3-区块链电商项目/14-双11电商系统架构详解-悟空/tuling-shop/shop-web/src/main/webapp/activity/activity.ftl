<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/activity/activity_common_productList.ftl" as activity>
<@html.htmlBase>
<meta property="qc:admins" content="50702666757625530706654" />
<meta property="wb:webmaster" content="28e244326adb6a77" />
<style type="text/css">
    .alert123{
        /* 	padding: 10px; */
        margin-bottom: 5px;margin-top: 10px;margin-right: -15px;
    }
    .product_css{
        height: 200px;border: 0px solid #ccc;
    }
    .left_product{
        font-size: 12px;max-height: 35px;overflow: hidden;text-overflow: ellipsis;-o-text-overflow: ellipsis;
    }
    img{border: 0px;}

    .thumbnail_css{
        border-color: red;
    }
    img.err-product {
		<#if systemSetting().defaultProductImg??>
            background: url(${systemSetting().defaultProductImg}) no-repeat 50% 50%;
		</#if>
    }
    .lazy {
        display: none;
    }
</style>
<script>
    function defaultProductImg(){
        var img=event.srcElement;
        img.src="${systemSetting().defaultProductImg!""}";
        img.onerror=null; //控制不要一直跳动
    }
</script>
	<@menu.menu selectMenu="activity"/>
	<div class="container" >
		<div class="row">
			<!-- 左侧导航栏、热卖商品、文章、事项 -->
			<div class="col-xs-3" style="margin-top: -15px;">
				<#include "/catalog_superMenu.ftl"/>
                </br>
				<#include "/productlist_left_picScroll.ftl"/>
			</div>
			<!-- 右侧。滚动图片、新闻活动、首页商品展示 -->
			<div class="col-xs-9" style="margin-top: -10px;">
				<@activity.activity title="降价促销" queryType="r" productList=rProductList/>
				<@activity.activity title="打折促销" queryType="d" productList=dProductList/>
				<@activity.activity title="双倍积分" queryType="s" productList=sProductList/>

			</div>
			
		</div>
	</div>
	<script type="text/javascript" src="${basepath}/resource/js/front.js"></script>
<script>
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