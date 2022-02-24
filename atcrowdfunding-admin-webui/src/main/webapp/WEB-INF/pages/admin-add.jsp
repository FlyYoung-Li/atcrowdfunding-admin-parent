<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/9/9
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/pages/include-head.jsp" %>

<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                <ol class="breadcrumb">
                    <li><a href="#">首页</a></li>
                    <li><a href="#">数据列表</a></li>
                    <li class="active">新增</li>
                </ol>

                <div class="panel panel-default">
                    <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                    <p class="help-block label label-warning">${requestScope.exception.message}</p
                    <div class="panel-body">
                        <form action="admin/save.html" method="post" role="form">
                            <div class="form-group">
                                <label for="exampleInputPassword1">登陆账号</label>
                                <input name="loginAcct" type="text" class="form-control" id="exampleInputPassword1" placeholder="请输入登陆账号">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">用户名称</label>
                                <input name="userName" type="text" class="form-control" id="exampleInputPassword2" placeholder="请输入用户名称">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">用户密码</label>
                                <input name="userPswd" type="text" class="form-control" id="exampleInputPassword3" placeholder="请输入用户密码">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">邮箱地址</label>
                                <input  name="email" type="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                                <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                            </div>
                            <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                            <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                        </form>
                    </div>
                </div>

        </div>
    </div>
</div>

</body>
</html>
