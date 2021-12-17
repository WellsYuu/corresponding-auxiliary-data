<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="键值对管理">
<form action="${basepath}/manage/keyvalue" id="form" name="form">
			<table class="table table-bordered">
				<tr style="background-color: #dff0d8">
					<td colspan="2" style="background-color: #dff0d8;text-align: center;">
						<strong>键值对编辑</strong>
					</td>
				</tr>
				<tr style="display: none;">
					<td>id</td>
					<td><input type="hidden" name="id" value="${e.id!""}" id="id"></td>
				</tr>
				<tr>
					<td style="text-align: right;">键</td>
					<td style="text-align: left;"><input type="text" name="key1" value="${e.key1!""}" id="key1" data-rule="键:required;key1;length[1~45];"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">值</td>
					<td style="text-align: left;"><input type="text" name="value" value="${e.value!""}" id="value" data-rule="值:required;value;length[1~45];"/></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<#if e.id??>
							<button method="update" class="btn btn-success" >
								<i class="icon-ok icon-white"></i> 保存
							</button>
							<#else>
								<button method="insert" class="btn btn-success" >
									<i class="icon-ok icon-white"></i> 新增
								</button>
						</#if>
					</td>
				</tr>
			</table>
	</form>

</@page.pageBase>