<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/9/20
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/pages/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        //单击右箭头按钮，数据直接将第一个select标签中的已经选中的option标签，追加到第二个select标签中
        $("#toRightBtn").click(function () {
            $("select:eq(0) option:selected").appendTo($("select:eq(1)"));
        });
        //单击左箭头按钮，数据直接将第二个select标签中的已经选中的option标签，追加到第一个select标签中
        $("#toLeftBtn").click(function () {
            $("select:eq(1) option:selected").appendTo($("select:eq(0)"));
        });
        //点击保存，将角色的分配结果保存
        $("#saveAssignRoleBtn").click(function () {
            {
                $("form select:eq(1)>option").prop("selected","selected");
            }
        });
    });
</script>
<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <input type="hidden" name="id" value="${param.id}">
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10"
                                    style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRole}" var="unAssignedRole">

                                    <option value="${unAssignedRole.id}">${unAssignedRole.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="multiple" size="10"
                                    style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRole}" var="assignedRole">
                                    <option value="${assignedRole.id}">${assignedRole.name}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div>
                            <button style="display: block;margin: 0 auto" id="saveAssignRoleBtn" type="submit"
                                    class="btn btn-primary">保存
                            </button>
                        </div>
                    </form>
                    <br/>


                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
