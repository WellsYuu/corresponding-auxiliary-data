<#import "/manage/tpl/pageBase.ftl" as page/>
<@page.pageBase currentMenu=(e.type=="p")?string("商品目录","文章分类")>
<script type="text/javascript">
	$(function() {
		$("#treegrid").treegrid({"treeColumn":1});
	});
	function deleteSelect(id) {
		try{
			if(confirm("确定删除当前记录?")){
				$.blockUI({ message: "系统处理中，请等待...",css: { 
			        border: 'none', 
			        padding: '15px', 
			        backgroundColor: '#000', 
			        '-webkit-border-radius': '10px', 
			        '-moz-border-radius': '10px', 
			        opacity: .5, 
			        color: '#fff' 
			    }});
				var _url = "deleteByID?id="+id;
				$.ajax({
				  type: 'POST',
				  url: _url,
				  data: {},
				  async:false,
				  success: function(data){
					  console.log("ajax.data="+data);
					  if(data){
						  
						var _form = $("#form");
						_form.attr("action","selectList");
						_form.submit();
							
						  //alert("删除成功！");
						  //document.form.action = "catalog!selectList.action";   
					      //document.form.submit();   
					  }
					  jQuery.unblockUI();
				  },
				  dataType: "text",
				  error:function(){
					  	jQuery.unblockUI();
						alert("加载失败，请联系管理员。");
				  }
				});
			}
		}catch(e){
			console.log("eee="+e);
		}
		return false;
	}
	//编辑
	function editSelect(id){
		//document.form1.action = "catalog!toEdit.action?e.id="+node.id;
        //document.form1.submit();
        var _url = "toEdit?id="+id;
        var _form = $("#form");
		_form.attr("action",_url);
		_form.submit();
	}
</script>
	<form action="${basepath}/manage/catalog" name="form" id="form" method="post" theme="simple">
		<input type="hidden" value="${e.type!""}" name="type" id="_type" />
		<table class="table table-bordered table-condensed table-hover">
			<tr>
				<td colspan="16">

					<a href="selectList" class="btn btn-primary">
						<i class="icon-search icon-white"></i> 查询
					</a>
						

					<a href="toAdd?type=${e.type!"a"}" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
						
<!-- 					<input type="button" onclick="editSelect();" value="编辑" class="btn btn-warning" /> -->


					
<!-- 					<input type="button" onclick="deleteSelect();" value="删除" class="btn btn-danger" /> -->

<!-- 						<i class="icon-remove-sign icon-white"></i> 删除 -->

					
				</td>
			</tr>
		</table>
	</form>

	<div class="alert alert-info" style="margin-bottom: 2px;">友情提示：商品目录一般分为两层，大类别、小类别。商品目录编码必须唯一。</div>
	<table id="treegrid" title="商品类别目录" class="table tree table-bordered" style="min-width:800px;min-height:250px">
		<thead>
			<tr>
				<th data-options="field:'id'" nowrap="nowrap">ID</th>
				<th data-options="field:'name'" nowrap="nowrap">类别名称</th>
				<th data-options="field:'order1'" nowrap="nowrap">顺序</th>
				<th data-options="field:'code'" nowrap="nowrap">编码</th>
				<th data-options="field:'showInNavStr'" nowrap="nowrap">是否在导航条显示</th>
				<#if e.type??&& e.type=="a">
				<#else>
					<th data-options="field:'productNum'" nowrap="nowrap" align="right">商品数量</th>
				</#if>
				<th nowrap="nowrap" align="right">操作</th>
			</tr>
			<#list list as item>
            <tr class="treegrid-${item.id} ${(item.pid=="0")?string("","treegrid-parent-"+item.pid)}">
                <td>${item.id!""}</td>
                <td>${item.name!""}</td>
                <td>${item.order1!""}</td>
                <td>${item.code!""}</td>
                <td>${(item.showInNav=="y")?string("是","否")}</td>
				<#if e.type??&& e.type=="a">
				<#else>
                <td>${item.productNum!""}</td>
				</#if>
				<td>
					<button class="btn btn-warning" onclick="return editSelect('${item.id}');">
                    <i class="icon-edit icon-white"></i> 编辑
                	</button>
                    <button method="deletes" class="btn btn-danger" onclick="return deleteSelect('${item.id}');">
                        <i class="icon-remove-sign icon-white"></i> 删除
                    </button>
				</td>
            </tr>
			</#list>
		</thead>
	</table>
	
<link rel="stylesheet" type="text/css" href="${basepath}/resource/jquery-treegrid/css/jquery.treegrid.css">
<#--<link rel="stylesheet" type="text/css" href="${basepath}/resource/jquery-easyui-1.3.4/demo/demo.css">-->
<script type="text/javascript" src="${basepath}/resource/jquery-treegrid/jquery.treegrid.js"></script>
<script type="text/javascript" src="${basepath}/resource/jquery-treegrid/jquery.treegrid.bootstrap3.js"></script>
</@page.pageBase>