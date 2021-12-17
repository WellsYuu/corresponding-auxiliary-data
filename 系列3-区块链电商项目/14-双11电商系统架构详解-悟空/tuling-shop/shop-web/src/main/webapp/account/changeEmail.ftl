<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
	<@menu.menu selectMenu=""/>
	<div class="container">
		<div class="row">
			<div class="col-xs-3">
				<@accountMenu.accountMenu currentMenu="account"/>
			</div>
			
			<div class="col-xs-9">
				<div class="row">
					<div class="col-xs-12">
						<ol class="breadcrumb">
						  <li>个人资料</li>
						  <li class="active">修改邮箱</li>
						</ol>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
						<span class="label label-success" style="font-size:100%;">
							1.填写账户信息
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-default" style="font-size:100%;">
							2.身份验证
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-default" style="font-size:100%;">
							3.完成
						</span>
					</div>
				</div>
				<hr>
				
				<div class="row">
					<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
						<div class="panel panel-default">
				              <div class="panel-body" style="font-weight: normal;text-align: center;">
				              		<form role="form" id="form" class="form-horizontal" action="${basepath}/account/doChangeEmail.html" theme="simple">
									  <div class="form-group">
									    <label for="account" class="col-lg-2 control-label">登陆密码：</label>
									    <div class="col-lg-6">
										    <input type="text" name="password" type="text" class="form-control" id="password"
					 					    data-rule="登陆密码:required;length[6~20];password;remote[checkPassword]" placeholder="请输入登陆密码" />
									    </div>
									  </div>
									  <div class="form-group">
									    <label for="account" class="col-lg-2 control-label">邮箱：</label>
									    <div class="col-lg-6">
										    <input type="text" name="newEmail" type="text" class="form-control" id="newEmail"
					 					    data-rule="邮箱:required;newEmail;remote[changeEmailCheck]" placeholder="请输入邮箱" />
									    </div>
									  </div>
									  
									  <div class="form-group">
									    <div class="col-lg-offset-2 col-lg-6">
									      <input type="submit" class="btn btn-success btn-sm" value="提交"/>
									    </div>
									  </div>
									</form>
				              </div>
						</div>
						<hr>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
</@html.htmlBase>