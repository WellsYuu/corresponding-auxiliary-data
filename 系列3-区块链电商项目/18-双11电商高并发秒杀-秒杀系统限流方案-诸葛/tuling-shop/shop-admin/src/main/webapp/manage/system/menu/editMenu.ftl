<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="资源管理">
<div style="text-align: center; border: 0px solid #999;margin: auto;">
	<div style="text-align: center; border: 0px solid #999;
		margin: auto;margin-top: 150px;">
		<form action="${basepath}/manage/menu" theme="simple" method="post">
				<table>
					<tr style="display: none;">
						<th>id</th>
						<td><input type="hidden" value="${e.rid!""}" name="rid"/></td>
					</tr>
					<tr style="display: none;">
						<th>pid</th>
						<td><input type="hidden" value="${e.pid!""}"  name="pid"/></td>
					</tr>
					<tr>
						<th>url</th>
						<td>
							<input type="text" value="${e.url!""}" name="url" readonly="false"/>
						</td>
					</tr>
					<tr>
						<th>name</th>
						<td>
                            <input type="text" value="${e.name!""}" name="name" readonly="false"/>
						</td>
					</tr>
					<tr><td></td>
						<td>
							<button value="save"></button>
							<button value="back"></button>
						    <#--<s:submit method="save" value="save"/>-->
						    <#--<s:submit method="back" value="back"/>-->
						</td>
					</tr>
				</table>

		</form>
	</div>
</div>
</@page.pageBase>