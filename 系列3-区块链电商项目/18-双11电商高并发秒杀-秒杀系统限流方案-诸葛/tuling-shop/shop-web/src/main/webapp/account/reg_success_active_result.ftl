<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
	<@menu.menu selectMenu=""/>

		<div class="container">
			<div class="row" style="margin-top: 10px;">

				<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
							<span class="label label-default" style="font-size:100%;">
								1.填写注册信息 
							</span>
							&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
							<span class="label label-default" style="font-size:100%;">
								2.邮箱验证 
							</span>
							&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
							<span class="label label-success" style="font-size:100%;">
								3.注册成功 
							</span>
						</div>
					</div>
					<hr>

					<div class="panel panel-success">
						<div class="panel-heading" style="text-align: left;">
			                <h3 class="panel-title">
			                	<span class="glyphicon glyphicon-user"></span>&nbsp;用户注册
			                </h3>
			              </div>
			              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
			              	  <div class="panel-body" style="font-size: 22px;font-weight: normal;">
				              	 <#if LinkInvalid??>
					              	<span class="glyphicon glyphicon-warning-sign"></span>
				              	 	<span class="text-default">链接已失效！</span>
				              	 <#else>
					              	 <span class="glyphicon glyphicon-ok text-success"></span>
									 <span class="text-success">恭喜：账号已成功激活，赶紧进行购物体验吧！</span>
				              	 </#if>
				              </div>
			              </div>
					</div>
					<hr>
				</div>
			</div>
		</div>
	</div>
</@html.htmlBase>