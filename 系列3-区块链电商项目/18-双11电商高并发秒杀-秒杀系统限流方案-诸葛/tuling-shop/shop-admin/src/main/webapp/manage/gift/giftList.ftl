<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="赠品管理">
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
</head>

<body>
	<form action="${basepath}/manage/gift" method="post" theme="simple" id="form" name="form">
		<input type="hidden" value="${e.type!""}" name="type"/>
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered">
			<tr>
				<td>赠品名称</td>
				<td><input type="text" value="${e.giftName!""}" class="input-medium search-query" name="giftName"></td>
				<td>状态</td>
				<td>

					<#assign map = {'':'','up':'已上架','down':'已下架'}>
                    <select id="status" name="status" class="input-medium" style="width:100px;">
						<#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
					<#--<!---->
				<#--<td>时间范围</td>-->
				<#--<td>-->
					<#--<input id="d4311" class="Wdate search-query input-small" type="text" name="createtime"-->
					<#--value="${e.createtime!""}"-->
					<#--onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>-->
					<#--~ -->
					<#--<input id="d4312" class="Wdate search-query input-small" type="text" name="createtimeEnd" -->
					<#--value="${e.createtimeEnd!""}"-->
					<#--onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>-->
					<#---->
					<#--<s:textfield class="Wdate input" -->
					<#--name="createtime" cssStyle="width:80px;"-->
					<#--onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" />-->
					<#--~ <s:textfield class="Wdate input" type="text" name="createtimeEnd" cssStyle="width:80px;"-->
					<#--onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" />-->
				<#--</td>-->
					 <#--&ndash;&gt;-->
			</tr>
			<tr>
				<td colspan="16">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
						
					<a href="toAdd?type=${e.type!""}" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
						
					<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
						<i class="icon-remove-sign icon-white"></i> 删除
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
				<th>赠品名称</th>
				<th>赠品价值</th>
				<th>状态</th>
				<th width="60px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td >${item.id!""}</td>
					<td class="aCss">
					  <a href="toEdit?id=${item.id!""}" >${item.giftName!""}</a>
					</td>
					<td>&nbsp;${item.giftPrice!""}</td>
					<td>&nbsp;
						<#if item.status??&&item.status=="up">
							<img alt="已上架" src="${basepath}/resource/images/action_check.gif" title="已上架">
						<#elseif item.status??&&item.status=="down">
							<img alt="已下架" src="${basepath}/resource/images/action_delete.gif" title="已下架">
						<#else>
							未知
						</#if>
					</td>
					<td>
						<a href="toEdit?id=${item.id!""}">编辑</a>
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