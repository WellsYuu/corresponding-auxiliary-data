<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="商品促销">
	<form action="${basepath}/manage/activity"  method="post" theme="simple"
		<table class="table table-bordered">
			<tr>
				<td>活动ID</td>
				<td><input type="text" value="${e.id!""}" class="input-small search-query" name="id"/></td>
<!-- 				<td>商品ID</td> -->
<#--<%-- 				<td><s:textfield cssClass="input-small search-query" name="id"/></td> --%>-->
				<td style="text-align: right;">活动类型</td>
				<td style="text-align: left;">

					<#assign map = {'':'全部','c':'促销活动','j':'积分兑换','t':'团购活动'}>
                    <select id="activityType" name="activityType"  class="input-small" >
						<#list map?keys as key>
                            <option value="${key}" <#if e.activityType?? && e.activityType==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
				<td style="text-align: right;">优惠方式</td>
				<td style="text-align: left;">
					<#assign map = {'':'','r':'减免','d':'折扣','s':'双倍积分'}>
                    <select id="discountType" name="discountType"  class="input-small" >
						<#list map?keys as key>
                            <option value="${key}" <#if e.discountType?? && e.discountType==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
				<td>状态</td>
				<td>
					<#assign map = {'':'','y':'显示','n':'不显示'}>
                    <select id="status" name="status"  style="width:100px;" >
						<#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
			</tr>
			<tr>
				<td colspan="18">
					<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					<a href="${basepath}/manage/activity/toAdd" class="btn btn-success">
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
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">活动ID</th>
				<th>活动名称</th>
				<th style="width: 80px;">活动类型</th>
				<th style="width: 80px;">优惠方式</th>
				<th>活动明细</th>
				<th style="width: 50px;">状态</th>
				<th style="width: 50px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.id!""}</td>
					<td nowrap="nowrap">&nbsp;${item.name!""}</td>
					<td nowrap="nowrap">&nbsp;
						<#if item.activityType=="c">
							<span class="badge badge-info">促销活动</span>
						<#elseif item.activityType=="j">
							<span class="badge badge-success">积分兑换</span>
						<#elseif item.activityType=="t">
							<span class="badge">团购活动</span>
						<#else>
							异常
						</#if>
					</td>
					<td nowrap="nowrap">&nbsp;
						<#if item.activityType?? && item.activityType=="c">
							<#assign discountTypes={"r":"减免","d":"折扣","s":"双倍积分"} />
							${discountTypes[item.discountType!""]!"异常"}
							<#--<s:if test="discountType.equals(\"r\")">-->
								<#--减免-->
							<#--</s:if>-->
							<#--<s:elseif test="discountType.equals(\"d\")">-->
								<#--折扣-->
							<#--</s:elseif>-->
							<#--<s:elseif test="discountType.equals(\"s\")">-->
								<#--双倍积分-->
							<#--</s:elseif>-->
							<#--<s:else>-->
								<#--异常-->
							<#--</s:else>-->
						</#if>
					</td>
					<td nowrap="nowrap">
						活动时间：${item.startDate!""} ~ ${item.endDate!""}
						<#if item.expire>
							<span class="label label-danger" style="background-color:Red;">活动已到期</span>
						</#if>
						<br>
						商品ID：${item.productID!""}<br>
						<#if item.exchangeScore!=0>
							兑换积分：${item.exchangeScore!""}<br>
						</#if>
						
						<#if item.activityType=="t">
							最低团购人数：${item.minGroupCount!""}</br>
							团购价：${item.tuanPrice!""}</br>
						</#if>
					</td>
					<td>&nbsp;
						<#if item.status?? && item.status=="y">
							<img alt="显示" src="${basepath}/resource/images/action_check.gif">
						<#else>
							<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
						</#if>
					</td>
					<td nowrap="nowrap">
						<a href="toEdit?id=${item.id!""}">编辑</a>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	function deleteSelect() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定删除选择的记录?");
	}
</script>
</@page.pageBase>