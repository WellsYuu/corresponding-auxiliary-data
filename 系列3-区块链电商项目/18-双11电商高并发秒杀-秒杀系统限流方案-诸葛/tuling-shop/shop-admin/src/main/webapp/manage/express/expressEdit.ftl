<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="配送方式">
<form action="${basepath}/manage/express" id="form" method="post">
	<table class="table table-bordered">
		<tr style="display: none;">
			<td>id</td>
			<td><input type="hidden" id="id" name="id" value="${e.id!""}"></td>
		</tr>
		<tr style="background-color: #dff0d8">
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>配送方式编辑</strong>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">编码</td>
			<td style="text-align: left;"><input type="text" name="code" value="${e.code!""}" data-rule="编码:required;code;length[1~10];"
					id="code" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">名称</td>   
			<td style="text-align: left;"><input type="text" value="${e.name!""}" name="name" id="name" data-rule="名称:required;name;length[1~45];" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">费用</td>   
			<td style="text-align: left;"><input type="text" value="${e.fee!""}" name="fee" id="fee" data-rule="费用:required;integer;fee;length[1~5];"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">顺序</td>   
			<td style="text-align: left;"><input type="text" value="${e.order1!""}" name="order1" id="order1" data-rule="费用:required;integer;order1;length[1~5];"/></td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan="2">
				<#if e.id??>
                    <!-- 						<i class="icon-ok icon-white"></i> 保存 -->
                    <button method="update" class="btn btn-success">
                        <i class="icon-ok icon-white"></i> 保存
                    </button>
				<#else>
                    <!-- 						<i class="icon-plus-sign icon-white"></i> 新增 -->
                    <button method="insert" class="btn btn-success">
                        <i class="icon-ok icon-white"></i> 新增
                    </button>
				</#if>
			</td>
		</tr>
	</table>
				
</form>

</@page.pageBase>