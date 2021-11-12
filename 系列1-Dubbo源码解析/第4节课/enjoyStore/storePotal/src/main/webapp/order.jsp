<%@ page  isELIgnored="false"%>
<%@ page language="java"   contentType="text/html;charset=utf-8"%>
<html>
<body>
<h2>Hello World!333</h2>
<h2>订单金额${order.money}元</h2>
<h2>订单状态：${order.status == 0?"未成交":"已成交"}</h2>
<h3>商品: ${order.productlist[0].name}----价格：${order.productlist[0].price} 元</h3>
<h3>商品: ${order.productlist[1].name}----价格：${order.productlist[1].price} 元</h3>
</body>
</html>
