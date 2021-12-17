<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
	<@menu.menu selectMenu=""/>
	<div class="container">
		<div class="row">
			<div class="col-xs-3">
				<@accountMenu.accountMenu currentMenu="favorite"/>
			</div>
			
			<div class="col-xs-9">
                <div class="row">
                    <div class="col-xs-12">
                        <ol class="breadcrumb">
                            <li class="active">收藏夹</li>
                        </ol>
                    </div>
                </div>

                <hr>
				<div class="row">
				<#if pager.list??>
					<table class="table table-bordered">
						<tr>
							<td>商品</td>
							<td width="100px">收藏日期</td>
						</tr>
						<#list pager.list as item>
							<#if item.product??>
								<tr>
									<td>
										<a href="${basepath}/product/${item.productID!""}" target="_blank" title="${item.product.name!""}">
											<img class="err-product" onerror="defaultProductImg()" style="width: 50px;height: 50px;border: 0px;" alt="" src="${systemSetting().imageRootPath}/${item.product.picture!""}" />
										</a>
										<div style="display: inline-block;">
											${item.product.name!""}<br>
											${item.product.nowPrice!""}
										</div>
									</td>
									<td>${item.createtime!""}</td>
								</tr>
							</#if>
						</#list>
					</table>
				<#else>
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-12">
								<ol class="breadcrumb">
								  <li class="active">收藏夹</li>
								</ol>
							</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
								<div class="panel panel-default">
						              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
							              <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
							              		<span class="glyphicon glyphicon-user"></span>亲，您暂时没有收藏任何商品哦，赶紧去收藏几个吧！
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
</@html.htmlBase>