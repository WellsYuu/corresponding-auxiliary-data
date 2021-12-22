<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="评论插件管理">
<script type="text/javascript">
	$(function() {
		
		$("input[type='radio']").click(function(){
			console.log($(this).attr("_id"));
			
			if(!confirm("确定设置该选项为系统默认的评论插件吗?")){
				return ;
			}
			var _url = "updateDefaultCommentType?id="+$(this).attr("_id");
			$.ajax({
			  type: 'POST',
			  url: _url,
			  data: {},
			  success: function(data){
				    alert("设置默认评论插件成功！");
				    
//				    document.form1.submit();
				  $("#btnQuery").click();
			  },
			  dataType: "text",
			  error:function(){
				  alert("设置默认评论插件失败！");
			  }
			});
		});
	});
</script>
	<form action="${basepath}/manage/commentType" method="post" theme="simple" name="form1">
		<table class="table table-bordered">
			<tr>
				<td colspan="6">
<#--<%-- 					<s:submit method="selectList" value="查询" cssClass="btn btn-primary" /> --%>-->
					<a id="btnQuery" method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</a>
<#--<%-- 					<s:submit method="toAdd" value="添加" cssClass="btn btn-success" />  --%>-->
<#--<%-- 					<s:submit method="deletes" onclick="return deleteSelect();" value="删除" cssClass="btn btn-danger" /> --%>-->
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th style="display: none;">编号</th>
				<th >名称</th>
				<th >代码</th>
				<th >状态</th>
<!-- 				<th >操作</th> -->
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td style="display: none;">&nbsp;${item.id!""}</td>
					<td>&nbsp;${item.name!""}</td>
					<td>&nbsp;${item.code!""}</td>
					<td>&nbsp;
						<#if item.status??&&item.status=="y">
							<input type="radio" name="e.status" checked="checked" _id="${item.id!""}"/>
							<img src="${basepath}/resource/images/action_check.gif">
						<#else>
							<input type="radio" name="e.status" _id="${item.id!""}"/>
							<img src="${basepath}/resource/images/action_delete.gif">
						</#if>
					</td>
				</tr>
			</#list>

			<tr>
				<td colspan="17" style="text-align: center;"><#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>

	</form>

</@page.pageBase>
