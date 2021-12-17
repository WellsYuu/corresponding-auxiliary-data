<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="订单管理">
<form id="form" name="form" method="post"  >
    <input type="hidden" name="tradeNo" value="${orderpay.tradeNo}"/>
    <input type="hidden" id="orderId" name="orderId" value="${orderpay.orderid}"/>
    <div class="alert alert-info" style="margin-bottom: 2px;text-align: left;">
        <strong>确认发货</strong>
    </div>
    <table class="table table-bordered">
        <tr>
            <th nowrap="nowrap" width="150px">物流方式</th>
            <td nowrap="nowrap">
                <div class="alert alert-info" id="WIDtransport_type_msg">
                    发货10天后，若对方没有确认收货，交易结束，金额自动进入您的账户。
                </div>
                <select name="WIDtransport_type" id="WIDtransport_type" onchange="changeFunc(this)">
                    <option value="EXPRESS">快递</option>
                    <option value="POST">平邮</option>
                    <option value="no">不需要物流</option>
                </select>
            </td>
        </tr>
        <tr id="companyTR">
            <th nowrap="nowrap">物流公司名称</th>
            <th nowrap="nowrap">
                <select name="expressCompanyName" id="expressCompanyName">
					<#list systemManager().manageExpressMap?keys as item>
                    <option value="${item}">${systemManager().manageExpressMap[item]}</option>
					</#list>
                </select>
            </th>
        </tr>
        <tr>
            <th nowrap="nowrap">运单号</th>
            <th nowrap="nowrap">
                <input name="expressNo" id="expressNo" data-rule="运单号:required;WIDinvoice_no;length[1~20];" maxlength="20" size="20"/>
            </th>
        </tr>
        <#--<tr>-->
            <#--<th nowrap="nowrap">确认发货备注</th>-->
            <#--<th nowrap="nowrap">-->
                <#--<input name="confirmSendProductRemark" id="confirmSendProductRemark"-->
                       <#--data-rule="确认发货备注:confirmSendProductRemark;length[1~50];" maxlength="50" size="50" />-->
            <#--</th>-->
        <#--</tr>-->
        <tr>
            <th nowrap="nowrap" colspan="2" style="text-align: center;">

                <input type="button" class="btn btn-primary" value="确认发货" onclick="return sendProduct()"/>
                <input type="button" value="返回" class="btn btn-default btn-sm" onclick="javascript:history.go(-1);"/>
            </th>
        </tr>
    </table>
</form>

<script type="text/javascript">
    function changeFunc(obj){
        var selectVal = $(obj).val();
        console.log("selectVal="+selectVal);
        $("#companyTR").show();
        if("EXPRESS"==selectVal || "no"==selectVal){
            $("#WIDtransport_type_msg").text("发货10天后，若对方没有确认收货，交易结束，金额自动进入您的账户。");
            if("no"==selectVal){
                $("#companyTR").hide();
            }else{
                $("#expressCompanyName").show();
            }
        }else if("POST"==selectVal){
            $("#WIDtransport_type_msg").text("发货30天后，若对方没有确认收货，交易结束，金额自动进入您的账户。");
            $("#expressCompanyName").hide();
        }
    }

    function sendProduct(){
        //$("form").on("valid.form", function(e, form){
        if($("#expressNo").val()==''){
            alert("运单号不能为空！");
            $("#expressNo").focus();
            return false;
        }

        $.blockUI({ message: "处理中，请稍候...",css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#fff'
        }});

        var _orderid = $("#orderId").val();
        var _url = window.location.href ;
        //alert("_url="+_url);
        $("#form").action = _url;
        var cc = $("#form").serialize();
        console.log("cc="+cc);
        $.ajax({
            url: '${basepath}/manage/order/doOrderSend',
            type: 'post',
            data: cc,
            dataType: 'json',
            //async : false,
            success: function(d){
                console.log("success="+d+",code="+d.success+",error="+d.error);
                if(d.success=="1"){
                    alert("发货成功！");
                    //页面专项明细页面
                    window.location.href = "${basepath}/manage/order/toEdit?id="+_orderid;
                }else if(d.success=="0"){
                    var errorStr = d.error;
//                    if(d.error=="TRADE_NOT_EXIST"){
//                        errorStr = "支付宝中不存在此交易号！";
//                    }else{
//
//                    }
                    alert("请求发货失败！原因："+errorStr);
                    //window.location.href = _url;
                    //window.location.reload();
                }
                jQuery.unblockUI();
                return false;
            },
            error:function(d){
                console.log("error="+d+",code="+d.code+",error="+d.error);
                alert("发送请求失败！请联系站点管理员！");
                jQuery.unblockUI();
            }
        });

        //var ccc = 1/0;
        //return false;
        //});
    }

    $(function(){
    });
</script>

</@page.pageBase>