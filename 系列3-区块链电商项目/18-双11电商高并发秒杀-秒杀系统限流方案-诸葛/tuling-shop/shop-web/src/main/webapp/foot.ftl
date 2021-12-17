<!-- UJian Button BEGIN <div class="ujian-hook"></div>-->
<!-- <script type="text/javascript" src="http://v1.ujian.cc/code/ujian.js?type=slide&fade=1&uid=1880230"></script> -->
<!-- <a href="http://www.ujian.cc" style="border:0;"><img src="http://img.ujian.cc/pixel.png" alt="友荐云推荐" style="border:0;padding:0;margin:0;" /></a> -->
<!-- UJian Button END -->

<style>
	
	/* IndexBottom */
.IndexBottom{padding-top:20px;padding-bottom:10px;border-top: 2px solid #f40;}
.IndexBottom dl{float: left;display: inline;width: 110px;margin-top: 22px;margin-left: 43px;}
.IndexBottom dt{margin-left: 5px;font-family: "Microsoft YaHei",\5fae\8f6f\96c5\9ed1,\5b8b\4f53;font-size: 16px;}
.IndexBottom dd{margin-top: 4px;}
.IndexBottom dd span{display: block;margin-top: 4px;}
/* .IndexBottom dd span a{background: url(../i/index_sprite.png) no-repeat -170px -336px;padding-left: 18px;+background-position: -170px -338px;_background-position: -170px -336px;} */
/* .IndexBottom dd span a:hover{background: url(../i/index_sprite.png) no-repeat -141px -316px;+background-position: -141px -318px;_background-position: -141px -316px;} */
.IndexBottom .aboutjy{margin-left: 68px;}
.IndexBottom .IndexBottom-help{margin-left: 112px;}
</style>
	<div id="footer" style="margin-top: 20px;">
	
<!-- 	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation"> -->
	
		<div class="container">
			<div class="row IndexBottom">
				<#list systemManager().newsCatalogs as item>
					<div class="col-xs-2" style="text-align: center;">
						<div class="row" style="margin-bottom: 10px;"><strong>${item.name!""}</strong></div>
						<#if item.news??>
						    <#list item.news as item>
                                <div class="row" style="line-height: 20px;">
                                    <a href="${basepath}/help/${item.code}.html" target="_blank">
                                    ${item.title!""}
                                    </a>
                                </div>
							</#list>
						</#if>
					</div>
				</#list>
			</div>
			<hr style="margin: 0px;">
			<!-- 友情链接 -->
			<div class="row" >
				<div class="col-xs-12" style="text-align: center;">
					<div style="text-align: center;margin: auto;">
						<#if systemManager().navigations??>
						    <#list systemManager().navigations as item>
                                <div style="float: left;margin: 5px;">
                                    <a href="http://${item.http!""}" target="_blank">${item.name!""}</a>
                                </div>
						    </#list>
						</#if>
					</div>
				</div>
			</div>
			<hr style="margin: 0px;">
			<div class="row" style="margin-top: 5px;display: inline;">
				<div class="col-xs-3">
				</div>
				<div class="col-xs-5">
					<p style="text-align: center;">${systemSetting().icp}
						<a target="_blank" href="http://www.aliyun.com/"><img src="http://gtms01.alicdn.com/tps/i1/T1W6.aFbFbXXcZj_6s-96-18.png" alt="运行在阿里云" /></a>
					</p>
				</div>
				<div class="col-xs-1">
					<!-- cnzz站点统计 -->
					${systemSetting().statisticsCode!""}
				</div>
				<div class="col-xs-3">
				</div>
			</div>
		</div>
	</div>

<#--<%@ include file="/resource/common_js.jsp"%>-->
<#include "/fixed.ftl"/>
<script>
$(function() {
	$("#myshopMenuPPP").hover(
		function(){
			$("#myshopMenu").show();
		},
		function(){
			$("#myshopMenu").hide();
		}		
	);
	
	//$("img.lazy").lazyload();
	
	$("img.lazy").lazyload({
		//threshold : 200,
		//placeholder : "http://imgt4.bdstatic.com/it/u=2281794157,3480422365&fm=21&gp=0.jpg",
		//event : "click"
		effect : "fadeIn"
		//event:"mouseover"
	});
});
</script>


	<!-- baidu fenxiang -->
<!-- 
<script type="text/javascript" id="bdshare_js" data="type=slide&amp;img=0&amp;pos=right&amp;uid=732516" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
</script>
 -->
<!-- Baidu Button END -->