<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/9/4
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="webjars/jquery/3.5.1/jquery.min.js"></script>

    <script src="bootstrap/js/bootstrap.js"></script>
    <script src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function(){

            $("button").click(function (){
                window.history.back();
            });

        });
    </script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

        <h2 style="text-align: center" class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 出错了</h2>
        <br/>
        <br/>
        <br/>
        <h3  style="text-align:center " >
            <%--    exception本来就存在于隐藏域中--%>
            错误的信息： <br/>
                <c:choose>
                    <c:when test="${not empty requestScope.exception.message}">
                        ${requestScope.exception.message}
                    </c:when>
                    <c:otherwise>
                        错误信息为空，空指针异常
                    </c:otherwise>
                </c:choose>
        </h3>
        <button  class="btn btn-lg btn-success btn-block" style="width: 500px;text-align: center;margin: auto">点我返回上一步</button>



</div>

</body>
</html>
