<#import "/manage/tpl/htmlBase.ftl" as html>
<@html.htmlBase>
<form action="${basepath}/manage/account" theme="simple" id="form">

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">基本信息</a></li>
	</ul>
	<div id="tabs-1">
		<div class="alert alert-info" style="margin-bottom: 2px;text-align: left;">
			<strong>会员信息：</strong>
		</div>
		<table class="table table-bordered">
					<tr style="display: none;">
						<td>id</td>
						<td><input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">会员类型</td>
						<td style="text-align: left;">
							<#if e.accountType?? && e.accountType=="qq">
								<span class="badge badge-warning">${e.accountTypeName!""}</span>
								<img alt="" src="${basepath}/resource/images/mini_qqLogin.png">
							<#elseif  e.accountType?? && e.accountType=="sinawb">
								<span class="badge badge-warning">${e.accountTypeName!""}</span>
								<img alt="" src="${basepath}/resource/images/mini_sinaWeibo.png">
							<#elseif  e.accountType?? && e.accountType=="alipay">
								<span class="badge badge-warning">alipay</span>
								<img alt="" src="${basepath}/resource/images/alipay_fastlogin.jpg">
							<#else>
								<span class="badge badge-warning">${systemSetting().systemCode}</span>
							</#if>
						
						</td>
					</tr>
					<tr>
						<td style="text-align: right;width: 200px;">昵称</td>
						<td style="text-align: left;">${e.nickname!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">账号</td>   
						<td style="text-align: left;">${e.account!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">城市</td>
						<td style="text-align: left;">${e.city!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">联系地址</td>
						<td style="text-align: left;">${e.address!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">消费额</td>
						<td style="text-align: left;">${e.amount!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">电话</td>
						<td style="text-align: left;">${e.tel!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">Email地址</td>
						<td style="text-align: left;">${e.email!""}
							<#if e.emailIsActive??&&e.emailIsActive=="y"><span class="badge badge-success">已激活</span>
							<#else><span class="badge badge-success">未激活</span></#if>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">是否冻结</td>
						<td style="text-align: left;">
							<#if e.freeze??&&e.freeze=="y">
								<span class="badge badge-important">
									已冻结(
									<#if !e.freezeStartdate?? && !e.freezeEnddate??>永久
									<#else>
									${e.freezeStartdate!""} ~ ${e.freezeEnddate!""}
									</#if>)
								</span>
								
							<#else><span class="badge badge-success">未冻结</span></#if>
						</td>
					</tr>
					
					<tr>
						<td style="text-align: right;">最后登陆时间</td>
						<td style="text-align: left;">${e.lastLoginTime!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">最后登陆IP</td>
						<td style="text-align: left;">${e.lastLoginIp!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">最后登陆位置</td>
						<td style="text-align: left;">${e.lastLoginIp!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">注册日期</td>
						<td style="text-align: left;">${e.regeistDate!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">积分</td>
						<td style="text-align: left;">${e.score!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">等级</td>
						<td style="text-align: left;">

						${e.rankName!""}(${e.rank!""})
<!-- 							<div class="rateit"></div> -->
							
<!-- 							<div id="rateit13" class="rateit bigstars" data-rateit-value="4.2" data-rateit-ispreset="true" data-rateit-readonly="false"  -->
<!-- data-rateit-resetable="false" data-rateit-min="1" data-rateit-max="5" -->
<!-- data-rateit-starwidth="32" data-rateit-starheight="32"></div> -->

						</td>
					</tr>
				</table>
	</div>
</div>
</form>

<script type="text/javascript">
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	
});

</script>

</@html.htmlBase>