<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="会员管理">
<form action="${basepath}/manage/account" method="post" theme="simple" id="form">
	<table class="table table-bordered">
			<tr>
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>冻结会员登陆账号</strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">昵称</td>
				<td style="text-align: left;"><input type="text" value="${e.nickname!""}" name="nickname"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">账号</td>   
				<td style="text-align: left;"><input type="text" value="${e.account!""}" name="account"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">是否冻结</td>
				<td style="text-align: left;">
					<#assign map = {'y':'是','n':'否'}>
                    <select id="freeze" name="freeze" class="input-medium">
						<#list map?keys as key>
                            <option value="${key}" <#if e.freeze?? && e.freeze==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
			</tr>
			<tr>
				<td width="200px" style="text-align: right;">
					冻结时间范围
				</td>
				<td>
					<input id="d4311" class="Wdate search-query input-small" type="text" name="freezeStartdate"
					value="${e.freezeStartdate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="freezeEnddate"
					value="${e.freezeEnddate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
					
					(注：不填写时间范围将永久冻结此账号！)
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
<#--<%-- 					<s:submit method="updateFreeze" value="提交" cssClass="btn btn-primary"/> --%>-->
<#--<%-- 					<s:a method="updateFreeze" cssClass="btn btn-success"> --%>-->
<!-- 						<i class="icon-ok icon-white"></i> 保存 -->
<#--<%-- 					</s:a> --%>-->
					<button method="updateFreeze" class="btn btn-success" onclick="submitNotValid2222(this)">
						<i class="icon-ok icon-white"></i> 保存
					</button>
<#--<%-- 					<s:submit method="back" value="返回" cssClass="btn btn-inverse"/> --%>-->
				</td>
			</tr>
		</table>
</form>

</@page.pageBase>