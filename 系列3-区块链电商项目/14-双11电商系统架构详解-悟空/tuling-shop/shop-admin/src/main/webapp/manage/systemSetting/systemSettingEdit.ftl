<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="系统设置">
<style type="text/css">
input,textarea{
	width: 80%;
}

.leftTd{
	text-align: right;width: 100px
}

.rightTd{
	text-align: left;
}
</style>
<script type="text/javascript">
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	changeStyle();
});
function changeStyle(){
	var _png = "${basepath}/resource/bootstrap3.3.4/style/"+$("#style").val()+".png";
	$("#styleImg").attr("src",_png);
	$("#ceshiA").attr("href",$("#base_input").val()+$("#style").val());
}
</script>
</head>

<body style="padding: 5px;">
<form action="${basepath}/manage/systemSetting/" id="form">

<div style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
	<button method="insertOrUpdate" class="btn btn-success">
		<i class="icon-ok icon-white"></i> 保存
	</button>
</div>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">基本设置</a></li>
		<li><a href="#tabs-2">图片设置</a></li>
		<li><a href="#tabs-3">样式设置</a></li>
<!-- 		<li><a href="#tabs-4">邮件格式</a></li> -->
		<li><a href="#tabs-5">QQ店小二设置</a></li>
		<li><a href="#tabs-6">图集</a></li>
<!-- 		<li><a href="#tabs-7">信任登陆配置</a></li> -->
<!-- 		<li><a href="#tabs-8">门户数据配置</a></li> -->
	</ul>
	<div id="tabs-1">
		<table class="table table-condensed">
					<tr style="display: none;">
						<td>id</td>
						<td><input type="hidden" name="id" value="${e.id!""}" id="id"/></td>
					</tr>
					<tr>
						<td style="text-align: right;width: 100px">系统版本</td>
						<td style="text-align: left;"><input type="text" name="version" value="${e.version!""}" id="version" data-rule="required;version;length[0~100];"/>
							</td>
					</tr>
					<tr>
						<td style="text-align: right;width: 100px">系统代号</td>
						<td style="text-align: left;"><input type="text"  name="systemCode" value="${e.systemCode!""}"
								id="systemCode" data-rule="required;systemCode;length[1~30];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;width: 100px">名称</td>
						<td style="text-align: left;"><input type="text"  name="name" value="${e.name!""}"
								id="name" data-rule="required;name;length[1~30];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">门户地址</td>   
						<td style="text-align: left;"><input type="text"  name="www" size="100" value="${e.www!""}"
								id="www" data-rule="required;www;length[1~100];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">后台地址</td>   
						<td style="text-align: left;"><input type="text"  name="manageHttp" size="100"  value="${e.manageHttp!""}"
								id="manageHttp" data-rule="required;manageHttp;length[1~100];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">log</td>
						<td style="text-align: left;"><input type="text"  name="log"  value="${e.log!""}"
								id="log" data-rule="required;log;length[0~100];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">网站标题</td>
						<td style="text-align: left;"><input type="text"  name="title"  value="${e.title!""}"
								id="title" data-rule="required;title;length[0~100];"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">description</td>
						<td style="text-align: left;">
                            <input type="text"  id="description" name="description" value="${e.description!""}" data-rule="required;description;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">keywords</td>
						<td style="text-align: left;">
							<input type="text"  id="keywords" name="keywords" value="${e.keywords!""}" data-rule="required;keywords;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">shortcuticon</td>
						<td style="text-align: left;">
							<input type="text"  id="shortcuticon" name="shortcuticon" value="${e.shortcuticon!""}" data-rule="required;shortcuticon;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">联系地址</td>
						<td style="text-align: left;">
							<input type="text"  id="address" name="address" value="${e.address!""}" data-rule="required;address;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">联系电话</td>
						<td style="text-align: left;">
							<input type="text"  id="tel" name="tel"  value="${e.tel!""}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">邮箱</td>
						<td style="text-align: left;">
							<input type="text"  id="email" name="email" value="${e.email!""}" data-rule="required;email;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">备案号</td>
						<td style="text-align: left;">
							<input type="text"  id="icp" name="icp"  value="${e.icp!""}" data-rule="required;icp;length[0~100];"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">站长统计代码</td>
						<td style="text-align: left;">
							<textarea id="statisticsCode" cols="300" rows="6" name="statisticsCode" data-rule="required;statisticsCode;length[10~1000];">${e.statisticsCode!""}</textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">是否开放网站</td>
						<td style="text-align: left;">
							<input type="checkbox" id="isopen" name="isopen" value="true" <#if e.isopen=="true">checked="checked"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">关闭信息</td>
						<td style="text-align: left;">
							<textarea id="closeMsg" name="closeMsg" cols="300" rows="6">${e.closeMsg!""}</textarea>
						</td>
					</tr>
				</table>
	</div>
	<div id="tabs-2">
		<table class="table table-condensed">
			<tr>
				<td style="text-align: right;width: 100px">是否启用响应式</td>
				<td style="text-align: left;">
					<#assign y_n = {'y':'启用','n':'禁用'}>
					<select id="unit" name="openResponsive">
					<#list y_n?keys as key>
					    <option value="${key}" <#if e.openResponsive?? && e.openResponsive==key>selected="selected" </#if>>${y_n[key]}</option>
					</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;width: 100px">图片根路径</td>
				<td style="text-align: left;"><input type="text"  name="imageRootPath"  value="${e.imageRootPath!""}"
						id="imageRootPath" data-rule="required;imageRootPath;length[0~100];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">产品默认图片</td>
				<td style="text-align: left;"><input type="text"  name="defaultProductImg"  value="${e.defaultProductImg!""}"
						id="defaultProductImg" data-rule="required;defaultProductImg;length[0~100];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 100px">后台左侧菜单叶子节点的图标</td>
				<td style="text-align: left;"><input type="text"  name="manageLeftTreeLeafIcon"  value="${e.manageLeftTreeLeafIcon!""}"
						id="manageLeftTreeLeafIcon" data-rule="required;manageLeftTreeLeafIcon;length[0~100];"/></td>
			</tr>
		</table>
	</div>
	<div id="tabs-3">
		<table class="table table-condensed">
			<tr>
				<td style="text-align: right;">选择样式</td>
				<td style="text-align: left;">

					<#assign styles = {'amelia':'amelia','cerulean':'cerulean','cosmo':'cosmo','cyborg':'cyborg','default':'default','flatly':'flatly','journal':'journal','readable':'readable','simplex':'simplex','slate':'slate','spacelab':'spacelab','united':'united','yeti':'yeti'}>
                    <select id="style" name="style" class="input-medium" onchange="changeStyle()" >
					<#list styles?keys as key>
                        <option value="${key}" <#if e.style?? && e.style==key>selected="selected" </#if>>${styles[key]}</option>
					</#list>
                    </select>
					<br>
					<a id="ceshiA" target="_blank">
						<img alt="" src="" id="styleImg" style="border: 1px solid #aaa;" >
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="tabs-5">
		<table class="table table-bordered">
			<tr>
				<td style="text-align: left;" colspan="2">
					<div class="alert alert-info">
						请在下面填写站点客服的QQ组建HTML内容。可以从QQ的网站(http://connect.qq.com/intro/wpa)上取得HTML内容。号码和内容描述可以自己修改。
					</div>
					<textarea name="qqHelpHtml" style="width:100%;height:200px;visibility:hidden;">${e.qqHelpHtml!""}</textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="tabs-6">
		<div>
			<div class="alert alert-info">图集：如果广告位的属性设置为图集随机优先，则广告位的位置会优先选择显示图集中的图片。选择图片的方式为随机选取。</div>
			<table class="table table-bordered">
				<tr>
					<td colspan="11">
						<input style="display: none;" onclick="addTrFunc();" value="添加" class="btn btn-warning" type="button"/>
						<button method="deleteImageByImgPaths" onclick="return deleteImageByImgPaths(this);"
								class="btn btn-primary">删除</button>
							
					</td>
				</tr>
				<tr style="background-color: #dff0d8">
					<th width="50"><input type="checkbox" id="firstCheckbox" /></th>
					<th>图片地址</th>
				</tr>
				<#list e.imagesList as img>
					<tr>
						<td><input type="checkbox" name="imagePaths"
								value="${img}" /></td>
						<td>
							<a href="${systemSetting().imageRootPath}/${img}" target="_blank">
								<img style="max-width: 100px;max-height: 100px;" alt="" src="${systemSetting().imageRootPath}/${img}">
							</a>
						</td>
					</tr>
				</#list>
			</table>
		</div>
		<br>
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td>文件</td>
			</tr>
			<tr id="firstTr">
				<td>
					<#list [0..10] as item>
					<div>
						<input type="button" name="filemanager" value="浏览图片" class="btn btn-warning"/>
						<input type="text" ccc="imagesInput" name="images" style="width: 80%;" />
					</div>
					</#list>
				</td>
			</tr>
		</table>
	</div>
	
</div>
</form>
<input value="${basepath}/?style=" id="base_input" style="display: none;"/>
<script>
	var qqHelpHtml;
	KindEditor.ready(function(K) {
		qqHelpHtml = K.create('textarea[name="qqHelpHtml"]', {
			allowFileManager : true,
            uploadJson : '${basepath}/editor/upload',
            fileManagerJson : '${basepath}/editor/fileManager'
		});
		K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		K('input[name=isEmpty]').click(function(e) {
			alert(editor.isEmpty());
		});
		K('input[name=getText]').click(function(e) {
			alert(editor.text());
		});
		K('input[name=selectedHtml]').click(function(e) {
			alert(editor.selectedHtml());
		});
		K('input[name=setHtml]').click(function(e) {
			editor.html('<h3>Hello KindEditor</h3>');
		});
		K('input[name=setText]').click(function(e) {
			editor.text('<h3>Hello KindEditor</h3>');
		});
		K('input[name=insertHtml]').click(function(e) {
			editor.insertHtml('<strong>插入HTML</strong>');
		});
		K('input[name=appendHtml]').click(function(e) {
			editor.appendHtml('<strong>添加HTML</strong>');
		});
		K('input[name=clear]').click(function(e) {
			editor.html('');
		});
	});
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

function deleteImageByImgPaths(obj){
	if ($("input:checked").size() == 0) {
		alert("请选择要删除的图片！");
		return false;
	}
	if(confirm("确定删除选择的图片吗?")){
		return true;
	}
	return false;
	
}
</script>

</@page.pageBase>