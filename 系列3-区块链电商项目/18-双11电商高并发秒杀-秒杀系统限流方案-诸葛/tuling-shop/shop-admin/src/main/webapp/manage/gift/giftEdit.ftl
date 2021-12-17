<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="赠品管理">
<style>
.btnCCC{
	background-image: url("../img/glyphicons-halflings-white.png");
	background-position: -288px 0;
}
</style>

	<form action="${basepath}/manage/gift" namespace="/manage" theme="simple" name="form" id="form" >
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
					<strong>商品赠品编辑 </strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">赠品名称</td>
				<td style="text-align: left;"><input type="text"  value="${e.giftName!""}" name="giftName"  style="width: 80%;" id="giftName"
				data-rule="赠品名称:required;giftName;length[1~100];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">赠品价值</td>
				<td style="text-align: left;"><input type="text"  value="${e.giftPrice!""}" name="giftPrice"  id="giftPrice"
				data-rule="赠品价格:required;giftPrice;length[1~100];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">状态</td>
				<td style="text-align: left;">
					<#assign map = {'up':'已上架','down':'已下架'}>
                    <select id="status" name="status" class="input-medium" style="width:100px;">
						<#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
						</#list>
                    </select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">主图</td>   
				<td style="text-align: left;" colspan="3">
					<input type="button" name="filemanager" value="浏览图片" class="btn btn-success"/>
					<input type="text"  value="${e.picture!""}" name="picture" type="text" id="picture" ccc="imagesInput" style="width: 600px;"
					data-rule="小图;required;picture;"/>
					<#if e.picture??>
						<a target="_blank" href="${systemSetting().imageRootPath}/${e.picture!""}">
							<img style="max-width: 50px;max-height: 50px;" alt="" src="${systemSetting().imageRootPath}/${e.picture!""}">
						</a>
					</#if>
				</td>
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
	
	<span id="pifeSpan" class="input-group-addon" style="display:none">${systemSetting().imageRootPath}</span>
	
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
