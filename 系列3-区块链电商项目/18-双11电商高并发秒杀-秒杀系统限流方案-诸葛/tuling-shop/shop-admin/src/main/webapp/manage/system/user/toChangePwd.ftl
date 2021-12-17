<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="修改密码">
<script type="text/javascript">
	$(function() {
		 $("#password").focus();
	});

	function onSubmit() {
		if ($.trim($("#password").val()) == "") {
			alert("旧密码不能为空!");
			$("#password").focus();
			return false;
		}
		if ($.trim($("#newpassword").val()) == "") {
			alert("密码不能为空!");
			$("#newpassword").focus();
			return false;
		}
		if ($.trim($("#newpassword2").val()) == "") {
			alert("密码不能为空!");
			$("#newpassword2").focus();
			return false;
		}
		if ($.trim($("#newpassword2").val()) != $.trim($("#newpassword").val())) {
			alert("两次输入的密码不一致!");
			return false;
		}
	}
</script>
    <form name="form" action="${basepath}/manage/user" method="post">
		<table class="table table-bordered" >
			<tr>
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>修改密码</strong>
				</td>
			</tr>
			<tr style="display:none;">
				<th>id</th>
				<td><input type="hidden" value="${e.id}" name="id"></td>
			</tr>
			<tr>
				<th style="text-align: right;">旧密码</th>
				<td style="text-align: left;"><input type="password" name="password" data-rule="旧密码:required;password;length[6~20];remote[checkOldPassword];"
						id="password" /></td>
			</tr>
			<tr>
				<th style="text-align: right;">新密码</th>
				<td style="text-align: left;"><input type="password" name="newpassword" data-rule="新密码:required;newpassword;length[6~20];"
						id="newpassword" /></td>
			</tr>
			<tr>
				<th style="text-align: right;">确认新密码</th>
				<td style="text-align: left;"><input type="password" name="newpassword2" data-rule="确认密码:required;match(newpassword)"
						id="newpassword2" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<button method="updateChangePwd" class="btn btn-success">
						<i class="icon-ok icon-white"></i> 确认修改
					</button>
				</td>
			</tr>
		</table>
        </form>
</@page.pageBase>