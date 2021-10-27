<%@ page language = "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贵金属期货</title>
</head>
<body>
<h1>贵金属期货</h1>
<div>
    <div>
        <h2>贵金属列表</h2>
    </div>
    <div>
        <h2 id="hint"></h2>
    </div>
    <hr>
    <div>
        <div><p>黄金</p><p id="c0" style="color:#F00"></p><b><p id="s0">历史价格：</p></b></div>
        <div><p>白银</p><p id="c1" style="color:#F00"></p><b><p id="s1">历史价格：</p></b></div>
        <div><p>铂</p><p id="c2" style="color:#F00"></p><b><p id="s2">历史价格：</p></b></div>
        <div><p>铱</p><p id="c3" style="color:#F00"></p><b><p id="s3">历史价格：</p></b></div>
    </div>
    <hr>

</div>
<script type="text/javascript" src="assets/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

    function showPrice(index,data){
        $("#c"+index).html("当前价格："+data);
        var s = $("#s"+index).html();
        $("#s"+index).html(s+data+" ");
    }

    if(!!window.EventSource){//判断浏览器支持度
        //拿到sse的对象
        var source = new EventSource('needPrice');
        //接收到服务器的消息
        source.onmessage=function (e) {
            var dataObj=e.data;
            var arr = dataObj.split(',');
            $.each(arr, function (i, item) {
                showPrice(i,item);
                });
            $("#hint").html("");
        };

        source.onopen=function (e) {
            console.log("Connecting server!");
        };

        source.onerror=function () {
            console.log("error");
        };

    }else{
        $("#hint").html("您的浏览器不支持SSE！");
    }


</script>
</body>
</html>