<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="广告管理">
<style type="text/css">
.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}
</style>
	<form action="${basepath}/manage/advert" method="post" theme="simple">
				<table class="table table-bordered">
					<tr>
						<td>广告标题</td>
						<td><input type="text"  value="${e.title!""}" name="title" class="input-medium search-query" /></td>
						<td>类型</td>
						<td>
                            <#assign map = {'index_top':'index_top','index_right_top':'index_right_top','index_right_bottom':'index_right_bottom','newslist_right_top':'newslist_right_top','newslist_right_bottom':'newslist_right_bottom','flashlist_right_top':'flashlist_right_top','flashlist_right_bottom':'flashlist_right_bottom'}>
                            <select id="code" name="code" class="input-medium">
                                <#list map?keys as key>
                                    <option value="${key}" <#if e.code?? && e.code==key>selected="selected" </#if>>${map[key]}</option>
                                </#list>
                            </select>
						</td>
						<!-- 
						<td>时间范围</td>
						<td>
							<input id="d4311" class="Wdate search-query input-small" type="text" name="createtime"
							value="<s:property value="e.createtime" />"
							onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
							~ 
							<input id="d4312" class="Wdate search-query input-small" type="text" name="createtimeEnd"
							value="<s:property value="e.createtimeEnd" />"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
						</td>
						 -->
					</tr>
					<tr>
						<td colspan="16">
							<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
								<i class="icon-search icon-white"></i> 查询
							</button>
						
							<a href="toAdd" class="btn btn-success">
								<i class="icon-plus-sign icon-white"></i> 添加
							</a>
						
							<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
								<i class="icon-remove-sign icon-white"></i> 删除
							</button>
							
							<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                                <#include "/manage/system/pager.ftl">
							</div>
						</td>
					</tr>
				</table>

				<table class="table table-bordered table-hover">
					<tr class="success">
						<td width="20px"><input type="checkbox" id="firstCheckbox" /></td>
						<td>广告标题</td>
						<td width="180px">code</td>
						<td width="180px">有效日期范围</td>
						<td width="80px">状态</td>
						<td width="80px">图集优先</td>
						<td width="50px">操作</td>
					</tr>

                    <#list pager.list as item>
						<tr>
							<td><input type="checkbox" name="ids"
								value="${item.id!""}" /></td>
							<td class="aCss">
							  <a href="toEdit?id=${item.id}"
										>${item.title!""}</a>
							</td>
							<td>&nbsp;${item.code!""}</td>
							<td>&nbsp;${item.startdate!""} ~ ${item.enddate!""}</td>
							<td>&nbsp;
								<#if item.status??&&item.status=="y">
									<img alt="显示" src="${basepath}/resource/images/action_check.gif">
								<#else>
									<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
								</#if>
							</td>
							<td>&nbsp;
								<#if  item.useImagesRandom??&&item.useImagesRandom=="y">
									<img alt="显示" src="${basepath}/resource/images/action_check.gif">
								<#else>
									<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
								</#if>
							</td>
							<td ><a href="toEdit?id=${item.id}">编辑</a></td>
						</tr>
                    </#list>

					<tr>
						<td colspan="7" style="text-align: center;font-size: 12px;"><#include "/manage/system/pager.ftl"></td>
					</tr>
				</table>
				
				<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
					图标含义：<BR>
					<img alt="显示" src="${basepath}/resource/images/action_check.gif">：显示到门户上
					<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">：不显示到门户上
				</div>

	</form>

</@page.pageBase>