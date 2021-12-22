<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu=(e.pid==0)?string("商品属性","商品参数")>
<script type="text/javascript">
	$(function() {
		$("#title").focus();
	});

	function onSubmit() {
		if ($.trim($("#name").val()) == "") {
			alert("名称不能为空!");
			$("#name").focus();
			return false;
		}
	}
</script>
<style>
	.leftTD_css{
		width: 100px;
		text-align: right;
	}
	#insertOrUpdateMsg{
		border: 0px solid #aaa;margin: 0px;position: fixed;top: 0;width: 100%;
		background-color: #d1d1d1;display: none;height: 30px;z-index: 9999;font-size: 18px;color: red;
	}
</style>

	<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
	<form action="${basepath}/manage/attribute" theme="simple" id="form">
		<input type="hidden" value="${e.pid!""}" name="pid" />
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>属性编辑</strong>
					<#if e.pid==0>
						<span class="badge badge-important">商品属性</span>&nbsp;<strong>
					<#else>
						<span class="badge badge-success">商品参数</span>&nbsp;<strong>
					</#if>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<tr>
				<td class="leftTD_css">分类目录</td>
				<td style="text-align: left;">
					<!-- 
					<input id="cc" class="easyui-combotree" name="catalogID" value="${e.catalogID!""}"
					data-options="url:'<%=request.getContextPath() %>/manage/catalog/catalog/getRootWithTreegrid?type=p',method:'get',required:false"
					>
				(注意：只能选择子目录。)
					 -->
				
					<select onchange="catalogChange(this)" name="catalogID" id="catalogSelect" data-rule="required;select;">
						<option></option>
						<#list catalogs as item>
							<option pid="0" value="${item.id!""}"><font color='red'>${item.name!""}</font></option>

							<#if item.children??>
								<#list item.children as item>
									<option value="${item.id!""}">&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
								</#list>
							</#if>
						</#list>
					</select><br>(请选择子类别)
				</td>
			</tr>
			<tr>
				<td class="leftTD_css">名称</td>
				<td style="text-align: left;"><input type="text"  value="${e.name!""}" name="name"  data-rule="required;name;length[1~20];"
						id="name" /></td>
			</tr>
			<tr>
				<td class="leftTD_css">顺序</td>
				<td style="text-align: left;"><input type="text"  value="${e.order1!""}" name="order1"
						id="order1" /></td>
			</tr>
			<tr style="background-color: #dff0d8">
				<td>
					<input type="button" onclick="addAttribute();" value="增加属性" class="btn btn-warning"/>
				</td>
				<td style="text-align: center;">
					<#if e.id??>
                        <button method="insert" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 保存
                        </button>
					<#else>
                        <button method="insert" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 新增
                        </button>
					</#if>
				</td>
			</tr>
			
			<!-- 母体 -->
			<tr id="cloneTR" style="display:none;">
				<td class="leftTD_css">名称</td>
				<td style="text-align: left;">
					<input type="text"  value="${e.attrNames!""}" name="attrNames"  placeholder="请输入属性名称"/>
					<span style="margin-left:150px;"></span> 显示顺序：<input type="text"  value="${e.order1List!""}" name="order1List" number="y" placeholder="请输入属性显示的顺序" value="0" size="5" maxlength="5"/>
				</td>
			</tr>
				
			<#if e.attrList?exists>
				<#list e.attrList as item>
					<#if item_index == 0><!-- 第一个不能删除 -->
						<tr class="pclass">
							<td class="leftTD_css">名称</td>
							<td style="text-align: left;">
								<input type="text"  name="attrNames0"  id="attrNames0" value="${item.name!""}" data-rule="required;attrNames0;length[1~20];"/>
								<input type="text"  name="idList0"  id="idList0" value="${item.id!""}" data-rule="required;idList0;length[1~20];" style="display:none;"/>
								<span style="margin-left:150px;"></span> 显示顺序：<input type="text"  name="order1List0" number="y" id="order1List0" value="${item.order1!""}" size="5" maxlength="5" data-rule="required;integer;order1List0;length[1~5];"/>
							</td>
						</tr>
					<#else>
						<tr class="pclass">
							<td class="leftTD_css">名称</td>
							<td style="text-align: left;">
								<input type="text"  name="attrNames"  value="${item.name!""}"/>
								<input type="text"  name="idList"  value="${item.id!""}" style="display:none;"/>
								<span style="margin-left:150px;"></span> 显示顺序：<input type="text"  name="order1List" number="y" id="order1List" value="${item.order1!""}" size="5" maxlength="5" data-rule="required;integer;order1List;length[1~5];"/>
							</td>
						</tr>
					</#if>
				</#list>
			<#else>
				<tr class="pclass">
					<td class="leftTD_css">名称</td>
					<td style="text-align: left;">
						<input type="text"  value="${e.attrNames0!""}" name="attrNames0"  id="attrNames0" data-rule="required;attrNames0;length[1~20];" placeholder="请输入属性名称"/>
						<span style="margin-left:150px;"></span> 显示顺序：<input type="text"  value="${e.order1List0!""}" name="order1List0" number="y" id="order1List0" size="5" maxlength="5" data-rule="required;order1List0;integer;length[1~5];" placeholder="请输入属性显示的顺序"/>
					</td>
				</tr>
			</#if>
		</table>
	</form>

<script type="text/javascript">
	$(function(){
		selectDefaultCatalog();
		
		var ccc = $("#insertOrUpdateMsg").html();
        console && console.log("insertOrUpdateMsg="+insertOrUpdateMsg);
		if(ccc!='' && ccc.trim().length>0){
			$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
		};
	});
	
	function addAttribute(){
		for(var i=0;i<10;i++){
			var cc = $("#cloneTR").clone();
			$("tr[class=pclass]:last").after(cc.show());//.find("input");//.attr("data-rule","");
			cc.find("input[type=button]").show();
		}
	}
	
	function removeThis(t){
		$(t).parent().parent().remove();
		return false;
	}
	
	function catalogChange(obj){
		var _pid = $(obj).find("option:selected").attr("pid");
		console.log("_pid="+_pid);
		if(_pid==0){
			alert("不能选择大类!");
			selectDefaultCatalog();
			return false;
		}
	}
	
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		console.log("_catalogID = " + _catalogID);
		if(_catalogID==0){
			//console.log("_catalogID2 = " + _catalogID);
			$("#catalogSelect").prop("value","");
		}else{
			$("#catalogSelect").prop("value",_catalogID);
		}
	}
	
	//键盘按下的时候对字符进行检查，只能是数字
	$("input[number=y]").keydown(function(event) {
		var key = event.keyCode ? event.keyCode : event.which;
		//console.log("key="+key+",value="+_obj.val()+"isNaN="+isNaN(_obj.val())+",IsNum="+IsNum(_obj.val()));
		if ((key >= 48 && key <= 57) || key==8) {
		//if (IsNum(_obj.val())) {
			var _obj = $(this);
			console.log(">>>_obj.val()="+_obj.val());
			//库存字符检查
			if($.trim(_obj.val())=='' || parseInt(_obj.val())<=0){
				_obj.val("1");
			}
			//checkStockFunc();
			return true;
		} else {
			return false;
		}
	});
</script>
</@page.pageBase>