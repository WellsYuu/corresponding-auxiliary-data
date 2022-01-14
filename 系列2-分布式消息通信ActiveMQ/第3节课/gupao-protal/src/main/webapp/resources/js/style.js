/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

$("#zhishi").click(function() {
    $(this).hide();
})
$(".qiehuan").click(function (){
	$("#zhishi").hide(1000);
	$(this).parents(".login").hide().siblings().show();	
});


$(".login-tab li").click(function () {$(this).addClass("login-on").siblings().removeClass("login-on");$(".login-style").eq($(this).index()).show().siblings().hide();$(".tishi").hide();})
var wait =60;document.getElementById("btn").disabled =false;function time(o) {if (wait ==0) {o.removeAttribute("disabled");o.value ="获取动态密码";wait =60;} else {o.setAttribute("disabled",true);o.value ="重新发送(" + wait + ")";wait--;setTimeout(function () {time(o);},1000)
}
}

//登录操作
function cliLogin() {

	var txtUser = $.trim($("#txtUser").val());
	var txtPwd = $("#Userpwd").val();
	var txtCode = $.trim($('#txtCode').val());
	if ($.trim(txtUser) == "") {
		Tip('请输入账号！');
		$("#txtUser").focus();
		return;
	}
	if ($.trim(txtPwd) == "") {
		Tip('请输入密码！');
		$("#Userpwd").focus();
		return;
	}
	if ($('#logincode').attr('iscode') == "1" && txtCode == "") {
		Tip('请输入验证码！');
		$("#txtCode").focus();
		return false;
	}
	return false;
}

function Sendpwd(sender) {
	var code = $("#txtCode2").val();
	var phone = $.trim($("#phone").val());
	if ($.trim(phone) == "") {
		Tip('请填写手机号码！');
		$("#phone").focus();
		return;
	}
	if ($("#yz-code").is(":visible") && $.trim(code) == "") {
		Tip('请填写验证码！');
		$("#txtCode2").focus();
		return;
	}
	return;
}
$("#dynamicLogon").click(function() {
	var pwd = $.trim($("#dynamicPWD").val());
	var phone = $.trim($("#phone").val());
	var code = $("#txtCode2").val();
	if ($.trim(phone) == "") {
		Tip('请填写手机号码！');
		$("#phone").focus();
		return;
	}
	if ($("#yz-code").is(":visible") && $.trim(code) == "") {
		Tip('请填写验证码！');
		$("#txtCode2").focus();
		return;
	}
	if ($.trim(pwd) == "") {
		Tip('动态密码未填写！');
		$("#dynamicPWD").focus();
		return;
	}
	return;
}); 
function Tip(msg) {
	$(".tishi").show().text(msg);
}
console.log("\u002f\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u000d\u000a\u0020\u002a\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u0009\u0009\u000d\u000a\u0020\u002a\u0020\u0009\u0009\u0009\u0009\u0009\u0009\u0020\u0020\u0020\u0020\u0020\u0020\u4ee3\u7801\u5e93\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0077\u0077\u0077\u002e\u0064\u006d\u0061\u006b\u0075\u002e\u0063\u006f\u006d\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0009\u0009\u0020\u0020\u52aa\u529b\u521b\u5efa\u5b8c\u5584\u3001\u6301\u7eed\u66f4\u65b0\u63d2\u4ef6\u4ee5\u53ca\u6a21\u677f\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002f");