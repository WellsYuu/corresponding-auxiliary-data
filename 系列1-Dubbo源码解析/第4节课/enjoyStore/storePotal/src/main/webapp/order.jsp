<%@ page  isELIgnored="false"%>
<%@ page language="java"   contentType="text/html;charset=utf-8"%>
<%--
  ~ Copyright 2021-2022 the original author or authors
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<html>
<body>
<h2>Hello World!333</h2>
<h2>订单金额${order.money}元</h2>
<h2>订单状态：${order.status == 0?"未成交":"已成交"}</h2>
<h3>商品: ${order.productlist[0].name}----价格：${order.productlist[0].price} 元</h3>
<h3>商品: ${order.productlist[1].name}----价格：${order.productlist[1].price} 元</h3>
</body>
</html>
