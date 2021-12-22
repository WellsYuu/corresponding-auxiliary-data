<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="评论管理">
	<form action="${basepath}/manage/comment" method="post" theme="simple">
				<table class="table table-bordered">
					<tr>
						<#--<!-- -->
						<#--<td style="text-align: right;">评论等级</td>-->
						<#--<td style="text-align: left;">-->
							<#--<s:select list="#{0:'好评',1:'中评',2:'差评'}" id="star" name="star"  class="input-medium" -->
								<#--headerKey="" headerValue=""-->
								<#--listKey="key" listValue="value"  />-->
						<#--</td>-->
						 <#--&ndash;&gt;-->
						<td style="text-align: right;">商品编号</td>
						<td style="text-align: left;">
							<input type="text" value="${e.productID!""}" name="productID" class="input-medium" />
						</td>
						
						<td style="text-align: right;">会员账号</td>
						<td style="text-align: left;">
                            <input type="text" value="${e.account!""}" name="account" class="input-medium" />
						</td>
						
						<td style="text-align: right;">订单编号</td>
						<td style="text-align: left;">
                            <input type="text" value="${e.orderID!""}" name="orderID" class="input-medium" />
						</td>
						
						<td style="text-align: right;">是否显示</td>
						<td style="text-align: left;">
							<#assign map = {'':'','y':'是','n':'否'}>
                            <select id="status" name="status" class="input-medium">
								<#list map?keys as key>
                                    <option value="${key}" <#if e.status?? && e.status==key>selected="selected" </#if>>${map[key]}</option>
								</#list>
                            </select>
						</td>
					</tr>
					<tr>
						<td colspan="16">
<#--<%-- 							<s:submit method="selectList" value="查询" class="btn btn-primary"> --%>-->
<!-- 								<i class="icon-search"></i> -->
<#--<%-- 							</s:submit> --%>-->
<#--<%-- 							<s:a method="selectList" class="btn btn-primary"> --%>-->
<!-- 								<i class="icon-search icon-white"></i> 查询 -->
<#--<%-- 							</s:a> --%>-->
							<button method="selectList" class="btn btn-primary" onclick="selectList(this)">
								<i class="icon-search icon-white"></i> 查询
							</button>
								
							<button method="updateStatusY" class="btn btn-warning" onclick="return submitIDs(this,'确定显示指定的记录吗？');">
								<i class="icon-arrow-up icon-white"></i> 显示
							</button>
							<button method="updateStatusN" class="btn btn-warning" onclick="return submitIDs(this,'确定不显示指定的记录吗？');">
								<i class="icon-arrow-down icon-white"></i> 不显示
							</button>
								
							<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
								<#include "/manage/system/pager.ftl"/>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="table table-bordered table-hover">
					<tr style="background-color: #dff0d8">
						<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
						<th style="display: none;">编号</th>
						<th nowrap="nowrap">商品编号</th>
						<th nowrap="nowrap">会员账号</th>
						<th nowrap="nowrap">订单编号</th>
						<th>评论内容</th>
						<th nowrap="nowrap">评论日期</th>
						<th nowrap="nowrap">评论星级</th>
						<th nowrap="nowrap">是否显示</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<#list pager.list as item>
						<tr>
							<td><input type="checkbox" name="ids"
								value="${item.id!""}"></td>
							<td style="display: none;">&nbsp;${item.id!""}</td>
							<td>
<#--<%-- 								&nbsp;<a target="_blank" href="product!toEdit.action?e.id=${item.productID!""}">${item.productID!""}</a> --%>-->
								<a target="_blank" href="${systemSetting().www}/product/${item.productID!""}.html">${item.productID!""}</a>
							</td>
							<td>
								<a target="_blank" href="${basepath}/manage/account/show?account=${item.account!""}">${item.account!""}
								</a>
							</td>
							<td>&nbsp;
								<a target="_blank" href="${basepath}/manage/order/toEdit?type=show&id=${item.orderID!""}">${item.orderID!""}</a>
							</td>
							<td width="500px">&nbsp;
								${item.content!""}<br>
								
								<#if item.reply??>
									<span style="color:#f50">【已回复】</span>： ${item.reply!""}
								</#if>
							</td>
							<td nowrap="nowrap">&nbsp;${item.createdate!""}</td>
							<td>&nbsp;${item.star!""}</td>
							<td>&nbsp;
								<#if item.status?? && item.status=="y">
									<img alt="显示" src="${basepath}/resource/images/action_check.gif">
								<#else>
									<img alt="不显示" src="${basepath}/resource/images/action_delete.gif">
								</#if>
							</td>
							<td nowrap="nowrap">
								<#if item.reply??>
                                    <a href="toEdit?id=${item.id!""}&update=y">修改回复</a>
								<#else>
                                    <a href="toEdit?id=${item.id!""}">回复</a>
								</#if>
							</td>
						</tr>
					</#list>
					<tr>
						<td colspan="17" style="text-align: center;"><#include "/manage/system/pager.ftl"/></td>
					</tr>
				</table>
	</form>
</@page.pageBase>