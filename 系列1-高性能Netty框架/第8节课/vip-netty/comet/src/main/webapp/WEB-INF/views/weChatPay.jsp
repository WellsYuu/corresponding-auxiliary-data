<%@ page language = "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<%--
  ~ Copyright [$tody.year] [Wales Yu of copyright owner]
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线支付</title>
</head>
<body>
<h1>在线支付</h1>
<div>
    <div>
        <h2>请扫码支付</h2>
        <div><img src="assets/img/ewm.jpg" alt=""></div>
        <button onclick="send()">支   付</button>
        <div style="color:#F00"><b><p id="payHint">  </p></b></div>
    </div>
</div>
<script type="text/javascript" src="assets/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

    //send();

    function send(){
        $("#payHint").html("正在处理中........");
        $.ajax({
            type: 'get',
            url:'payMoney?weCharId=1234567',
            dataType:'text',
            success:function(e){
                console.log(e);
                var arr = e.split('data:');
                var hint = '';
                $.each(arr, function (i, item) {
                    hint = hint+item+'<br>';
                });
                $("#payHint").html(hint);
            },
            error:function(data){
                $("#payHint").html(data);
            }

        });
    }


</script>
</body>
</html>