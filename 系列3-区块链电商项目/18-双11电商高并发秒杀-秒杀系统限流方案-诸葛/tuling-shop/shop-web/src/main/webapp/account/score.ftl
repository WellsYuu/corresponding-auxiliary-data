<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
<style type="text/css">
/* 	#form .n-invalid {border: 1px solid #f00;} */
</style>
<style type="text/css">
.centerImageCss{
	width: 560px;
	height: 180px;
}
</style>
	<@menu.menu selectMenu=""/>
	<div class="container">
		<div class="row">
			<div class="col-xs-3">
				<@accountMenu.accountMenu currentMenu="score"/>
			</div>
			
			<div class="col-xs-9">

                <div class="row">
                    <div class="col-xs-12">
                        <ol class="breadcrumb">
                            <li class="active">我的积分</li>
                        </ol>
                    </div>
                </div>

                <hr>

				  <div class="row">
				  <div class="col-xs-12">
				<div class="panel panel-default">
				<div class="panel-body">
					<div style="width:300px;height:150px;margin-left:50px;padding:20px;text-align:center;border-right:1px solid #ddd;float:left;">
						<font style="font-size:20px;font-weight:600">您当前的积分</font>
						<br><br>
						<font style="font-size:26px;font-weight:900;color:#dd4814">${e.score}</font>
					</div>
					<div style="height:150px;line-height:150px;float:right;margin-right:70px;">
						<a href="${basepath}/activity/score.html" class="btn btn-primary">前往积分商城消费</a>
					</div>
				</div>
				</div>
			</div>
	
<script type="text/javascript">
$(function() {
	if($.trim($("#insertOrUpdateMsg").html()).length>0){
		$("#insertOrUpdateMsg").slideDown(1000).delay(2000).slideUp(1000);
	}
});
</script>
</@html.htmlBase>