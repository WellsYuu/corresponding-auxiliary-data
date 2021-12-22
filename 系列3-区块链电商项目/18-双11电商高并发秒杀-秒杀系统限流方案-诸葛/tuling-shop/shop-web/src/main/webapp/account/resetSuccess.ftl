<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
	<@menu.menu selectMenu=""/>
<div class="container">
		<div class="row">
	
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
						<span class="label label-default" style="font-size:100%;">
							1.填写账户信息
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-default" style="font-size:100%;">
							2.身份验证
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-default" style="font-size:100%;">
							3.设置新密码
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-success" style="font-size:100%;">
							4.完成
						</span>
					</div>
				</div>
				<hr>
				
				<div class="panel panel-success">
					<div class="panel-heading" style="text-align: left;">
		                <h3 class="panel-title">
		                	找回密码
		                </h3>
		              </div>
		              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
		              	  <div class="panel-body" style="font-size: 16px;font-weight: normal;">
			              	 <span class="glyphicon glyphicon-ok"></span>
			              	 <span class="text-success">密码已成功修改！您可以使用新密码登陆了。</span>
							  <a href="${basepath}/account/login.html" class="btn btn-link btn-md">立即登录</a>
			              </div>
		              </div>
				</div>
				<hr>
			</div>
		</div>
	</div>
</@html.htmlBase>