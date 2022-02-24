<%--
  Created by IntelliJ IDEA.
  User: 李晓扬
  Date: 2021/9/7
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@ include file="/WEB-INF/pages/include-head.jsp" %>

<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>

<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {

        window.keyword = "";
        window.pageNum = 1;
        window.pageSize = 5;
        generatePage();

        //按关键字进行分页
        $("#queryBtn").click(function () {
            //获取value属性值
            var keyword = $("#keywordContent").val();
            //判断，为null或者空串，提示消息，return
            if (keyword == null || keyword == "") {
                layer.msg("请输入查找的条件");

            }
            //不为空，覆盖window.keyword
            window.keyword = keyword;
            //执行分页（获取数据，删标签，填标签，导航条，翻页回调函数）
            generatePage();
        });

        //新增角色显示打开模态框
        $("#showModalBtn").click(function () {
            $("#addModal").modal("show");
        });
        //新增角色保存到后端
        $("#saveRoleBtn").click(function () {
            var roleInput = $("#roleInput").val().trim();
            if (roleInput == null || roleInput == "") {
                layer.msg("不能保存，用户名为空的角色");
                return;
            }
            $.ajax({
                "url": "role/add.json",
                "type": "post",
                "data": {
                    "name": roleInput
                },
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {

                        $("#addModal").modal("hide");
                        $("#roleInput").val("");
                        layer.msg("保存成功");
                        window.pageNum = 999999;
                        generatePage();
                    } else {
                        layer.msg("出现异常，错误原因：" + response.message);
                        return ;
                    }
                },
                "error": function (response) {
                    //此时的response，不是ResultEntity，而是请求失败后的，ajax请求对象
                    layer.msg("出错了：" + response.status + ",错误原因：" + response.statusText);
                    return;
                }
            });
        });
        //点击动态标签（编辑标签），出现模态框
        //因为id属性此时，带了动态数据，会变化，不能作为选择器
        $("#rolePageTbody").on("click", ".editBtn", function () {
            //打开模态框
            $("#editModal").modal("show");
            //从当前标签，roleName属性值，自己的属性，通过attr()获取
            var name = $(this).attr("roleName");

            //自己定义window.roleID，表示当前模态框中要修改的role的id
            window.roleId = $(this).attr("roleId");

            $("input#roleUpdate").val(name);
        });
        //点击模态框中的保存按钮，发送角色更新请求给后端
        $("button#updateRoleBtn").click(function () {
            var updateRoleName = $("input#roleUpdate").val();
            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id": window.roleId,
                    "name": updateRoleName
                },
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {

                        $("#editModal").modal("hide");
                        $("input#roleUpdate").val("");
                        layer.msg("保存成功");
                        generatePage();
                    } else {
                        layer.msg("出现异常，错误原因：" + response.message);
                        return null;
                    }
                },
                "error": function (response) {
                    //此时的response，不是ResultEntity，而是请求失败后的，ajax请求对象
                    layer.msg("出错了：" + response.status + ",错误原因：" + response.statusText);
                    return;
                }
            });
        });
        //单个删除,封装role对象数组（一个元素），显示模态框
        $("#rolePageTbody").on("click", "button.removeBtn", function () {
            var roleId = $(this).attr("roleId");
            var roleName = $(this).attr("roleName");
            var roleArray = [{"id": roleId, "name": roleName}];

            removeConfirm(roleArray);

        });

        //模态框出现后的，点击删除按钮
        $("button#removeRoleBtn").click(function () {
            //隐藏模态框
            $("div#removeModal").modal("hide");
            //确认删除，发送请求
            removeRolesByIdArray(window.roleIdArray);
        });
        //点击总选进行全选
        $("input#summaryBox").click(function () {
            //获取默认属性,html元素的.checked
            var checkedStatus = this.checked;
            //设置默认属性dom对象的"checked"
            $("input#itemBox").prop("checked", checkedStatus);
        });
        //全选全不选反向操作
        $("#rolePageTbody").on("click", "input#itemBox", function () {
            //使用过滤器
            var lengthChecked = $("input#itemBox:checked").length;
            var length = $("input#itemBox").length;
            $("input#summaryBox").prop("checked", length == lengthChecked);
        });

        //批量删除,显示模态框
        $("button#batchRemoveBtn").click(function () {
            //下面函数需要role数组
            var roleArray = [];
            //遍历checked的标签数组，都装进数组role对象
            $("input#itemBox:checked").each(function () {
                var role = {
                    "id": $(this).attr("roleId"),
                    "name": $(this).attr("roleName")
                };
                roleArray.push(role);
            });//判断，删除内容，不能为空
            if (roleArray.length == 0 || roleArray == null || roleArray == undefined) {
                layer.msg("删除内容不能为空")
                return;
            }
            //调用模态框，进行提示
            removeConfirm(roleArray);
        });
        //点击分配权限按钮，打开模态框，显示树形结构，显示角色分配权限情况。
        $("#rolePageTbody").on("click", ".checkBtn", function () {
            //存入当前role的id
            window.roleId= this.id;
            //打开模态框
            $("#roleAssignAuthModal").modal("show");
           //显示authList的树结构
            generateAuthTree();
        });
        //点击保存分配，修改分配情况
        $("#RoleAssignAuthBtn").click(function (){
            //事先声明存放authId的数组
            var authIdList =[];
            // 获取目前用户选中的节点(要通过zTreeObj对象)
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            // 树形结构中选中框，也是zTree提供的，是否选中也得看zTree
            var checkedNodes = zTreeObj.getCheckedNodes();
            // 获取所有选中的节点，遍历，
            for (var i = 0; i < checkedNodes.length; i++) {
                var checkNode = checkedNodes[i];
                var authId = checkNode.id;
                //每一个authId，都存入数组中
                authIdList.push(authId);
            }

            //准备发的数据（参数）
            var assignedResult={
                "authIdList":authIdList,
                "roleId":[window.roleId]
            }
            var requestBody = JSON.stringify(assignedResult);
            //给后端发数据，保存分配情况
            $.ajax({
                "url":"assign/do/role/assign/auth.json",
                "type":"post",
                "data":requestBody,
                "contentType":"application/json;charset=utf-8",
                "dataType":"json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {
                        layer.msg("分配成功");
                        generatePage();
                    } if(response.result =="FAILED") {
                        layer.msg("分配失败，错误原因：" + response.message);
                        return null;
                    }
                }, "error": function (response) {
                    //此时的response，不是ResultEntity，而是请求失败后的，ajax请求对象
                    layer.msg("出错了：" + response.status + ",错误原因：" + response.statusText);
                    return;
                }
            });$("#roleAssignAuthModal").modal("hide");
        });
    });
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

                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordContent" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>

                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">

                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>

                            <tbody id="rolePageTbody">


                            </tbody>

                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"/>
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
<%@ include file="/WEB-INF/pages/modal-role-add.jsp" %>
<%@ include file="/WEB-INF/pages/modal-role-edit.jsp" %>
<%@ include file="/WEB-INF/pages/modal-role-remove.jsp" %>
<%@ include file="/WEB-INF/pages/modal-role-assign-auth.jsp" %>

</body>
</html>
