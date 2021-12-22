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
				<@accountMenu.accountMenu currentMenu="topwd"/>
			</div>
			
			<div class="col-xs-9">

                <div class="row">
                    <div class="col-xs-12">
                        <ol class="breadcrumb">
                            <li class="active">修改密码</li>
                        </ol>
                    </div>
                </div>

                <hr>
				<form role="form" id="form" method="post" class="form-horizontal jqtransform" action="${basepath}/account/changePwd" autocomplete="off" theme="simple" >

				  <div class="form-group">
				    <label for="optionMessage" class="col-lg-2 control-label"></label>
				    <div class="col-lg-6">
				    	<div id="insertOrUpdateMsg" style="background-color: #aaa;font-size: 14px;">
							${errorMsg!""}
				    	</div>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="password" class="col-lg-2 control-label">原密码</label>
				    <div class="col-lg-6">
					    <input  name="password" type="password" class="form-control" id="required"
					    data-rule="旧密码:required;password;remote[unique]" placeholder="请输入原密码" />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="newPassword" class="col-lg-2 control-label">新密码</label>
				    <div class="col-lg-6">
					    <input name="newPassword" type="password" class="form-control" id="newPassword" data-rule="新密码: required;password;" placeholder="请输入密码" />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="newPassword2" class="col-lg-2 control-label">确认新密码</label>
				    <div class="col-lg-6">
					    <input name="newPassword2" type="password" class="form-control" id="newPassword2" data-rule="确认新密码: required;match(newPassword);" placeholder="请输入确认密码" />
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="col-lg-offset-2 col-lg-8">
				    	<#if currentAccount().accountType??>
                            <div class="panel panel-default">
                                <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
                                    <span class="glyphicon glyphicon-user"></span>亲，非系统账号登陆，无需修改密码哦！
                                </div>
                            </div>
                            <hr>
                            <input type="submit" class="btn btn-success btn-sm" value="修改" disabled="disabled"/>
				    	<#else>
                            <button type="submit" class="btn btn-success btn-sm" value="保存">
                                <span class="glyphicon glyphicon-ok"></span>&nbsp;修改
                            </button>
				    	</#if>
				    </div>
				  </div>
				</form>
				
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