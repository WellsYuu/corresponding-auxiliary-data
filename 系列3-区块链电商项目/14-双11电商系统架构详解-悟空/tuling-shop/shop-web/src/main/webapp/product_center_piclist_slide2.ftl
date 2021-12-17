
<!-- 产品明细页面中间位置的图片列表 -->
<style type="text/css">
/* html{overflow-y:scroll;} */
/* body{margin:0; font:12px "\5B8B\4F53",san-serif;background:#ffffff;} */
div,ul,li{padding:0; margin:0;}
li{list-style-type:none;}
img{vertical-align:top;border:0;}

/* box */
.box{
/* 	width:366px; */
}
.tb-pic a{display:table-cell;text-align:center;vertical-align:middle;}
.tb-pic a img{vertical-align:middle;}
.tb-pic a{*display:block;*font-family:Arial;*line-height:1;}
.tb-thumb{margin:10px 0 0;overflow:hidden;}
.tb-thumb li{background:none repeat scroll 0 0 transparent;float:left;max-height:42px;max-width:42px;margin:0 6px 0 0;overflow:hidden;padding:1px;}
.tb-s310, .tb-s310 a{height:334px;border: 2px solid #f40;width: 366px;}
.tb-s310, .tb-s310 img{max-height:334px;max-width: 100%;}
.tb-s310 a{*font-size:271px;}
.tb-s40 a{*font-size:35px;}
.tb-s40, .tb-s40 a{max-width: 40px;}
.tb-booth{border:0px solid #CDCDCD;position:relative;z-index:1;}
.tb-thumb .tb-selected{border: 2px solid #C30008;}
.tb-thumb .tb-selected div{background-color:#FFFFFF;border:medium none;}
.tb-thumb li div{border:1px solid #CDCDCD;}
div.zoomDiv{z-index:999;position:absolute;top:0px;left:0px;width:200px;height:200px;background:#ffffff;border:1px solid #CCCCCC;display:none;text-align:center;overflow:hidden;}
div.zoomMask{position:absolute;background:url("${basepath}/resource/js/jquery.imagezoom/images/mask.png") repeat scroll 0 0 transparent;cursor:move;z-index:1;
	width:20px;height:20px;
}
</style>
<div class="box" id="mainBox00">

	<div class="tb-booth tb-pic tb-s310">
		<#list e.productImageList as img>
			<#if img_index==0>
				<a href="${systemSetting().imageRootPath}${img.image3!""}">
				<img
				src="${systemSetting().imageRootPath}${img.image1!""}"
				rel="${systemSetting().imageRootPath}${img.image3!""}" class="jqzoom" /></a>
			</#if>
		</#list>
	</div>

	<ul class="tb-thumb" id="thumblist">
<#list e.productImageList as img>
			<#if img_index==0>
				<li class="tb-selected">
			<#else>
				<li>
			</#if>
				<div class="tb-pic tb-s40" style="width: 50px;height: 50px;">
					<a href="#"><img style="max-width: 50px;max-height: 50px;" src="${systemSetting().imageRootPath}${img.image1!""}"
					mid="${systemSetting().imageRootPath}${img.image2!""}"
					big="${systemSetting().imageRootPath}${img.image3!""}"></a>
				</div>
			</li>
</#list>
	</ul>
	
</div>

