//生成页面（调用这个函数就，生成页面效果，重新加载页面）
function generatePage() {
    //1.获取分页数据
    var pageInfo = getPageInfoRemote();
    //2.填充表格内容
    fillTableBody(pageInfo);

}

//获取PageInfo(调用这个函数，从服务器端拿到ResultEntity json数据)
function getPageInfoRemote() {

    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "keyword": window.keyword,
            "pageNum": window.pageNum,
            "pageSize": window.pageSize
        },
        "dataType": "json",
        "async": false,

    });
    //判断当前状态码是否为200
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("服务器端调用程序失败，状态码为：" + statusCode + ",错误信息为：" + ajaxResult.statusText);
        return null;
    }
    //如果状态码200，表示调用成功，成功返回ResultEntity,获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    //判断ResultEntity返回是否成功
    var result = resultEntity.result;

    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    var pageInfo = resultEntity.data;
    return pageInfo;

}

//填充表格的主体（调用这个函数，将服务端拿到的数据，填充进表格主体中）
function fillTableBody(pageInfo) {
    //每次调用，都清空整个表格下的内容
    $("#rolePageTbody").empty();
    $("#Pagination").empty();

    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageTbody").append("<tr><td colspan='4'>抱歉，没有查询到您要的数据！</td></tr>");
        return null;
    }
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        //变量不加引号
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='itemBox'roleId='" + roleId + "' roleName='" + roleName + "'type='checkbox'></td>";
        var roleNameTd = "<td id='roleName'>" + roleName + "</td>";
        //当个标签整个双引号，属性名单引号，如果是变量引用在属性名中id='"+变量+"'
        //前面自己测试自定义属性，所以checkBtn，这次用默认属性
        var checkBtn = "<button id='" + roleId + "'roleName='" + roleName + "'type='button'class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button roleId='" + roleId + "' roleName='" + roleName + "' type='button' class='btn btn-primary btn-xs editBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button roleId='" + roleId + "' roleName='" + roleName + "'type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + ' ' + editBtn + ' ' + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageTbody").append(tr);
    }
    generateNavigator(pageInfo);
}

//生成页码导航条（调用这个函数，生成页码导航条）
function generateNavigator(pageInfo) {
    //获取总记录数
    var totalRecord = pageInfo.total;
    //声明一个json对象存储Pagination要设置的属性
    var properties = {

        num_edge_entries: 3,		                                //边缘页数（entry：条目）
        num_display_entries: 5,		                          //主体页数
        callback: paginationCallBack,		                  //指定用户点击“翻页”进行页面跳转的回调函数
        items_per_page: pageInfo.pageSize,                    //每页要显示的数据的数量
        current_page: pageInfo.pageNum - 1,                       //Pagination内部使用PageIndex来管理页码
        prev_text: "上一页",			                          //上一页按钮显示的文本
        next_text: "下一页"			                          //下一页按钮显示的文本

    };
    //生成页码导航条
    $("#Pagination").pagination(totalRecord, properties);
}

//pagnation导航条，初始化函数中，（翻页）点击页码跳转的回调函数
function paginationCallBack(pageIndex, jQuery) {
    //决定pageNum（跟着前端pagiNation索引值变化）
    window.pageNum = pageIndex + 1;
    // window.location.href="role/to/page.html?pageNum="+ pageNum+"&keyword="+window.keyword;
    generatePage();
    //由于每一个页码按钮，都是超链接，所以在这个函数最后取消超链接的默认行为
    return false;
}

//传进来roleId 数组，发送请求，后端删除这些id对应的role对象
function removeRolesByIdArray(roleIdArray) {
    //json字符串
    var stringify = JSON.stringify(roleIdArray);
    $.ajax({
        "url": "role/remove.json",
        "type": "post",
        "data": stringify,
        "dataType": "json",
        "contentType": "application/json;charset=utf-8",
        "success": function (response) {
            if (response.result == "SUCCESS") {
                layer.msg("删除成功");
                generatePage();
            } else {
                layer.msg("出现异常，错误原因：" + response.message);
                return null;
            }
        }, "error": function (response) {
            //此时的response，不是ResultEntity，而是请求失败后的，ajax请求对象
            layer.msg("出错了：" + response.status + ",错误原因：" + response.statusText);
            return;
        }
    });
}

//根据传进来的role数组，将name遍历放到模态框页面打开并显示，将id存到window.roleId数组中
function removeConfirm(roleArray) {
    $("div#showRemoveRoleName").empty();
    window.roleIdArray = [];
    $("div#removeModal").modal("show");
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleId = role.id;
        var roleName = role.name;
        $("div#showRemoveRoleName").append(roleName + "<br/>");
        window.roleIdArray.push(roleId);
    }
}

//点击分配权限，先显示所有的属性结构
function generateAuthTree() {
    $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "post",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                //形成树形结构需要的数据、配置
                var authList = response.data;
                var setting = {
                    "data": {
                        "key": {
                            "name": "title"
                        },
                        "simpleData": {
                            "enable": true,
                            "pIdKey": "categoryId"
                        }
                    },
                    "check": {
                        "enable": true,
                    }
                }
                //生成树形结构
                $.fn.zTree.init($("#authTreeDemo"), setting, authList)
                //设置节点（树形结构）展开
                var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                zTreeObj.expandAll(true);
                //只能获取一个zTreeObj
                showRoleAssignedAuthList(zTreeObj);

            }
            if (result == "FAILED") {
                layer.msg("获取该角色分配的权限失败");
                return;
            }
        },
        "error": function (response) {
            layer.msg("生成树错误：" + response.status + "错误消息：" + response.statusText)
        }
    });
}

//将该角色所属权限选中（先获取->选中->获取节点->设置节点选中）
function showRoleAssignedAuthList(zTreeObj) {
    var ajaxReturn = $.ajax({
        "url": "assign/get/assigned/auth/id/by/role/id.json",
        "type": "post",
        "data": {
            "roleId": window.roleId,
        },
        "dataType": "json",
        "async": false
    });
    //如果失败，错误显示
    var statusCode = ajaxReturn.status;
    var statusReason = ajaxReturn.statusText;
    if (statusCode != 200) {
        layer.msg("错误：" + statusCode + ",错误原因" + statusReason)
        return;
    }
    //如果成功，获取数据
    var authIdList = ajaxReturn.responseJSON.data;

    //根据roleId，查找对应的Node，然后使框选中
    //注意：zTreeObj只能获取一次，不然就失效了，前面，展开节点的时候，已经获取过了。
    //这里，使用获取的zTreeObj
    /* var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");*/
    for (var i = 0; i < authIdList.length; i++) {
        var authId = authIdList[i];
        //获取节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);

        //checked表示节点勾选
        var checked = true;
        //checkTypeFlag：设置为false，表示不联动
        var checkTypeFlag = false;

        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);

    }

}

