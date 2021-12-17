<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="门户滚动图片">
<form action="${basepath}/manage/indexImg" method="post" theme="simple">
			<table class="table table-bordered">
				<tr>
					<td colspan="4">
						<a href="selectList" class="btn btn-primary">
							<i class="icon-search icon-white"></i> 查询
						</a>
					
						<a href="toAdd" class="btn btn-success">
							<i class="icon-plus-sign icon-white"></i> 添加
						</a>
					
						<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
							<i class="icon-remove-sign icon-white"></i> 删除
						</button>
					
						<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
								<#include "/manage/system/pager.ftl" >
						</div>
					</td>
				</tr>
			</table>
			
			<div class="alert alert-info">
				注意：图片尺寸请尽量保持在630*180，否则超出的部分会显示不出来。
			</div>
			
			<table class="table table-bordered">
				<tr style="background-color: #dff0d8">
					<th width="20"><input type="checkbox" id="firstCheckbox"/></th>
					<th style="display: none;">id</th>
					<th nowrap="nowrap">标题</th>
					<th>图片</th>
					<th>排序</th>
<!-- 					<th>描述</th> -->
					<th style="width: 50px;">操作</th>
				</tr>
                <#list pager.list as item>
					<tr>
						<td><input type="checkbox" name="ids" value="${item.id!""}"/></td>
						<td style="display: none;">&nbsp;${item.id!""}</td>
						<td>&nbsp;${item.title!""}</td>
						<td>&nbsp;
							<a href="${systemSetting().imageRootPath}${item.picture!""}" target="_blank">
								<img style="max-width: 100px;max-height: 100px;" alt="" src="${systemSetting().imageRootPath}${item.picture!""}">
							</a>
							<br>
							<div>图片链接：</div>
							<a target="_blank" href="${item.link!""}">${item.link!""}</a>
						</td>
						<td>&nbsp;${item.order1!""}</td>
						<td>
							<a href="toEdit?id=${item.id}">编辑</a>
						</td>
					</tr>
                </#list>
				<tr>
						<td colspan="16" style="text-align: center;">
                            <#include "/manage/system/pager.ftl" ></td>
					</tr>
			</table>
</form>
</@page.pageBase>