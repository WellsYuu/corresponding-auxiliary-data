<%@page import="com.jiagouedu.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>

<script src="<%=request.getContextPath() %>/resource/js/showProduct/js/mzp-packed.js"></script>
<link href="<%=request.getContextPath() %>/resource/js/showProduct/css/lrtk.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/resource/js/showProduct/css/magiczoomplus.css" type="text/css" rel="stylesheet">

<!-- 产品明细页面中间位置的图片列表 -->
<div class="box">
	<div class="left-pro">
		<div class="t1">
			<img src="<%=request.getContextPath() %>/resource/js/showProduct/images/gotop.gif" id="gotop" />
			<div id="showArea">
				<s:iterator value="e.productImageList" status="i" var="img">
					<a href="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="image3" />" rel="zoom1" rev="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="image2" />">
							<img src="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="image1" />"
					</a>
				</s:iterator>
			</div>
			<img src="<%=request.getContextPath() %>/resource/js/showProduct/images/gobottom.gif" id="gobottom"  />
		</div>
		<div class="t2">
		<a href="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="e.picture" />" id="zoom1" class="MagicZoom MagicThumb">
			<img src="<%=SystemManager.getInstance().getSystemSetting().getImageRootPath()%><s:property escape="false" value="e.picture" />" 
			id="main_img" class="main_img" style="width:300px; height:300px;" />
		</a>
		</div>
	</div>
</div>
<div style="clear:both;"></div>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/showProduct/js/lrtk.js"></script>
