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
						<span class="label label-success" style="font-size:100%;">
							3.设置新密码
						</span>
						&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
						<span class="label label-default" style="font-size:100%;">
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
						  <#if reset_password_email_timeout??>
                              <!-- 找回密码链接已失效了！ -->
                              <span class="glyphicon glyphicon-warning-sign"></span>&nbsp;${reset_password_email_timeout!""}
                              <a style="display: none;" class="btn btn-success btn-sm" href="${systemSetting().www}/accountdoForget.html?account=${e.account!""}"><span class="glyphicon glyphicon-send"></span>&nbsp;重新发送邮件</a>

						  <#else>

                              <form role="form" name="form" id="form" class="form-horizontal" action="${basepath}/account/doReset" theme="simple">
                                  <input type="hidden" name="account" value="${e.account!""}"/>
                                  <div class="form-group">
                                      <label for="account" class="col-lg-2 control-label">密码</label>
                                      <div class="col-lg-6">
                                          <input  name="password" type="password" class="form-control" id="password" placeholder="请输入密码"
                                                  data-rule="密码:required;password;length[6~20];" size="20" maxlength="20"/>
                                      </div>
                                  </div>

                                  <div class="form-group">
                                      <label for="vcode" class="col-lg-2 control-label">确认密码</label>
                                      <div class="col-lg-6">
                                          <input name="password2" type="password" class="form-control" id="password2" placeholder="请输入确认密码"
                                                 data-rule="确认密码:required;password2;length[6~20];match(password)" size="20" maxlength="20" />
                                      </div>
                                  </div>

                                  <div class="form-group">
                                      <div class="col-lg-offset-2 col-lg-6">
                                          <button type="submit" class="btn btn-success btn-sm" value="提交信息">
                                              <span class="glyphicon glyphicon-ok"></span>&nbsp;确认修改
                                          </button>
                                      </div>
                                  </div>
                              </form>
						  </#if>
		              </div>
				</div>
				<hr>
			</div>
		</div>
	</div>
</@html.htmlBase>