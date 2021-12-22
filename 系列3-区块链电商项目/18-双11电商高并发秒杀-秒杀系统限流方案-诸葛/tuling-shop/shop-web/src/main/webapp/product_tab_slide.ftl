<!-- 首页通知切换卡 -->
<style type="text/css">
		/* css 重置 */
/* 		*{margin:0; padding:0; list-style:none; } */
/* 		body{ background:#fff; font:normal 12px/22px 宋体;  } */
/* 		img{ border:0;  } */
/* 		a{ text-decoration:none; color:#333;  } */
/* 		a:hover{ color:#1974A1;  } */


		/* 本例子css */
.slideTxtBox{ width:100%; border:1px solid #ddd; text-align:left;  }
.slideTxtBox *{margin:0; padding:0; list-style:none;}
.slideTxtBox .hd{ height:30px; line-height:30px; background:#f4f4f4; padding:0 20px; border-bottom:1px solid #ddd;  position:relative;  }
.slideTxtBox .hd ul{ float:left; position:absolute; left:20px; top:-1px; height:32px;   }
.slideTxtBox .hd ul li{ float:left; padding:0 15px; cursor:pointer;  }
.slideTxtBox .hd ul li.on{ height:30px;  background:#fff; border:1px solid #ddd; border-bottom:2px solid #fff; }
.slideTxtBox .bd ul{ padding:15px;  zoom:1;  }
/* 		.slideTxtBox .bd li{ height:24px; line-height:24px;    */
/* 			display: block; */
/* 			width: 200px; */
/* 			overflow: hidden; /*注意不要写在最后了*/ */
/* 			white-space: nowrap; */
/* 			-o-text-overflow: ellipsis; */
/* 			text-overflow: ellipsis; */
/* 		} */
.slideTxtBox .bd li .date{ float:right; color:#999;  }
#productdetailDiv img{max-width: 670px;}
</style>
		<div class="slideTxtBox">
			<div class="hd">
				<ul><li>商品介绍</li><li>商品评论</li></ul>
			</div>
			<div class="bd">
				<ul>
					<!-- 商品参数 -->
					<div class="row">
						<div class="col-md-12">
							<#list e.parameterList as item>
								<div class="col-md-4" style="margin-bottom: 5px;padding-right: 2px;">
									<b>${item.name!""} : </b>${item.value!""}<br>
								</div>
							</#list>
						</div>
					</div>
					<br>
					
					<!-- 商品HTML信息 -->
					<div class="row">
						<div class="col-xs-12">
							<div style="border: 0px solid; text-align: left;" id="productdetailDiv">
								${e.productHTML!""}
							</div>
						</div>
					</div>
				</ul>
				<ul>
				<!-- 评论 -->
					<#if  commentTypeCode=="default">
						<!-- 系统评论 -->
						<div class="row">
							<div class="col-xs-12">
								<#list pager.list as item>
									<ul class="media-list">
									  <li class="media">
									    <span class="pull-left" href="#">
									      <img class="media-object err-product" style="width: 50px;height: 50px;border: 0px;" alt="" src="http://myshopxx.oss.aliyuncs.com/attached/image/20140304/1393900153455_3.jpg">
									      (金牌会员)
									    </span>
									    <div class="media-body">
									      <h4 class="media-heading">${item.nickname!""}</h4>
									      ${item.content!""}
									      
									      <#if item.reply??>
										      <div class="media">
										      	<span class="pull-left" href="#">
											      <img class="media-object err-product" style="width: 50px;height: 50px;border: 0px;" alt="" src="http://myshopxx.oss.aliyuncs.com/attached/image/20140304/1393900153455_3.jpg">
											      (店小二)
											    </span>
											    <div class="media-body">
											    	<h4 class="media-heading" style="color:color:#f50">[店小二]回复：</h4>
										      		${item.reply!""}
											    </div>
										      </div>
									      </#if>
									    </div>
									  </li>
									</ul>
								</#list>
							</div>
							<div class="row" style="text-align: right;">
								<div class="col-xs-12">
									<#if pager.list??>
										<#include "/pager.ftl"/>
									<#else>
										该商品暂无评论！
									</#if>
								</div>
							</div>
						</div>
					<#elseif commentTypeCode=="duoshuo">
						<div class="row">
							<div class="col-xs-12">
								<#include "/duoshuo.ftl"/>
							</div>
						</div>
					</#if>
				</ul>
			</div>
		</div>
