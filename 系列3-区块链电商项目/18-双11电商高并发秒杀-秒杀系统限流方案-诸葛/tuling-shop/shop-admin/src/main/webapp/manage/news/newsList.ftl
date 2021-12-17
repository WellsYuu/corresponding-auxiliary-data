<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu=(e.type=="help")?string("文章管理","公告管理")>
<style type="text/css">
.titleCss {
	background-color: #e6e6e6;
	border: solid 1px #e6e6e6;
	position: relative;
	margin: -1px 0 0 0;
	line-height: 32px;
	text-align: left;
}

.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}

.liCss {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	height: 30px;
	text-align: left;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
	<form action="${basepath}/manage/news" method="post" theme="simple" id="form" name="form">
		<input type="hidden" value="${e.type!""}" name="type"/>
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered">
			<tr>
				<td>标题</td>
				<td><input type="text" value="${e.title!""}" class="input-medium search-query" name="title"/></td>
				<#if e.type=="help">
					<td>文章类型</td>
					<td>
						<select onchange="catalogChange(this)" name="catalogID" id="catalogSelect">
							<option></option>
							<#list catalogsArticle as item>
								<option pid="0" value="${item.id!""}"><font color='red'>${item.name!""}</font></option>
                                <#if item.children??>
                                    <#list item.children as item>
                                        <option value="${item.id!""}">&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
                                    </#list>
                                </#if>
                            </#list>
						</select>
					</td>
				</#if>
				<td>状态</td>
				<td>

                    <#assign map = {'':'','y':'显示','n':'不显示'}>
                    <select id="status" name="status" class="input-medium" style="width:100px;">
                        <#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
                        </#list>
                    </select>
					<!--
				<td>时间范围</td>
				<td>
					<input id="d4311" class="Wdate search-query input-small" type="text" name="createtime"
					value="${e.createtime!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="createtimeEnd"
					value="${e.createtimeEnd!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
					
					<s:textfield class="Wdate input" 
					name="createtime" cssStyle="width:80px;"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					~ <s:textfield class="Wdate input" type="text" name="createtimeEnd" cssStyle="width:80px;"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
				</td>
					 -->
			</tr>
			<tr>
				<td colspan="16">

					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
						
					<a href="toAdd?type=${e.type}" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
						
					<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
						<i class="icon-remove-sign icon-white"></i> 删除
					</button>
					
					<button method="updateStatusY" class="btn btn-warning" onclick="return submitIDs(this,'确定让选择的记录审核通过?这样选择的记录将会出现在门户上。');">
						<i class="icon-arrow-up icon-white"></i> 显示
					</button>
						
					<button method="updateStatusN" class="btn btn-warning" onclick="return submitIDs(this,'执行该操作后,选择的记录将不会出现在门户上。确定要执行?');">
						<i class="icon-arrow-down icon-white"></i> 不显示
					</button>
						
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                        <#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>

		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20px"><input type="checkbox" id="firstCheckbox" /></th>
				<th width="120px">ID</th>
				<th>标题</th>
				<#if e.type=="help">
					<th>code</th>
					<th width="130px">显示顺序</th>
				</#if>
				<th width="80px">最后操作时间</th>
				<th width="60px;">显示状态</th>
				<th width="60px;">操作</th>
			</tr>

            <#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td >${item.id!""}</td>
					<td class="aCss">
					  <a href="toEdit?id=${item.id!""}"
								>${item.title!""}</a>
					</td>
					<#if e.type=="help">
						<td nowrap="nowrap">&nbsp;${item.code!""}</td>
						<td nowrap="nowrap">&nbsp;${item.order1!""}</td>
					</#if>
					<td>&nbsp;${item.updatetime!""}</td>
					<td>&nbsp;
						<#if item.status??&&item.status=="y">
							<img alt="显示" src="${basepath}/resource/images/action_check.gif">
						<#else>
							<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
                        </#if>
					</td>
					<td>
						<a href="toEdit?id=${item.id!""}">编辑</a>|
						<#if item.type="help">
							<a target="_blank" href="${systemSetting().www}/help/${item.code!""}.html" >查看</a>
						<#else>
							<a target="_blank" href="${systemSetting().www}/news/${item.id!""}.html" >查看</a>
						</#if>
					</td>
				</tr>
            </#list>

			<tr>
				<td colspan="17" style="text-align: center;font-size: 12px;">
                    <#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
		
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			图标含义：<BR>
			<img alt="显示" src="${basepath}/resource/images/action_check.gif">：显示到门户上
			<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">：不显示到门户上
		</div>

	</form>
	

<SCRIPT type="text/javascript">
$(function(){
	selectDefaultCatalog();
});
function selectDefaultCatalog(){
	var _catalogID = $("#catalogID").val();
	console.log("_catalogID="+_catalogID);
	if(_catalogID!='' && _catalogID>0){
		$("#catalogSelect").attr("value",_catalogID);
	}
}
</SCRIPT>
</@page.pageBase>