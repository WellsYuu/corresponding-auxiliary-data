<#import "/manage/tpl/htmlBase.ftl" as html>
<@html.htmlBase>
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
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>商品赠品信息 </strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">赠品名称</td>
				<td style="text-align: left;">
					${e.giftName!""}
				</td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">赠品价值</td>
				<td style="text-align: left;">
					${e.giftPrice!""}
				</td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;">状态</td>
				<td style="text-align: left;">
					<#if e.status??&&e.status=="up">已上架
					<#elseif  e.status??&&e.status=="down">已下架</#if>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">主图</td>   
				<td style="text-align: left;" colspan="3">
					<#if  e.picture??>
						<a target="_blank" href="${systemSetting().imageRootPath}/${e.picture!""}">
							<img src="${systemSetting().imageRootPath}/${e.picture!""}">
						</a>
					</#if>
				</td>
			</tr>

			<#if e.createAccount??>
                <tr>
                    <td style="text-align: right;">添加</td>
                    <td style="text-align: left;">
                        添加人：${e.createAccount!""}<br>
                        添加时间：${e.createtime!""}<br>
                    </td>
                </tr>
			</#if>

			<#if e.updateAccount??>
                <tr>
                    <td style="text-align: right;">最后修改</td>
                    <td style="text-align: left;">
                        修改人：${e.updateAccount!""}<br>
                        修改时间：${e.updatetime!""}<br>
                    </td>
                </tr>
			</#if>
		</table>
</@html.htmlBase>