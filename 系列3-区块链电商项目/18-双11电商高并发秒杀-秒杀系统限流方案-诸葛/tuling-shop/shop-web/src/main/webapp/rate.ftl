<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<@html.htmlBase>
<style type="text/css">
.topCss {
	height: 28px;
	line-height: 28px;
	background-color: #f8f8f8;
	border-bottom: 1px solid #E6E6E6;
	padding-left: 9px;
	font-size: 14px;
	font-weight: bold;
	position: relative;
	margin-top: 0px;
}
.left_product{
	font-size: 12px;display: inline-block;overflow: hidden;text-overflow: ellipsis;-o-text-overflow: ellipsis;white-space: nowrap;max-width: 150px;
}
img.err-product {
background: url(${systemSetting().defaultProductImg}) no-repeat 50% 50%;
}
</style>
	<@menu.menu selectMenu=""/>

	<div class="container">
	
		<div class="row">
			<div class="col-xs-12">
			
				<#if e.rateOrderdetailList??>
                    <p class="text-success">您可以对下面的商品进行点评，点评后还可以获得一定的积分哦!</p>
                    <hr style="margin-top: 10px;">

                    <form action="${basepath}/order/doRate?orderid=${e.id!""}" id="form" method="post" onsubmit="return checkRate();">
						<#list e.rateOrderdetailList as item>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="media">

                                        <a class="pull-left" href="${basepath}/product/${item.productID!""}" target="_blank" title="${item.productName!""}">
                                            <img class="media-object err-product" style="width: 100px;height: 100px;border: 0px;" alt="" src="${systemSetting().imageRootPath}/${item.picture!""}" onerror="nofind()"/>
                                        </a>
                                        <div class="media-body">
                                            <h5 class="media-heading">对【${item.productName!""}】进行评价：</h5>
                                            <textarea class="form-control" name="product_${item.productID!""}" rows="4" cols="80" ></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
						</#list>

                        <div style="text-align: center;">
                            <input type="submit" id="rateBtn2" class="btn btn-primary" value="我来点评"/>
                        </div>
                    </form>
				<#else>
                    <p class="text-success">感谢您的评价!</p>
                    <hr style="margin-top: 10px;">


                    <div class="panel panel-default">
                        <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
                            <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
                                <div class="text-success" style="font-size: 16px;font-weight: 700;">
                                    <span class="glyphicon glyphicon-ban-circle"></span>&nbsp;您好，您当前的订单已经被点评过了！
                                </div>
                            </div>
                        </div>
                    </div>
				</#if>
			</div>
		</div>
	</div>
	
<script type="text/javascript">
function checkRate(){
	var hasValue = false;
	$("textarea").each(function(){
		if($.trim($(this).val()).length>0){
			hasValue = true;
		}
	});
	if(!hasValue){
		alert("您还没有进行过任何点评！");
		$("textarea:eq(0)").focus();
		return false;
	}
	return true;
}
</script>
</@html.htmlBase>