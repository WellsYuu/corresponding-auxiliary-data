<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试Session共享内容</title>
</head>
<body>
    <%
         Object sessionMessage = session.getAttribute("sessionMessage");
        if (sessionMessage!=null && sessionMessage.toString().trim().length()>0) {
            out.println("session有值 session="+sessionMessage);
        }else{
            session.setAttribute("sessionMessage","Hello imooc jiangzh");
            out.println("session没有值");
        }
    %>
</body>
</html>
