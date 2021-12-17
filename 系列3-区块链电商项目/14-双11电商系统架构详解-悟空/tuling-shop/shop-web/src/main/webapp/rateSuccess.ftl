<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
<style type="text/css">
.topCss {
	height: 28px;
	line-height: 28px;
	background-color: #f8f8f8;
	border-bottom: 1px solid #E6E6E6;
	padding-left: 9px;
	font-size: 14px;
	font-weight: bold;
	position: relative;
	margin-top: 0px;
}
.left_product{
	font-size: 12px;display: inline-block;overflow: hidden;text-overflow: ellipsis;-o-text-overflow: ellipsis;white-space: nowrap;max-width: 150px;
}
img.err-product {
background: url(${systemSetting().defaultProductImg!""}) no-repeat 50% 50%;
}
</style>
	<@menu.menu selectMenu=""/>

	<div class="container">
	
		<div class="row">
			<div class="col-xs-12">
				<p class="text-success">感谢您的评价!</p>
				<hr style="margin-top: 10px;">
				
				
				<div class="panel panel-default">
		              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
			              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
			              		<div class="text-success" style="font-size: 16px;font-weight: 700;">
									<span class="glyphicon glyphicon-ok"></span>&nbsp;点评成功！您已成功获得了5个积分！  
								</div>
			              </div>
		              </div>
				</div>
			</div>
		</div>
	</div>
	
</@html.htmlBase>