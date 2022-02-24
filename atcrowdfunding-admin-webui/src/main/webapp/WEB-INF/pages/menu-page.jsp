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

<%@ include file="/WEB-INF/pages/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<body>
<script type="text/javascript">
    $(function () {
        //生成菜单维护的树形结构
        generateTree();
        //点击节点的新增按钮
        $("#treeDemo").on("click",".addBtn",function (){
            $("#menuAddModal").modal("show");
            //当前节点要添加，保存他的节点
            window.pid=this.id;
            return false;
        });
        //新增，点击保存以后
        $("button#menuSaveBtn").click(function (){
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
            var pid = window.pid;

            $.ajax({
                "url":"menu/add.json",
                "type":"post",
                "data":{
                    "pid":pid,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                "dataType":"json",
                "success":function (response){
                    var result = response.result;
                    if(result =="SUCCESS"){
                        layer.msg("保存成功")
                    }if(result == "FAILED"){
                        layer.msg("保存失败，错误信息"+response.message)
                    }
                    //响应成功后，关闭模态框
                    $("#menuAddModal").modal("hide");
                    //操作完成后，重新获取树结构，重新生成树
                    generateTree();
                    //每次，要清空新增框（表单最方便的操作，直接重置）
                    $("#menuResetBtn").click();
                },
                "error":function (response){
                    layer.msg(response.status+""+response.statusText)

                }
            });

        });
        //点击节点的删除按钮
        $("#treeDemo").on("click","a.removeBtn",function (){
            //删除的模态框显示
            $("#menuConfirmModal").modal("show");
            window.id = this.id;
            //获取节点
            var key ="id";
            var value = window.id;

            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var node = zTreeObj.getNodeByParam(key,value);
            var nodeName = node.name;

            // $("#removeNodeSpan").text(nodeName).removeClass().addClass(node.icon);
            $("#removeNodeSpan").html("<i class='"+node.icon+"'>"+node.name)
           return false;
        });
        //点击模态框的删除按钮
        $("button#confirmBtn").click(function (){
            $.ajax({
                "url":"menu/remove.json",
                "type":"post",
                "data":{
                    "id":id,
                },
                "dataType":"json",
                "async":false,
                "success":function (response){
                    var result = response.result;
                    if(result =="SUCCESS"){
                        layer.msg("删除成功")
                    }if(result == "FAILED"){
                        layer.msg("删除失败，错误信息"+response.message)
                    }
                },
                "error":function (response){
                    layer.msg(response.status+""+response.statusText)
                    //失败了，return一下
                    return ;
                },
            });
            //关闭模态框
            $("#menuConfirmModal").modal("hide");
            //操作完成后，重新获取树结构，重新生成树
            generateTree();

        });
        //点击节点的修改按钮
        $("#treeDemo").on("click","a.editBtn",function (){
            //显示模态框
            $("#menuEditModal").modal("show");
            //修改节点的id标签，保存到全局变量中去
            window.id = this.id;
            //获取zTreeObj,获取到Node，就获得Node的所有属性，显示到模态框中
            var key = "id";
            var value = window.id;
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var node = zTreeObj.getNodeByParam(key,value);
            //获取node的属性
            var name =node.name;
            var url = node.url;
            var icon = node.icon;
            //将这些属性回显到页面中去
            $("#menuEditModal [name=name]").val(name);
            $("#menuEditModal [name=url]").val(url);
            $("#menuEditModal [name=icon]").val([icon]);
            return false;
        });

        //点击修改模态框的更新的按钮
        $("#menuEditBtn").click(function (){
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();
            var id = window.id;

            $.ajax({
                "url":"menu/edit.json",
                "type":"post",
                "data":{
                    "id":id,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                "dataType":"json",
                "success":function (response){
                    var result = response.result;
                    if(result =="SUCCESS"){
                        layer.msg("更新成功")
                    }if(result == "FAILED"){
                        layer.msg("更新失败，错误信息"+response.message)
                    }
                    //响应成功后，关闭模态框
                    $("#menuEditModal").modal("hide");
                    //操作完成后，重新获取树结构，重新生成树
                    generateTree();

                },
                "error":function (response){
                    layer.msg(response.status+""+response.statusText)

                }
            });
        });
    });
</script>
<%@ include file="/WEB-INF/pages/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <%--这个ul标签，是树形结构动态标签要生成所附着的静态标签--%>
                <ul id="treeDemo" class="ztree">

                </ul>
            </div>
        </div>
    <%@ include file="/WEB-INF/pages/modal-menu-add.jsp"%>
    <%@ include file="/WEB-INF/pages/modal-menu-confirm.jsp"%>
    <%@ include file="/WEB-INF/pages/modal-menu-edit.jsp"%>
</body>
</html>
