<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="等级管理">
<form action="${basepath}/manage/accountRank" method="post" theme="simple" id="form" >
	<table class="table table-bordered">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>会员等级编辑</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<td>id</td>
			<td><input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">code</td>
			<td style="text-align: left;"><input type="text" value="${e.code!""}" name="code" id="code" data-rule="code:required;code;length[1~10];"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">等级名称</td>   
			<td style="text-align: left;"><input type="text" value="${e.name!""}" name="name" data-rule="等级名称:required;name;length[1~10];"
					id="name"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">最小积分</td>
			<td style="text-align: left;"><input type="text" value="${e.minScore!""}" name="minScore" data-rule="最小积分:integer;minScore;length[1~10];"
					id="minScore" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">最大积分</td>
			<td style="text-align: left;"><input type="text" value="${e.maxScore!""}" name="maxScore" data-rule="最大积分:integer;maxScore;length[1~10];"
					id="maxScore" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">备注</td>
			<td style="text-align: left;"><input type="text" value="${e.remark!""}" name="remark"
					id="remark" /></td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan="2">
				<#if e.id??>
<#--<%-- 					<s:submit method="insert" value="新增" cssClass="btn btn-primary"/> --%>-->
<#--<%-- 					<s:a method="insert" cssClass="btn btn-success"> --%>-->
<#--<!-- 						<i class="icon-plus-sign icon-white"></i> 新增 &ndash;&gt;-->
<#--<%-- 					</s:a> --%>-->
                    <button method="update" class="btn btn-success">
                        <i class="icon-ok icon-white"></i> 保存
                    </button>
				<#else>
<#--<%-- 					<s:submit method="update" value="保存" cssClass="btn btn-primary"/> --%>-->
<#--<%-- 					<s:a method="update" cssClass="btn btn-success"> --%>-->
<#--<!-- 						<i class="icon-ok icon-white"></i> 保存 &ndash;&gt;-->
<#--<%-- 					</s:a> --%>-->
                    <button method="insert" class="btn btn-success">
                        <i class="icon-ok icon-white"></i> 新增
                    </button>
				</#if>
<#--<%-- 				<s:submit method="back" value="返回" cssClass="btn btn-inverse"/> --%>-->
			</td>
		</tr>
	</table>
</form>

</@page.pageBase>