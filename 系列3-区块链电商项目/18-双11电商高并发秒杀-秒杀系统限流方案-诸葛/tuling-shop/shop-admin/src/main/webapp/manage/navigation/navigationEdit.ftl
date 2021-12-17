<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="友情链接">
    <form action="${basepath}/manage/navigation" theme="simple" name="form" id="form">
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>导航菜单编辑</strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">名称</td>
				<td style="text-align: left;"><input type="text"  value="${e.name!""}" name="name"  data-rule="名称:required;name;length[1~45];"
						id="name" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">打开方式</td>
				<td style="text-align: left;">_blank
					
					<#--<!-- -->
					<#--<s:select disabled="disabled" list="#{'_blank':'_blank','_self':'_self'}" -->
					<#--id="target" name="target"  cssClass="input-medium" -->
						<#--listKey="key" listValue="value"  />-->
					 <#--&ndash;&gt;-->
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">位置</td>
				<td style="text-align: left;">bottom
					<#--<!-- -->
					<#--<s:select disabled="disabled" list="#{'top':'top','center':'center','bottom':'bottom'}" -->
					<#--id="position" name="position"  cssClass="input-medium" -->
						<#--listKey="key" listValue="value"  />-->
					 <#--&ndash;&gt;-->
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">顺序</td>
				<td style="text-align: left;"><input type="text"  value="${e.order1!""}" name="order1"  data-rule="顺序:integer;order1;length[1~5];"
						id="order1" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">地址</td>
				<td style="text-align: left;"><input type="text"  value="${e.http!""}" name="http"  data-rule="名称:required;http;length[1~70];"
						id="http" /><br>
					(输入的地址不带http://)
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<#if e.id??>
                        <button method="update" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 保存
                        </button>
					<#else>
                        <button method="insert" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 新增
                        </button>
					</#if>
				</td>
			</tr>
		</table>
	</form>
</@page.pageBase>