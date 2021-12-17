<!-- 产品列表左边产品图片滚动 -->
<style type="text/css">
		/* 本例子css */
		.picScroll-top{ overflow:hidden; position:relative;  border:1px solid #ccc;   }
/* 		.picScroll-top *{margin:0; padding:0; list-style:none;} */
		.picScroll-top .hd{ overflow:hidden;  height:30px; background:#f4f4f4; padding:0 10px;  }
		.picScroll-top .hd .prev,.picScroll-top .hd .next{ display:block;  width:9px; height:5px; float:right; margin-right:5px; margin-top:10px;  overflow:hidden;
			 cursor:pointer; background:url("${basepath}/resource/js/superSlide/demo/images/icoUp.gif") no-repeat;}
		.picScroll-top .hd .next{ background:url("${basepath}/resource/js/superSlide/demo/images/icoDown.gif") no-repeat;  }
		.picScroll-top .hd ul{ float:right; overflow:hidden; zoom:1; margin-top:10px; zoom:1; }
		.picScroll-top .hd ul li{ float:left;  width:9px; height:9px; overflow:hidden; margin-right:5px; text-indent:-999px; cursor:pointer; background:url("${basepath}/resource/js/superSlide/demo/images/icoCircle.gif") 0 -9px no-repeat; }
		.picScroll-top .hd ul li.on{ background-position:0 0; }
		.picScroll-top .bd{ padding:10px;   }
		.picScroll-top .bd ul{ overflow:hidden; zoom:1; }
		.picScroll-top .bd ul li{ text-align:center; zoom:1; }
		.picScroll-top .bd ul li .pic{ text-align:center;margin: auto; }
		.picScroll-top .bd ul li .pic img{ max-width:200px; max-height:200px; display:block;  padding:0px; border:0px solid #ccc; }
		.picScroll-top .bd ul li .pic a:hover img{ border-color:#999;  }
		.picScroll-top .bd ul li .title{ line-height:24px;text-align: left; }

		</style>

		<div class="picScroll-top">
			<div class="hd"><b>热门推荐</b>
				<a class="next"></a>
				<ul></ul>
				<a class="prev"></a>
				<span class="pageState"></span>
			</div>
			<div class="bd">
				<ul class="picList">
					<#assign _code>${catalogCode!""}</#assign>
						<#if systemManager().getProductsByCatalogCode(_code)??>
						<#list systemManager().getProductsByCatalogCode(_code) as item>
							<li class="row col-xs-12">
								<div class="pic"><a href="${basepath}/product/${item.id}" target="_blank">
									<img border="0" style="margin: auto;" src="${systemSetting().imageRootPath}/${item.picture!""}" /></a>
								</div>
								<div class="left_title" style="text-align: center;">
									<a href="${basepath}/product/${item.id}" target="_blank" style="margin: auto;text-align: center;" title="${item.name!""}">
									${item.name!""}
									</a>
								</div>
								<div class="left_title" style="text-align: center;">
									<b style="font-weight: bold;color: #cc0000;">
										￥${item.nowPrice!"0.00"}
									</b>
									<b style="text-decoration: line-through;font-weight: normal;font-size: 11px;color: #a5a5a5;margin-left: 20px;">
										￥${item.price!"0.00"}
									</b>
								</div>
							</li>
						</#list>
						</#if>
				</ul>
			</div>
		</div>

