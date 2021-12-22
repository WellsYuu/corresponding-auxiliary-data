<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="订单管理">
<body>
<div class="container" style="margin-top: 0px;padding-top: 0px;">
    <div class="row">
        <div class="col-md-9" style="min-height: 200px;">
            <div class="row">

                <div class="alert alert-info">
                    修改订单配送地址信息
                </div>
                <form action="${basepath}/manage/order/updateOrdership" method="post" name="form" role="form" id="form">
                    <input type="hidden" name="id" value="${e.ordership.orderid }"/>
                    <input type="hidden" name="ordership.id" value="${e.ordership.id }"/>
                    <table class="table table-bordered">
                        <tr>
                            <td width="100px">收货人姓名</td>
                            <td><input name="ordership.shipname" type="text" value="${e.ordership.shipname}"
                                       id="name" data-rule="收货人姓名:required;length[2~8];name;" placeholder="请输入收货人姓名" /></td>
                        </tr>
                        <tr>
                            <td>地址区域</td>
                            <td>
                                <input type="hidden"  name="ordership.province" value="${e.ordership.province!"" }" id="provinceName"/>
                                <select onchange="changeProvince()" name="ordership.provinceCode" id="province" >
                                    <#if !e.ordership.provinceCode??>
                                        <option value="">--选择省份--</option>
                                    </#if>
                                    <#list provinces as item>
                                        <option value="${item.code}" <#if e.ordership.provinceCode?? && item.code == e.ordership.provinceCode > selected='selected' </#if>>${item.name}</option>
                                    </#list>
                                </select>
                                <input type="hidden"  name="ordership.city" value="${e.ordership.city!"" }" id="cityName"/>
                                <select onchange="changeCity()" id="citySelect" name="ordership.cityCode" >
                                        <#if !e.ordership.cityCode??>
                                            <option value="">--选择城市--</option>
                                        </#if>
                                    <#list cities as item>
                                        <option value="${item.code}" <#if e.ordership.cityCode?? && item.code == e.ordership.cityCode> selected='selected' </#if>>${item.name}</option>
                                    </#list>
                                </select>
                                <input type="hidden" name="ordership.area" value="${e.ordership.area!"" }" id="areaName"/>
                                <select onchange="areaCity()" id="areaSelect" name="ordership.areaCode" >
                                        <#if !e.ordership.areaCode??>
                                            <option value="">--选择区域--</option>
                                        </#if>
                                    <#list areas as item>
                                        <option value="${item.code}" <#if e.ordership.areaCode?? && item.code == e.ordership.areaCode> selected='selected' </#if>>${item.name}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>详细地址</td>
                            <td>
								<textarea class="form-control" name="ordership.shipaddress" type="text"
                                          id="address"  data-rule="地址:required;length[0~70];address;" placeholder="请输入收货人地址" >${e.ordership.shipaddress}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>邮编</td>
                            <td><input name="ordership.zip" type="text"  value="${e.ordership.zip}"
                                       id="zip" data-rule="邮编:required;length[0~70];zip;" placeholder="请输入收货人邮编" /></td>
                        </tr>
                        <tr>
                            <td>手机</td>
                            <td><input name="ordership.phone" type="text" value="${e.ordership.phone}"
                                       id="mobile" data-rule="手机:required;length[0~70];mobile;" placeholder="请输入收货人手机" /></td>
                        </tr>
                        <tr>
                            <td>电话号码</td>
                            <td><input name="ordership.tel" type="text" value="${e.ordership.tel}"
                                       id="phone" data-rule="电话号码:required;length[0~70];phone;" placeholder="请输入收货人座机号码" /></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <button type="submit" class="btn btn-success btn-sm" value="保存">
                                    <span class="glyphicon glyphicon-ok"></span>确认修改
                                </button>
                                <input type="button" value="返回" class="btn btn-default btn-sm" onclick="javascript:history.go(-1);"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {

    });

    function changeProvince(){
        var selectVal = $("#province").val();
        $("#provinceName").val($("#province").find("option:selected").text());
        if(!selectVal){
            console.log("return;");
            return;
        }
        var _url = "${basepath}/manage/order/selectCitysByProvinceCode?provinceCode="+selectVal;
        console.log("_url="+_url);
        $("#citySelect").empty().show().append("<option value=''>--选择城市--</option>");
        $("#areaSelect").empty().hide().append("<option value=''>--选择区域--</option>");
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
        $("#cityName").val($("#citySelect").find("option:selected").text());
        if(!selectProvinceVal || !selectCityVal){
            console.log("return;");
            return;
        }
        var _url = "${basepath}/manage/order/selectAreaListByCityCode?provinceCode="+selectProvinceVal+"&cityCode="+selectCityVal;
        console.log("_url="+_url);
        $("#areaSelect").empty().show().append("<option value=''>--选择区域--</option>");
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
    function areaCity(){
        $("#areaName").val($("#areaSelect").find("option:selected").text());
    }
</script>

</@page.pageBase>