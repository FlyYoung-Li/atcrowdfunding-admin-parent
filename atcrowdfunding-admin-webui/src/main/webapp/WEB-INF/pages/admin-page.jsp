<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/9/7
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/pages/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">

    $(function () {
        //调用初始化函数，对页码导航条进行初始化操作
        initPagination();

        $("a#delete").click(function (){
            confirm("你确定要删除么？");
        });
    });

    function initPagination() {
        //获取总记录数
        var totalRecord = "${requestScope.pageInfo.total}";
        //声明一个json对象存储Pagination要设置的属性
        var properties = {

            num_edge_entries:3,		                                //边缘页数（entry：条目）
            num_display_entries:5,		                          //主体页数
            callback: pageSelectCallback,		                  //指定用户点击“翻页”进行页面跳转的回调函数
            items_per_page: "${requestScope.pageInfo.pageSize}", //每页要显示的数据的数量
            current_page: "${requestScope.pageInfo.pageNum-1}",   //Pagination内部使用PageIndex来管理页码
            prev_text: "上一页",			                          //上一页按钮显示的文本
            next_text: "下一页"			                          //下一页按钮显示的文本

        };
        //生成页码导航条
        $("#Pagination").pagination(totalRecord, properties);
    }

    //回调函数的含义：声明出来以后不是自己调用，而是交给系统或框架调用
    //用户点击“上一页、下一页、1、2、3....这样的页码时调用这个回调函数，跳转具体页面”
    //pageIndex是Pagination传给我们的那个“从0开始”的页码
    function pageSelectCallback(pageIndex,jQuery) {
        var pageNum = pageIndex + 1;
        window.location.href="admin/get.html?pageNum="+ pageNum+"&keyword=${param.keyword}";
        //由于每一个页码按钮，都是超链接，所以在这个函数最后取消超链接的默认行为
        return false;
    }


</script>

<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>

                <div class="panel-body">

                    <form action="admin/get.html"method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" name="keyword" value="${param.keyword}"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>

                    <button type="submit" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='admin/to/add/page.html'">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty pageInfo.list}">
                                <td colspan="6" align="center">很抱歉，要查询的数据为空</td>
                            </c:if>

                            <c:if test="${!empty pageInfo.list}">
                                <c:forEach items="${pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a id="assign" href="assign/to/assign/page.html?keyword=${param.keyword}&pageNum=${pageInfo.pageNum}&id=${admin.id} " class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i></a>
                                            <a id="update" href="admin/to/edit/page.html?keyword=${param.keyword}&pageNum=${pageInfo.pageNum}&id=${admin.id}" class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></a>
                                            <a id="delete" href="admin/remove/${admin.id}/${pageInfo.pageNum}/${param.keyword}.html" class="btn btn-danger btn-xs"><i
                                                    class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            </tbody>

                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"/>


                                    <%--这里显示分页--%>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
