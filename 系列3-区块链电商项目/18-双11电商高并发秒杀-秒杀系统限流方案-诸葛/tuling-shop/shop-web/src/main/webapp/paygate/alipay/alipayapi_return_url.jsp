<%@page import="com.jiagouedu.core.FrontContainer"%>
<%@page import="com.jiagouedu.services.front.order.bean.Order"%>
<%@page import="com.jiagouedu.services.front.order.OrderService"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.slf4j.*"%>
<%@page
	import="com.jiagouedu.core.pay.alipay.alipayescow.util.AlipayNotify"%>
<%
	/* *
	功能：支付宝页面跳转同步通知页面
	版本：3.2
	日期：2011-03-17
	说明：
	以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	//***********页面功能说明***********
	该页面可在本机电脑测试
	可放入HTML等美化页面的代码、商户业务逻辑程序代码
	TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	//********************************
	 * */
%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付宝页面跳转同步通知页面</title>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/resource/common_css.jsp"%>
</head>
<body>
	<%
		/**
		 * 需要再支付宝回调方法中 如果支付成功的话，则清空购物车。
		 */
		request.getSession().setAttribute(FrontContainer.myCart, null);//清空购物车

		Logger logger = LoggerFactory.getLogger(AlipayNotify.class);
		//获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		logger.error("同步通知request.getParameterMap()=" + requestParams);
		for (Iterator iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		logger.error("同步通知params=" + params);

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter(
				"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter(
				"trade_status").getBytes("ISO-8859-1"), "UTF-8");
		//退款状态
		//String refund_status = new String(request.getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		logger.error("同步通知verify_result=" + verify_result);
		String result = null;
		if (verify_result) {//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			out.println("trade_status=" + trade_status + "<br />");
			if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				out.println("买家已付款，等待卖家发货" + "<br />");

				//本系统的业务逻辑处理，把订单更新为已成功付款状态
				WebApplicationContext app = WebApplicationContextUtils
						.getWebApplicationContext(request.getSession()
								.getServletContext());
				OrderService orderService = (OrderService) app
						.getBean("orderServiceFront");
				if (orderService.alipayNotify(trade_status, null,
						out_trade_no, trade_no)) {
					out.println("修改订单状态为【已付款】成功" + "<br />");
					result = "支付成功！";
				} else {
					result = "订单支付异常，请联系客服！";
					out.println("修改订单状态为【已付款】失败！，请检查" + "<br />");
				}
			}

			//该页面可做页面美工编辑
			out.println("验证成功<br />");

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			//////////////////////////////////////////////////////////////////////////////////////////
		} else {
			//该页面可做页面美工编辑
			out.println("验证失败");
			result = "订单支付异常，请联系客服！";
		}
		//session.setAttribute("pay_result", result);
		//转到首页
		response.sendRedirect(SystemManager.getInstance().getSystemSetting().getWww()+"/order/paySuccess.html");
		if(true){
			return;
		}
	%>

	<div class="container">
		<div class="row">
			<%=result%>
		</div>
	</div>
</body>
</html>