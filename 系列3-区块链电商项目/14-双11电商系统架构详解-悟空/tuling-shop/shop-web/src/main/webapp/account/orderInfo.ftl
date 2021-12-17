<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="orders">
<style type="text/css">
.centerImageCss{
	width: 560px;
	height: 180px;
}
</style>
<script>
function defaultProductImg(){
	var img=event.srcElement;
	img.src="${systemSetting().defaultProductImg}";
	img.onerror=null; //控制不要一直跳动
}
</script>

<div class="col-xs-9">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading"><b>订单详情</b></div>
            <div class="panel-body" style="line-height: 30px;">
                <table>
                    <tr>
                        <td align="right">订单编号：</td>
                        <td>${e.id!""}</td>
                    </tr>
                    <tr>
                        <td align="right">创建日期：</td>
                        <td>${e.createdate!""}</td>
                    </tr>
                    <tr>
                        <td align="right">订单总金额：</td>
                        <td style="font-weight: 700;font-size: 16px;color: #f50;">${e.amount!""}</td>
                    </tr>
                    <tr>
                        <td align="right">配送费：</td>
                        <td style="font-weight: 700;font-size: 16px;color: #f50;">${e.fee!""}</td>
                    </tr>
                    <tr>
                        <td align="right">配送方式：</td>
                        <td>${e.expressName!""}</td>
                    </tr>
                </table>
            </div>
            <ul class="list-group">
                <li class="list-group-item"><b>配送信息：</b>
                    ${e.ordership.shipname!""},
                    ${e.ordership.shipaddress!""},
                    ${e.ordership.phone!""},
                    ${e.ordership.zip!""}
                </li>

                <#if e.status?? && (e.status=="send" || e.status=="sign")>
                    <li class="list-group-item">
                        <b>快递信息：</b>
                        <a target="_blank" href="http://www.kuaidi100.com/chaxun?com=${e.expressCompanyName!""}&nu=${e.expressNo!""}">快递物流</a>
                        <div style="display: none;">
                            <#if e.kuaid100Info??>
                                ${e.kuaid100Info.message!""}<br>
                                ${e.kuaid100Info.status!""}<br>
                                ${e.kuaid100Info.state!""}<br>

                                <#if e.kuaid100Info.data??>
                                    <#list e.kuaid100Info.data as item>
                                        ${item.time!""}${item.context!""}<br>
                                    </#list>
                                </#if>
                            <#else>
                                没有查询到快递信息.
                            </#if>
                        </div>
                    </li>
                </#if>
            </ul>
            <table class="table ">
                <tr>
                    <th style="text-align: left;">商品</th>
                    <th style="text-align: center;" nowrap="nowrap">数量</th>
                    <th style="text-align: center;" nowrap="nowrap">单价</th>
                </tr>
                <#list e.orders as item>
                    <tr>
                        <td>
                            <div style="width:50px;height: 50px;border: 0px solid;float: left;margin-left: 20px;">
                                <a href="${basepath}/product/${item.productID!""}.html" target="_blank">
                                    <img style="width: 100%;height: 100%;border: 0px;" alt="" src="${systemSetting().imageRootPath}/${item.picture!""}" onerror="defaultProductImg()"/>
                                </a>
                            </div>
                            <div style="float: left;">&nbsp;${item.productName!""}</div>
                        </td>
                        <td style="text-align: center;">&nbsp;${item.productNumber!""}</td>
                        <td style="text-align: center;">&nbsp;${item.price!""}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>

</@accountHtml.html>