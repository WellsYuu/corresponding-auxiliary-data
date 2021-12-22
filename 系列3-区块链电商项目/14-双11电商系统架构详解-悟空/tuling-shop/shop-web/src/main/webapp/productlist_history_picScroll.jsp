<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@page import="com.jiagouedu.services.front.product.bean.Product"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="com.jiagouedu.services.front.news.bean.News"%>
<%@page import="com.jiagouedu.core.FrontContainer"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%-- <script src="<%=request.getContextPath() %>/resource/js/superSlide/jquery.pack.js"></script> --%>
<script src="<%=request.getContextPath() %>/resource/js/superSlide/jquery.SuperSlide.js"></script>
</head>

<body>
<!-- 用户浏览的商品记录 -->
<style type="text/css">
		/* css 重置 */
/* 		*{margin:0; padding:0; list-style:none; } */
/* 		body{ background:#fff; font:normal 12px/22px 宋体;  } */
/* 		img{ border:0;  } */
/* 		a{ text-decoration:none; color:#333;  } */

		/* 本例子css */
		.picScroll-top{ margin:0 auto;  width:100%;  overflow:hidden; position:relative;  border:1px solid #ccc;   }
		.picScroll-top *{margin:0; padding:0; list-style:none;}
		.picScroll-top .hd{ overflow:hidden;  height:30px; background:#f4f4f4; padding:0 10px;  }
		.picScroll-top .hd .prev,.picScroll-top .hd .next{ display:block;  width:9px; height:5px; float:right; margin-right:5px; margin-top:10px;  overflow:hidden;
			 cursor:pointer; background:url("<%=request.getContextPath() %>/resource/js/superSlide/demo/images/icoUp.gif") no-repeat;}
		.picScroll-top .hd .next{ background:url("<%=request.getContextPath() %>/resource/js/superSlide/demo/images/icoDown.gif") no-repeat;  }
		.picScroll-top .hd ul{ float:right; overflow:hidden; zoom:1; margin-top:10px; zoom:1; }
		.picScroll-top .hd ul li{ float:left;  width:9px; height:9px; overflow:hidden; margin-right:5px; text-indent:-999px; cursor:pointer; background:url("<%=request.getContextPath() %>/resource/js/superSlide/demo/images/icoCircle.gif") 0 -9px no-repeat; }
		.picScroll-top .hd ul li.on{ background-position:0 0; }
		.picScroll-top .bd{ padding:10px;   }
		.picScroll-top .bd ul{ overflow:hidden; zoom:1; }
		.picScroll-top .bd ul li{ text-align:center; zoom:1; }
		.picScroll-top .bd ul li .pic{ text-align:center; }
		.picScroll-top .bd ul li .pic img{ width:100%; height:90px; display:block;  padding:0px; border:0px solid #ccc; }
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
					<%
						application.setAttribute("suijiProducts", SystemManager.suijiProducts);
// 					out.println(SystemManager.suijiProducts.size());
					%>
					<s:iterator value="#application.suijiProducts" status="i" var="row">
						<li>
							<div class="pic"><a href="http://www.SuperSlide2.com" target="_blank">
								<img border="0" src="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="picture" />" /></a>
							</div>
							<div class="title">
								<a href="http://www.SuperSlide2.com" target="_blank">
									<s:property escape="false" value="name"/>
								</a>
							</div>
							<div class="title">
								<b style="font-weight: bold;color: #cc0000;float: left;">
									￥<s:property escape="false" value="nowPrice" />
								</b>
								<b style="text-decoration: line-through;font-weight: normal;font-size: 11px;color: #a5a5a5;">
									￥<s:property escape="false" value="price" />
								</b>
							</div>
						</li>
					</s:iterator>
				</ul>
			</div>
		</div>

		<script type="text/javascript">
		 jQuery(".picScroll-top").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"top",autoPlay:true,
			 scroll:5,vis:5});
		</script>

</body>
</html>
