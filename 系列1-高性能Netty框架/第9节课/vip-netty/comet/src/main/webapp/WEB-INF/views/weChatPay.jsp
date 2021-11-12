<%@ page language = "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
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