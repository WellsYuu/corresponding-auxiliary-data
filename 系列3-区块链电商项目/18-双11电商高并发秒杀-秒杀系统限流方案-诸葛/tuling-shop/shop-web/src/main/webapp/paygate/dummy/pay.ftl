<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>

<@html.htmlBase title="确认支付">
    <@menu.menu selectMenu=""/>
<style type="text/css">
    .align-center{
        margin:0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
        width:500px; /* 给个宽度 顶到浏览器的两边就看不出居中效果了 */
        text-align:center; /* 文字等内容居中 */
    }
</style>
<div class="align-center">
<h3>待支付信息</h3>
<table class="table">
    <tr>
        <td>订单号:</td>
        <td>${payInfo.WIDout_trade_no!""}</td>
    </tr>
    <tr>
        <td>商品名称 :</td>
        <td>${payInfo.WIDsubject!""}</td>
    </tr>
    <tr>
        <td>金额:</td>
        <td>${payInfo.WIDprice!""}</td>
    </tr>
    <tr>
        <td>配送费:</td>
        <td>${payInfo.logistics_fee!""}</td>
    </tr>
</table>
<div> <input id="btnPay" type="button" class="btn btn-primary" value="确认支付"/></div>
</div>
<script>
    $(function(){
        $("#btnPay").click(function(){
            if(confirm("确认支付?")) {
                $.ajax({
                    dataType:"json",
                    url:"${basepath}/paygate/dummyPay",
                    type:"POST",
                    data:{orderId:"${payInfo.WIDout_trade_no}"},
                    success:function(data){
                        window.location.href="${basepath}/order/paySuccess.html";
                    },
                    error:function(data){
                        alert("支付失败");
                    }
                });
            }
        });
    })
</script>
</@html.htmlBase>