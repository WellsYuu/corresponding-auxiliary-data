<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu=(e.type=="help")?string("文章管理","公告管理")>
<style>
#insertOrUpdateMsg{
border: 0px solid #aaa;margin: 0px;position: fixed;top: 0;width: 100%;
background-color: #d1d1d1;display: none;height: 30px;z-index: 9999;font-size: 18px;color: red;
}
.btnCCC{
	background-image: url("../img/glyphicons-halflings-white.png");
	background-position: -288px 0;
}
</style>
<script>
$(function(){
	<#if e.id??>
	var id = "${e.id}";
	$("#btnStatic").click(function(){
		$.post("${basepath}/freemarker/create?method=staticNewsByID&id="+id, null ,function(response){
			alert(response == "success" ? "操作成功！" : "操作失败!");
		});
	});
	</#if>
});
</script>
	<form action="${basepath}/manage/news" namespace="/manage" theme="simple" name="form" id="form" method="post">
		<input type="hidden" value="${e.type!""}" name="type"/>
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered">
			<tr>
				<td colspan="2" style="text-align: center;">
					<#if e.id??>
                        文章ID：<span class="badge badge-success">${e.id!""}</span>
                        <button method="update" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 保存
                        </button>


                        <#if e.status??&&e.status=="y">
                        <a action="news" id="btnDown" href="down?id=${e.id}" class="btn btn-warning" onclick="return confirm(\"确定不显示此文章吗?\");">
                        <i class="icon-arrow-down icon-white"></i> 不显示</a>
                        <#else>
                            <a action="news" id="btnUp" href="up?id=${e.id}" class="btn btn-warning" onclick="return confirm(\"确定显示此文章吗?\");">
                            <i class="icon-arrow-up icon-white"></i> 显示</a>
                        </#if>

                        <#if e.type??&&e.type=="notice">
                        <a class="btn btn-info" target="_blank" href="${systemSetting().www}/news/${e.id!""}.html">
                        <i class="icon-eye-open icon-white"></i> 查看</a>
                        <#elseif e.type??&&e.type=="help">
                        <a class="btn btn-info" target="_blank" href="${systemSetting().www}/help/${e.code!""}.html">
                        <i class="icon-eye-open icon-white"></i> 查看</a>
                        </#if>
                        <a id="btnStatic" href="#" class="btn btn-warning">
                        <i class="icon-refresh icon-white"></i> 静态化</a>
					<#else>
                        <button method="insert" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 新增
                        </button>
					</#if>
				</td>
			</tr>
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>文章内容编辑 </strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<#if e.type??&&e.type=="help">
				<tr>
					<td style="text-align: right;">类别</td>
					<td>
						<select onchange="catalogChange(this)" name="catalogID" id="catalogSelect" data-rule="类别:required;catalogSelect;">
							<option></option>
                            <#list catalogsArticle as item>
								<option pid="0" <#if e.catalogID?? && item.id==e.catalogID>selected="selected" </#if> value="${item.id!""}"><font color='red'>${item.name!""}</font></option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">文章code</td>
					<td style="text-align: left;"><input type="text"  value="${e.code!""}" name="code"  data-rule="文章编码:required;code;length[1~25];remote[unique]"
							id="code" /><br>
						(例如：[新手帮助]的编码为xsbz，或者输入别的字符，但是必须唯一，最好不要使用中文。)		
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">顺序</td>
					<td style="text-align: left;"><input type="text"  value="${e.order1!""}" name="order1"  data-rule="顺序:integer;order1;length[1~5];"
							id="order1" /></td>
				</tr>
			</#if>
			<tr>
				<td style="text-align: right;width: 80px;">标题</td>
				<td style="text-align: left;"><input type="text" value="${e.title!""}" name="title" style="width: 80%;" id="title"
				data-rule="标题:required;title;length[1~45];"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">内容</td>
				<td style="text-align: left;">
					<textarea name="content" style="width:100%;height:400px;visibility:hidden;" id="content"
					data-rule="内容:required;content;">${e.content!""}</textarea>
				</td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$(function() {
		//$("#title").focus();
		selectDefaultCatalog();
	});
	function doSubmitFunc(obj){
			var m = $(obj).attr("name");
			console.log(m);
			console.log(m.split(":")[1]+".action");
			
			$("#form").on("valid.form", function(e, form){
				var _formAction = $("#form").attr("action");
				var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
				console.log(aa);
				
				var lastFormAction = aa + m.split(":")[1]+".action";
				$("#form").attr("action",lastFormAction);
				
				console.log($("#form").attr("action"));
				console.log(this.isValid);
				//form.submit();
			});
	}
	
	
	
	function doSubmitFuncByLink(obj){
		var _href = $(obj).attr("href");
		var _form = $("#form");
		_form.attr("action",_href);
		
		console.log("_href="+_href);
		
		$("#form").on("valid.form", function(e, form){
			console.log("this.isValid="+this.isValid);
			
			
			//_form.submit();
		});
		//_form.submit();
		return false;
	}

	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val()+"";//alert(_catalogID);
		if(_catalogID!='' && _catalogID>0){//alert("_catalogID="+_catalogID);
			$("#catalogSelect").val(_catalogID);
		}
	}
</script>

<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
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
</@page.pageBase>