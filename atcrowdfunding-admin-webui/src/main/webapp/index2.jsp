<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/8/28
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <title>Title</title>
    <script type="text/javascript" src="webjars/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function (){
            console.log("hello啊");
            alert("我能出来么？");
        });
    </script>
</head>
<body>
    能看到我么
</body>
</html>
