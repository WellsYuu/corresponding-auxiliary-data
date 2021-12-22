<#import "/manage/tpl/htmlBase.ftl" as html>
<@html.htmlBase>
<div class="container">
	<div class="row">
		<div class="span12">
			
			<table class="table table-bordered">
				<tr><td colspan="11">订单信息</td></tr>
				<tr>
					<th nowrap="nowrap">订单号</th>
					<th nowrap="nowrap">${e.id!""}</th>
					<th nowrap="nowrap">订单总金额</th>
					<th nowrap="nowrap">${e.amount!""}</th>
					<th nowrap="nowrap">数量</th>
					<th nowrap="nowrap">${e.quantity!""}</th>
				</tr>
				<tr>
					<th nowrap="nowrap">下单日期</th>
					<td nowrap="nowrap">${e.createdate!""}</td>
					<th nowrap="nowrap">配送费</th>
					<th nowrap="nowrap">${e.fee!""}</th>
					<th nowrap="nowrap"></th>
					<th nowrap="nowrap"></th>
				</tr>
				<tr>
					<th nowrap="nowrap">交易平台</th>
					<td nowrap="nowrap" colspan="21">${systemSetting().name}
						(${systemSetting().www })
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">收货人</th>
					<td colspan="21">
						${e.ordership.province!""},
						${e.ordership.city!""},
						${e.ordership.area!""},
						${e.ordership.shipaddress!""},
						${e.ordership.shipname!""}(收)
					</td>
				</tr>
				
				<tr><td colspan="11">&nbsp;</td></tr>
				
				<tr>
					<td width="50px">序号</td>
		<!-- 			<td>商品编号</td> -->
					<td>商品名称</td>
					<td>数量</td>
					<td>单价</td>
					<td>配送费</td>
					<td>小计</td>
				</tr>
				<#list e.orderdetail as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td width="400px">${item.productName!""}</td>
						<td>${item.number!""}</td>
						<td>￥${item.price!""}</td>
						<td>￥${item.fee!""}</td>
						<td>￥${item.total0!""}</td>
						</td>
					</tr>
				</#list>
			</table>
	
		</div>
	</div>	
</div>
	
</body>
</html>

</@html.htmlBase>