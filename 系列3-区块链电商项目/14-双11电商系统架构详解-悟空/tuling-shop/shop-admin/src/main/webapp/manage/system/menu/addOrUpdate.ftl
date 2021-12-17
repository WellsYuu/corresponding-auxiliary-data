<#import "/manage/tpl/htmlBase.ftl" as html/>
<@html.htmlBase>
<body>
	<SCRIPT type="text/javascript">
		<!--
		$(function(){
			
	 		//$("#add").add("#update").click(function(){
	 			//art.dialog.open('${basepath}/menu!toEdit.action',
	 				//	{title: '个人信息',width:500, height:350,lock:true});	 			
	 			//art.dialog.load('./ajaxContent/login.html', false);
	 			//art.dialog.open('', {title: '提示'});
	 		//});
	 		$("#input_menu_name").focus();
	 		
	 		$('input:radio[name="parentOrChildRadio"]').click(function(){
	 			var parentOrChild =$(this).val();//子菜单还是根节点
	 			if(parentOrChild==0 || parentOrChild==1){
	 				//添加顶级菜单
	 				$("#result_table1").hide();
	 			}else{
	 				$("#result_table1").show();
	 			}
	 		});
	 		
	 		//添加子菜单/修改父菜单
	 		$("#add").click(function(){
	 			var id = $("#input_menu_id").val();
	 			var name = $("#input_menu_name").val();
	 			var url = $("#input_menu_url").val();
	 			var orderNum = $("#input_menu_orderNum").val();
	 			var type = $("#input_menu_type").val();
	 			
	 			var n_name = $("#input_new_menu_name").val();
	 			var n_url = $("#input_new_menu_url").val();
	 			var parentOrChild =$('input:radio[name="parentOrChildRadio"]:checked').val();//子菜单还是根节点
	 			var n_orderNum = $("#input_new_menu_level").val();
	 			var n_type = $("#input_new_menu_type").val();
	 			
	 			$.ajax({
					url:"${basepath}/manage/menu/addOrUpdate",
					type:"post",
					data:{
						updateP:2,//-1不修改父菜单，1修改
						//父菜单信息
						id:id,
						name:name,
						url:url,
						orderNum:orderNum,
						type:type,
						
						//子菜单信息
						n_name:n_name,
						n_url:n_url,
						parentOrChild:parentOrChild,
						n_orderNum:n_orderNum,
						n_type:n_type
					},
					dataType:"text",
					success:function(data, textStatus){
						//var zNodes = eval('('+data+')');
						//$.fn.zTree.init($("#treeDemo2"), setting, zNodes);
						
						//document.form1.action = "menu!selectList.action";
						//document.form1.submit();
						//alert("修改资源菜单成功请刷新页面!");
						
						$("#showMsgDiv").html("修改资源菜单成功!");
						
						setTimeout(function(){$("#showMsgDiv").html("");},2000);
					},
					error:function(){
						alert("修改资源菜单失败!");
					}
				});
	 		});
	 		
		});
		//-->
	</SCRIPT>
<style>
	body{text-align:center;}
</style>
<form action="${basepath}/manage/menu" name="form1" method="post" theme="simple">
<div id="contians_div" style="text-align: right; border: 0px solid red; margin: auto;">
			<div id="context_div" style="margin-top: 20px;">
			
			<div id="showMsgDiv" style="text-align: center;"></div>
	<table id="result_table1" class="table table-bordered" style="width: 500px;margin: auto;margin-top: 20px;">
	<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>当前选中的菜单</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<td>id</td>
			<td>
				<inputid="input_menu_id" readonly="readonly" value='${e.id!""}'/>
			</td>
		</tr>
		<tr style="display: none;">
			<td>pid</td>
			<td>
				<input id="input_menu_pid" readonly="readonly" value='${e.pid!""}'/>
			</td>
		</tr>
		<tr>
			<th>名称</th>
			<td style="text-align: left;">
				<input type="text"  id="input_menu_name" size="60" value='${e.name!""}'/>
			</td>
		</tr>
		<tr>
			<th>url</th>
			<td style="text-align: left;">
				<input type="text"  id="input_menu_url" size="80" value='${e.url!""}' style="width: 360px"/>
			</td>
		</tr>
		<tr>
			<th>类型</th>
			<td style="text-align: left;">

				<#assign y_n = {'':'--请选择--','module':'模块','page':'页面','button':'按钮'}>
                <select id="input_menu_type" name="type" class="input-medium">
					<#list y_n?keys as key>
                        <option value="${key}" <#if e.type?? && e.type==key>selected="selected" </#if>>${y_n[key]}</option>
					</#list>
                </select>
			</td>
		</tr>
		<tr>
			<th>顺序</th>
			<td style="text-align: left;">
				<input type="text"  id="input_menu_orderNum" value='${e.orderNum!""}'/>
				(菜单顺序从1开始，小的显示在前面)
			</td>
		</tr>
		</table>
		<!-- item -->
		
		<table id="result_table2" class="table table-bordered" style="width: 500px;margin: auto;margin-top: 20px;">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>添加菜单</strong>
				顶级模块<input type="radio" name="parentOrChildRadio" value="0"/>
				顶级页面<input type="radio" name="parentOrChildRadio" value="1"/>
				子模块<input type="radio" name="parentOrChildRadio" value="2"/>
				子页面<input type="radio" name="parentOrChildRadio" value="3"/>
				功能<input type="radio" name="parentOrChildRadio" value="4" checked="checked"/>
			</td>
		</tr>
		<tr style="display: none;">
			<td>id</td>
			<td>
				<input type="text"  id="input_new_menu_id" readonly="readonly"/>
			</td>
		</tr>
		<tr style="display: none;">
			<td>pid</td>
			<td>
				<input type="text"  id="input_new_menu_pid" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>名称</th>
			<td style="text-align: left;">
				<input type="text"  id="input_new_menu_name" size="60" />
			</td>
		</tr>
		<tr>
			<th>url</th>
			<td style="text-align: left;">
				<input type="text"  id="input_new_menu_url" size="80" style="width: 360px"/>
			</td>
		</tr>
		<tr>
			<th>类型</th>
			<td style="text-align: left;">
				<select id="input_new_menu_type">
					<option value="">--请选择--</option>
					<option value="module">模块</option>
					<option value="page">页面</option>
					<option value="button">功能</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>顺序</th>
			<td style="text-align: left;">
				<input type="text"  id="input_new_menu_level"/>(菜单顺序从1开始，小的显示在前面)
			</td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan="2">
				<input type="button" id="add" value="修改或添加" class="btn btn-primary"/>
			</td>
		</tr>
	</table>
</div></div>
</form>
</body>
</@html.htmlBase>