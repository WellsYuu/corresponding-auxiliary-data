<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="热门查询管理">

	<div class="alert alert-info">
		提示：对【热门查询】的添加/修改不会立即生效，需要到系统管理--缓存管理页面点击【热门查询关键字】按钮，才能生效。
	</div>
	
	<form action="${basepath}/manage/hotquery" namespace="/manage" theme="simple" name="form" id="form" >
		<input type="hidden" value="${e.type!""}" name="type"/>
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered">
			<tr>
				<td colspan="2" style="text-align: center;">
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
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>热门查询编辑 </strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 100px;">热门查询关键字</td>
				<td style="text-align: left;"><input type="text"  value="${e.key1!""}" name="key1"  id="key1" style="width: 80%;"
				data-rule="热门查询关键字:required;key1;length[1~100];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 100px;">链接</td>
				<td style="text-align: left;"><input type="text"  value="${e.url!""}" name="url"  id="url" style="width: 80%;"
				data-rule="链接:required;url;length[1~100];"/></td>
			</tr>
			
			<#if e.createAccount??>
				<tr>
					<td style="text-align: right;">添加</td>
					<td style="text-align: left;">
						添加人：${e.createAccount!""}<br>
						添加时间：${e.createtime!""}<br>
					</td>
				</tr>
			</#if>
			
			<#if e.updateAccount??>
				<tr>
					<td style="text-align: right;">最后修改</td>
					<td style="text-align: left;">
						修改人：${e.updateAccount!""}<br>
						修改时间：${e.updatetime!""}<br>
					</td>
				</tr>
			</#if>
		</table>
	</form>
	
	<span id="pifeSpan" class="input-group-addon" style="display:none"><%=SystemManager.systemSetting.getImageRootPath()%></span>
	
<script type="text/javascript">
	
	$(function() {
		var ccc = $("#insertOrUpdateMsg").html();
		console.log("insertOrUpdateMsg="+insertOrUpdateMsg);
		if(ccc!='' && ccc.trim().length>0){
			$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
		};
	});
		
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val()+"";//alert(_catalogID);
		if(_catalogID!='' && _catalogID>0){//alert("_catalogID="+_catalogID);
			$("#catalogSelect").val(_catalogID);
		}
	}
</script>

<script>
KindEditor.ready(function(K) {
	var editor = K.editor({
		fileManagerJson : '${basepath}/editor/fileManager'
	});
	K('input[name=filemanager]').click(function() {
		var imagesInputObj = $(this).parent().children("input[ccc=imagesInput]");
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					//K('#picture').val(url);
					//alert(url);
					imagesInputObj.val(url);
					editor.hideDialog();
					clearRootImagePath(imagesInputObj);//$("#picture"));
				}
			});
		});
	});
	
});

</script>

</@page.pageBase>