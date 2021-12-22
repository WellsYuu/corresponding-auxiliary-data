<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="商品促销">
<style>
	.dfsfdsfsf label{
		display: inline;
	}
</style>
<div class="alert alert-info">
	提示：对【商品促销】的添加/修改不会立即生效，需要到系统管理--缓存管理页面点击【加载活动+活动商品列表】按钮，才能生效。
</div>
	
<form action="${basepath}/manage/activity" theme="simple" id="form" class="form-horizontal">
	<table class="table table-bordered">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>活动编辑</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<td>id</td>
			<td><input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/></td>
		</tr>
		<tr>
			<td style="text-align: right;width: 200px;">活动名称</td>
			<td style="text-align: left;">
				<input type="text" value="${e.name!""}" name="name" id="name" data-rule="活动名称:required;name;length[1~45];"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">会员范围</td>   
			<td style="text-align: left;" class="dfsfdsfsf" id="checkboxArr">
				<#--<%-->
				<#--application.setAttribute("accountRankMap",SystemManager.accountRankMap);-->
				<#--%>-->

				<input type="checkbox" name="accountRange" value="R1" id="e.accountRange-1" data-rule="checked">
				<label for="e.accountRange-1" class="checkboxLabel">普通会员</label>
				<input type="checkbox" name="accountRange" value="R2" id="e.accountRange-2">
				<label for="e.accountRange-2" class="checkboxLabel">铜牌会员</label>
				<input type="checkbox" name="accountRange" value="R3" id="e.accountRange-3">
				<label for="e.accountRange-3" class="checkboxLabel">银牌会员</label>
				<input type="checkbox" name="accountRange" value="R4" id="e.accountRange-4">
				<label for="e.accountRange-4" class="checkboxLabel">金牌会员</label>
				<input type="checkbox" name="accountRange" value="R5" id="e.accountRange-5">
				<label for="e.accountRange-5" class="checkboxLabel">钻石会员</label>
				<input type="hidden" id="__multiselect_form_e_accountRange" name="__multiselect_e.accountRange" value="${e.accountRange!""}">
				
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">活动开始时间</td>
			<td style="text-align: left;">
				<input id="startDate" class="Wdate search-query " type="text" name="startDate"
				value="${e.startDate!""}" data-rule="活动开始时间:required;startDate;"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')||\'2020-10-01\'}'})"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">活动结束时间</td>
			<td style="text-align: left;">
				<input id="endDate" class="Wdate search-query " type="text" name="endDate"
				value="${e.endDate!""}" data-rule="活动结束时间:required;endDate;"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2020-10-01'})"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">状态</td>
			<td style="text-align: left;">
				<#assign map = {'':'','n':'禁用','y':'启用'}>
                <select id="status" name="status"  class="input-small" data-rule="状态:required;status;">
					<#list map?keys as key>
                        <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
					</#list>
                </select>
			</td>
		</tr>
		
		<tr>
			<td style="text-align: right;">参与活动的商品</td>
			<td style="text-align: left;">
				<input type="text" value="${e.productID!""}" id="productID" name="productID"  style="width:80%;"/>
				<br>多个商品ID之间用|分割
			</td>
		</tr>
		
		<tr>
			<td style="text-align: right;">活动类型</td>
			<td style="text-align: left;">

				<#assign map = {'':'','c':'促销活动','j':'积分兑换','t':'团购活动'}>
                <select id="activityType" name="activityType"  class="input-small" data-rule="活动类型:required;activityType;">
					<#list map?keys as key>
                        <option value="${key}" <#if e.activityType?? && e.activityType==key>selected="selected" </#if>>${map[key]}</option>
					</#list>
                </select>
				<p>
				<div id="discountDiv" style="display: none;">
					<p>优惠方式：
						<#assign map = {'-':'-','r':'减免','d':'折扣','s':'双倍积分'}>
                        <select id="discountType" name="discountType"  class="input-small" data-rule="优惠方式:required;discountType;">
							<#list map?keys as key>
                                <option value="${key}" <#if e.discountType?? && e.discountType==key>selected="selected" </#if>>${map[key]}</option>
							</#list>
                        </select>
					<p>折扣/减价：<input type="text" value="${e.discount!""}" id="discount" name="discount"  class="input-small" /></p>
				</div>
				
				<div id="exchangeScoreDiv" style="display: none;">
					兑换积分：<input type="text" value="${e.exchangeScore!""}" id="exchangeScore" name="exchangeScore"  class="input-small" />
				</div>
				
				<div id="minGroupCountDiv" style="display: none;">
					<p>最低团购人数：<input type="text" value="${e.minGroupCount!""}" id="minGroupCount" name="minGroupCount"  class="input-small" /></p>
					<p>团购价：<input type="text" value="${e.tuanPrice!""}" id="tuanPrice" name="tuanPrice"  class="input-small" /></p>
				</div>
			</td>
		</tr>
<!-- 		<tr id="discountTypeTr" style="display: none;"> -->
<!-- 			<td style="text-align: right;">优惠方式</td> -->
<!-- 			<td style="text-align: left;"> -->
<#--<%-- 				<s:select list="#{'':'','r':'减免','d':'折扣','s':'双倍积分'}" id="discountType" name="discountType"  listKey="key" listValue="value"   --%>-->
<#--<%-- 				data-rule="优惠方式:required;discountType;"/> --%>-->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr id="discountTr" style="display: none;"> -->
<!-- 			<td style="text-align: right;">discount</td> -->
<!-- 			<td style="text-align: left;"> -->
<#--<%-- 				<s:textfield id="discount" name="discount"  cssClass="input-small" /> --%>-->
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<td style="text-align: right;">最大购买数量</td>
			<td style="text-align: left;">
                <input type="text" value="${e.maxSellCount!""}" id="maxSellCount" name="maxSellCount"  class="input-small"  data-rule="最大购买数量:required;integer;maxSellCount;"/>
				<br>
				(0：表示不限制)
			</td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan="2">
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

<script type="text/javascript">
$(function(){
	ceqweqeqwe();
	
	$("#activityType").change(function(){
		ceqweqeqwe();
	});
	
	var checkboxValue = $("#__multiselect_form_e_accountRange").val();
	//console.log("checkboxValue="+checkboxValue);
	if(checkboxValue){
		var arr = checkboxValue.split(",");
		for(var i=0;i<arr.length;i++){
			$("#checkboxArr").find("input[type=checkbox]").each(function(){
				//console.log("value="+$(this).attr("value")+",attr[i]="+arr[i]);
				if($(this).attr("value")==$.trim(arr[i])){
					$(this).attr("checked",true);
				}
			});
		}
	}
});

function ceqweqeqwe(){
	var v = $("#activityType").val();
	//console.log("v="+v);
	if(v=="c"){
		$("#discountDiv").show();
		$("#exchangeScoreDiv").hide();
		$("#minGroupCountDiv").hide();
	}else if(v=="j"){
		$("#discountDiv").hide();
		$("#exchangeScoreDiv").show();
		$("#minGroupCountDiv").hide();
	}else if(v=="t"){
		$("#discountDiv").hide();
		$("#exchangeScoreDiv").hide();
		$("#minGroupCountDiv").show();
	}else{
		$("#discountDiv").hide();
		$("#exchangeScoreDiv").hide();
		$("#minGroupCountDiv").hide();
	}
}
</script>
</@page.pageBase>