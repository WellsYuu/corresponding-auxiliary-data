//common
String.prototype.endsWith = function (pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.lastIndexOf(pattern) === d;
}

//删除图片主路径
function clearRootImagePath(picInput){
	var _imgVal = picInput.val();
	if(_imgVal.indexOf(imageRootPath) >= 0) {
		picInput.val(_imgVal.substring(imageRootPath.length));
	}
}
/**
 * 后台脚本JS
 */

$(function(){
	//后台查询页面 全选/全不选 功能	jQuery v1.9
	$("#firstCheckbox").on("click",function(){
		console.log("check="+$(this).prop("checked"));
		if($(this).prop("checked")){
			$("input[type=checkbox]").prop("checked",true);
		}else{
			$("input[type=checkbox]").prop("checked", false);
		}
	});
	
	//为了使用bootstrap2的图标功能，只有牺牲使用struts2的s:submit方式提交表单。
	//这里对s:form表单的action进行重新组装，加上了你点击的按钮的method="update"方法，最后验证通过则提交表单。
	$("form").on('click', 'button', function(e){
	    this.form.buttonMethod = $(this).attr('method');
	});
	//onclick="doSubmitFuncWhenButton(this)"
	
	//通用按钮的提交表单事件
	$("form").on("valid.form", function(e, form){
        var buttonMethod = typeof(form.buttonMethod)=="undefined" ? "" : form.buttonMethod;
		var _formAction = $(form).attr("action");
		if(buttonMethod != "") {
			_formAction = _formAction.endsWith("/")?_formAction : _formAction + "/";
			_formAction += buttonMethod;
		}
		$(form).attr("action",_formAction);
        $(form).attr("method", "POST");
		
		$.blockUI({ message: "系统处理中，请等待...",css: {
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        }});
		createMark();
		form.submit();
	});
	setTimeout(function(){
		$('#alert-success').alert("close");
		$('#alert-warning').alert("close");
		$('#alert-danger').alert("close");
	}, 3000);
		
});

//创建遮罩效果
function createMark(){
	$.blockUI({ message: "系统处理中，请等待...",css: { 
        border: 'none', 
        padding: '15px', 
        backgroundColor: '#000', 
        '-webkit-border-radius': '10px', 
        '-moz-border-radius': '10px', 
        opacity: .5, 
        color: '#fff' 
    }});
}

//查询
function selectList(obj){
	var tableId = $(obj).attr("table-id");
	var table;
	if(tableId && (table=$("#"+tableId).DataTable({"retrieve":true}))) {
		table.on('preXhr.dt', function ( e, settings, data ) {
			$.each($("form").serializeArray(),function(ix,v){
				data[v.name]= v.value;
			});
		} );
		table.ajax.reload();
		return false;
	}
	console.log("selectList...");
	var _form = $("form");
	_form.attr("action",$(obj).attr("method"));
	_form.submit();
}

//批量删除选择的记录
function submitIDs(obj,tip){
	console.log("submitIDs...");
	if ($("input:checked").size() == 0) {
		alert("请先选择要操作的内容！");
		return false;
	}

	if(confirm(tip)){
		createMark();
		var _form = $("form");
		_form.attr("action",$(obj).attr("method"));
		_form.submit();
	}
	return false;
}

//不需要任何验证的提交    
function submitNotValid2222(obj){
	createMark();
	console.log("submitNotValid2222...");
	var _form = $("form");
	_form.attr("action",$(obj).attr("method"));
	_form.submit();
}



//为了使用bootstrap2的图标功能，只有牺牲使用struts2的s:submit方式提交表单。
//这里对s:form表单的action进行重新组装，加上了你点击的按钮的method="update"方法，最后验证通过则提交表单。
function doSubmitFuncWhenButton(obj){
	/*
	$("#form").validator({
		
		valid: function(form){
			var me = this;
	        // ajax提交表单之前，先禁用submit
	        me.holdSubmit();
	        $(form).find('button').css('color', '#999').text('正在提交..');
	        
			this.isAjaxSubmit = false;
			var method = $(obj).attr("method");
			console.log(method);
			var _formAction = $(form).attr("action");
			var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
			console.log(aa);
			
			var lastFormAction = aa+method;//aa +"!" +method+".action";
			console.log("lastFormAction="+lastFormAction);
			$(form).attr("action",lastFormAction);
			
			console.log($(form).attr("action"));
			
			form.submit();
			
			me.holdSubmit(false);
		}
	});
	*/
	
	$("#form").on("valid.form", function(e, form){
		console.log(this.isValid);
		//if(this.isValid && this.isValid==true){
	        console.log("submit...");
		//}
	});
	
	$("#form").on("valid.form", function(e, form){
		console.log(this.isValid);
		if(this.isValid && this.isValid==true){
			
			//var me = this;
	        // ajax提交表单之前，先禁用submit
	        //me.holdSubmit();
	        //$(form).find('button').css('color', '#999').text('正在提交..');
	        console.log("submit...");
			/*
			this.isAjaxSubmit = false;
			var method = $(obj).attr("method");
			console.log(method);
			var _formAction = $(form).attr("action");
			var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
			console.log(aa);
			
			var lastFormAction = aa+method;
			console.log("lastFormAction="+lastFormAction);
			$(form).attr("action",lastFormAction);
			
			console.log($(form).attr("action"));
			
			form.submit();
			*/
			
			//me.holdSubmit(false);
		}
	});
	
}
