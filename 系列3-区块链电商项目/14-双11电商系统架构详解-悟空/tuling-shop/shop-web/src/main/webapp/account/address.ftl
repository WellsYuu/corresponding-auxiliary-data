<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
<style type="text/css">
.centerImageCss{
	width: 560px;
	height: 180px;
}
</style>
	<@menu.menu selectMenu=""/>
	<div class="container" style="margin-top: 0px;padding-top: 0px;">
		<div class="row">
			<div class="col-xs-3" style="min-height: 300px;">
				<@accountMenu.accountMenu currentMenu="address"/>
			</div>
			
			<div class="col-xs-9" style="min-height: 200px;">

                <div class="row">
                    <div class="col-xs-12">
                        <ol class="breadcrumb">
                            <li class="active">配送地址</li>
                        </ol>
                    </div>
                </div>

                <hr>
				<div class="row">
					<form role="form" id="form" class="form-horizontal" method="post" action="saveAddress" theme="simple">
					  <input type="hidden" id="id" name="id" value="${address.id!""}"/>
					  <div class="form-group">
					    <label for="name" class="col-lg-2 control-label">收货人姓名</label>
					    <div class="col-lg-6">
					    	<input type="text"  value="${address.name!""}" name="name"  type="text"
						    class="form-control" id="name" data-rule="收货人姓名:required;length[2~8];name;" placeholder="请输入收货人姓名" maxlength="8" size="8"/>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="name" class="col-lg-2 control-label">地址区域</label>
					    <div class="col-lg-3">
                            <select name="province" id="province" class="form-control" onchange="changeProvince()">
                                <option value="">--选择省份--</option>
								<#list provinces as item>
                                    <option value="${item.code}" ${(address.province??&&address.province==item.code)?string("selected", "")}>${item.name}</option>
								</#list>
                            </select>
					    </div>
					    <div class="col-lg-3">
                            <select class="form-control" id="citySelect" name="city" onchange="changeCity()">
                                <option value="">--选择城市--</option>
								<#list cities as item>
                                    <option value="${item.code}" ${(address.city??&&address.city==item.code)?string("selected", "")}>${item.name}</option>
								</#list>
                            </select>
					    </div>
					    
					    <div class="col-lg-3">
                            <select class="form-control" id="areaSelect" name="area">
                                <option value="">--选择区县--</option>
								<#list areas as item>
									<option value="${item.code}" ${(address.area??&&address.area==item.code)?string("selected", "")}>${item.name}</option>
								</#list>
                            </select>
					    </div>
					    
					  </div>
					  <div class="form-group">
					    <label for="address" class="col-lg-2 control-label">地址</label>
					    <div class="col-lg-6">
					    	<input type="text"  value="${address.address!""}" name="address"  type="text"
						    class="form-control" id="address" data-rule="地址:required;length[0~70];address;" placeholder="请输入收货人地址" maxlength="70" size="70"/>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="zip" class="col-lg-2 control-label">邮编</label>
					    <div class="col-lg-6">
					    	<input type="text"  value="${address.zip!""}" name="zip"  type="text"
						    class="form-control" id="zip" data-rule="邮编:required;length[6];zip;" placeholder="请输入收货人邮编" size="6" maxlength="6"/>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="mobile" class="col-lg-2 control-label">手机</label>
					    <div class="col-lg-6">
					    	<input type="text"  value="${address.mobile!""}" name="mobile"  type="text"
						    class="form-control" id="mobile" data-rule="手机:required;length[10~15];mobile;" placeholder="请输入收货人手机" maxlength="15"/>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="phone" class="col-lg-2 control-label">电话号码</label>
					    <div class="col-lg-6">
					    	<input type="text"  value="${address.phone!""}" name="phone"  type="text"
						    class="form-control" id="phone" data-rule="电话号码:required;length[0~15];phone;" placeholder="请输入收货人座机号码" maxlength="15"/>
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-lg-offset-2 col-lg-6">
					      <button type="submit" class="btn btn-success btn-sm" value="保存">
					      	 <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
					      </button>
					    </div>
					  </div>
					</form>
				
					<#if addressList??>
						<table class="table table-bordered table-hover" style="margin-bottom: 10px;">
							<tr style="background-color: #dff0d8">
								<th width="20px" style="display: none;"><input type="checkbox" id="firstCheckbox" /></th>
								<th nowrap="nowrap" style="text-align: center;">收货人</th>
								<th style="text-align: left;">所在区域</th>
								<th style="text-align: left;">街道地址</th>
								<th style="text-align: center;">邮编</th>
								<th style="text-align: center;">电话号码</th>
								<th style="text-align: center;">手机号</th>
								<th nowrap="nowrap" style="text-align: center;">设为默认</th>
								<th style="text-align: center;">操作</th>
							</tr>
							<#list addressList as item>
								<tr>
									<td style="display: none;">${item.id!""}</td>
									<td style="text-align: center;">${item.name!""}</td>
									<td style="text-align: left;">${item.pcadetail!""}</td>
									<td style="text-align: left;">${item.address!""}</td>
									<td style="text-align: center;">${item.zip!""}</td>
									<td style="text-align: center;">${item.phone!""}</td>
									<td style="text-align: center;">${item.mobile!""}</td>
									<td nowrap="nowrap" style="text-align: center;">
										<#if item.isdefault?? && item.isdefault=="y">
											<input type="radio" current="1" name="setDefaultRadio" value="${item.id!""}" checked="checked"/>
										<#else>
											<input type="radio" name="setDefaultRadio" value="${item.id!""}"/>
										</#if>
									</td>
									<td style="text-align: center;" nowrap="nowrap">
										<a href="${basepath}/account/editAddress?id=${item.id!""}">
											修改
										</a>|
										<a onclick="return deletes();" href="${basepath}/account/deleteAddress?id=${item.id!""}">
											删除
										</a>
									</td>
								</tr>
							</#list>
						</table>
					<#else>
						<!-- 
						<div class="bs-callout bs-callout-danger author" style="text-align: left;font-size: 14px;margin: 2px 0px;">
							还没有任何配送信息！赶紧添加吧。
						</div>
						 -->
						
						<div class="col-xs-12">
							<hr>
							<div class="row">
								<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
									<div class="panel panel-default">
							              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
								              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
								              		<span class="glyphicon glyphicon-user"></span>亲，还没有任何配送信息哦！赶紧添加吧。
								              </div>
							              </div>
									</div>
									<hr>
								</div>
							</div>
							
						</div>
						
					</#if>
					
				</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">
$(function() {
	$("input[name=setDefaultRadio]").click(function(){
		var $this = $(this);
		var current = $this.attr("current");
		if(current == "1") {
			return false;
		}
		var _url = "setAddressDefault";
		//alert(_url);
		$.ajax(_url,{
		  type: 'POST',
		  data: {id:$this.val()},
		  success: function(data){
              $(":radio[name=setDefaultRadio][current=1]").attr("current", "0");
              $this.attr("current", "1");
			  alert("修改默认地址成功！");
		  },
		  dataType: "json",
		  error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("操作失败，请联系管理员或更换浏览器再试!");				  
		  }
		});
	});
});
function search(){
	var _key = $.trim($("#key").val());
	if(_key==''){
		return false;
	}
	$("#searchForm").submit();
}
function deletes(){
	return confirm("确定删除选择的记录?");
}
function changeProvince(){
	var selectVal = $("#province").val();
	if(!selectVal){
		console.log("return;");
		return;
	}
	var _url = "selectCitysByProvinceCode?provinceCode="+selectVal;
	console.log("_url="+_url);
	$("#citySelect").empty().show().append("<option value=''>--选择城市--</option>");
	$("#areaSelect").empty().show().append("<option value=''>--选择区县--</option>");
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  dataType: "json",
	  success: function(data){
		  //console.log("changeProvince success!data = "+data);
		  $.each(data,function(index,value){
			  //console.log("index="+index+",value="+value.code+","+value.name);
			  $("#citySelect").append("<option value='"+value.code+"'>"+value.name+"</option>");
		  });
	  },
	  error:function(er){
		  console.log("changeProvince error!er = "+er);
	  }
	});
}

function changeCity(){
	var selectProvinceVal = $("#province").val();
	var selectCityVal = $("#citySelect").val();
	if(!selectProvinceVal || !selectCityVal){
		console.log("return;");
		return;
	}
	var _url = "selectAreaListByCityCode?provinceCode="+selectProvinceVal+"&cityCode="+selectCityVal;
	console.log("_url="+_url);
	$("#areaSelect").empty().show().append("<option value=''>--选择区县--</option>");
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  dataType: "json",
	  success: function(data){
		  //console.log("changeProvince success!data = "+data);
		  $.each(data,function(index,value){
			  //console.log("index="+index+",value="+value.code+","+value.name);
			  $("#areaSelect").append("<option value='"+value.code+"'>"+value.name+"</option>");
		  });
	  },
	  error:function(er){
		  console.log("changeCity error!er = "+er);
	  }
	});
}
</script>
</@html.htmlBase>