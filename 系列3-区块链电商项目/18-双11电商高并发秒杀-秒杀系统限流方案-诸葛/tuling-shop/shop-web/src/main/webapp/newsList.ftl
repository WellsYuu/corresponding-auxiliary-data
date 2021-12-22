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

.title1{
    display: block;
    width: 600px;
    overflow: hidden; /*注意不要写在最后了*/
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}
        </style>
<div id="wrap">
	<@menu.menu selectMenu=""/>

	<div class="container">
	
		<div class="row">
			<div class="col-xs-9">
				<div class="row">
					<div class="col-xs-12">
						<ol class="breadcrumb">
						  <li><a href="${basepath}"/>首页</a></li>
						  <li class="active">公告新闻</li>
						</ol>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-12">
						<!-- 新闻列表 -->
						<table class="table">
							<tr>
								<td colspan="2"><h5><b>公告新闻列表</b></h5></td>
							</tr>
							<#list pager.list as item>
								<tr>
									<td>
										<a href="${basepath}/news/${item.id!""}.html" title="${item.title!""}">
											${item.title!""}
										</a>
									</td>
									<td>
										${item.createtime!""}
									</td>
								</tr>
							</#list>
						</table>
												
						<div class="row" style="margin-top: 10px;">
							<div class="col-xs-12" style="border: 0px solid;text-align: right;">
								<#if pager.list??>
									<#include "/pager.ftl"/>
								</#if>
							</div>
						</div>
				
					</div>
				</div>
				
			</div>
			
			<!-- 右边公共部分 -->
			<div class="col-xs-3">
				<div class="row">
					<#include "/index_notice_slide.ftl"/>
				</br>
					<#include "/productlist_left_picScroll.ftl"/>
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
function defaultProductImg(){ 
	var img=event.srcElement; 
	img.src="${systemSetting().defaultProductImg!""}";
	img.onerror=null; //控制不要一直跳动 
}
</script>
</@html.htmlBase>