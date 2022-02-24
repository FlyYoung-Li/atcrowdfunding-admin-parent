//����ҳ�棨������������ͣ�����ҳ��Ч�������¼���ҳ�棩
function generatePage() {
    //1.��ȡ��ҳ����
    var pageInfo = getPageInfoRemote();
    //2.���������
    fillTableBody(pageInfo);

}

//��ȡPageInfo(��������������ӷ��������õ�ResultEntity json����)
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
    //�жϵ�ǰ״̬���Ƿ�Ϊ200
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("�������˵��ó���ʧ�ܣ�״̬��Ϊ��" + statusCode + ",������ϢΪ��" + ajaxResult.statusText);
        return null;
    }
    //���״̬��200����ʾ���óɹ����ɹ�����ResultEntity,��ȡpageInfo
    var resultEntity = ajaxResult.responseJSON;

    //�ж�ResultEntity�����Ƿ�ɹ�
    var result = resultEntity.result;

    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    var pageInfo = resultEntity.data;
    return pageInfo;

}

//���������壨���������������������õ������ݣ�������������У�
function fillTableBody(pageInfo) {
    //ÿ�ε��ã��������������µ�����
    $("#rolePageTbody").empty();
    $("#Pagination").empty();

    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageTbody").append("<tr><td colspan='4'>��Ǹ��û�в�ѯ����Ҫ�����ݣ�</td></tr>");
        return null;
    }
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        //������������
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='itemBox'roleId='" + roleId + "' roleName='" + roleName + "'type='checkbox'></td>";
        var roleNameTd = "<td id='roleName'>" + roleName + "</td>";
        //������ǩ����˫���ţ������������ţ�����Ǳ�����������������id='"+����+"'
        //ǰ���Լ������Զ������ԣ�����checkBtn�������Ĭ������
        var checkBtn = "<button id='" + roleId + "'roleName='" + roleName + "'type='button'class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button roleId='" + roleId + "' roleName='" + roleName + "' type='button' class='btn btn-primary btn-xs editBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button roleId='" + roleId + "' roleName='" + roleName + "'type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + ' ' + editBtn + ' ' + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageTbody").append(tr);
    }
    generateNavigator(pageInfo);
}

//����ҳ�뵼�����������������������ҳ�뵼������
function generateNavigator(pageInfo) {
    //��ȡ�ܼ�¼��
    var totalRecord = pageInfo.total;
    //����һ��json����洢PaginationҪ���õ�����
    var properties = {

        num_edge_entries: 3,		                                //��Եҳ����entry����Ŀ��
        num_display_entries: 5,		                          //����ҳ��
        callback: paginationCallBack,		                  //ָ���û��������ҳ������ҳ����ת�Ļص�����
        items_per_page: pageInfo.pageSize,                    //ÿҳҪ��ʾ�����ݵ�����
        current_page: pageInfo.pageNum - 1,                       //Pagination�ڲ�ʹ��PageIndex������ҳ��
        prev_text: "��һҳ",			                          //��һҳ��ť��ʾ���ı�
        next_text: "��һҳ"			                          //��һҳ��ť��ʾ���ı�

    };
    //����ҳ�뵼����
    $("#Pagination").pagination(totalRecord, properties);
}

//pagnation����������ʼ�������У�����ҳ�����ҳ����ת�Ļص�����
function paginationCallBack(pageIndex, jQuery) {
    //����pageNum������ǰ��pagiNation����ֵ�仯��
    window.pageNum = pageIndex + 1;
    // window.location.href="role/to/page.html?pageNum="+ pageNum+"&keyword="+window.keyword;
    generatePage();
    //����ÿһ��ҳ�밴ť�����ǳ����ӣ�����������������ȡ�������ӵ�Ĭ����Ϊ
    return false;
}

//������roleId ���飬�������󣬺��ɾ����Щid��Ӧ��role����
function removeRolesByIdArray(roleIdArray) {
    //json�ַ���
    var stringify = JSON.stringify(roleIdArray);
    $.ajax({
        "url": "role/remove.json",
        "type": "post",
        "data": stringify,
        "dataType": "json",
        "contentType": "application/json;charset=utf-8",
        "success": function (response) {
            if (response.result == "SUCCESS") {
                layer.msg("ɾ���ɹ�");
                generatePage();
            } else {
                layer.msg("�����쳣������ԭ��" + response.message);
                return null;
            }
        }, "error": function (response) {
            //��ʱ��response������ResultEntity����������ʧ�ܺ�ģ�ajax�������
            layer.msg("�����ˣ�" + response.status + ",����ԭ��" + response.statusText);
            return;
        }
    });
}

//���ݴ�������role���飬��name�����ŵ�ģ̬��ҳ��򿪲���ʾ����id�浽window.roleId������
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

//�������Ȩ�ޣ�����ʾ���е����Խṹ
function generateAuthTree() {
    $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "post",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                //�γ����νṹ��Ҫ�����ݡ�����
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
                //�������νṹ
                $.fn.zTree.init($("#authTreeDemo"), setting, authList)
                //���ýڵ㣨���νṹ��չ��
                var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                zTreeObj.expandAll(true);
                //ֻ�ܻ�ȡһ��zTreeObj
                showRoleAssignedAuthList(zTreeObj);

            }
            if (result == "FAILED") {
                layer.msg("��ȡ�ý�ɫ�����Ȩ��ʧ��");
                return;
            }
        },
        "error": function (response) {
            layer.msg("����������" + response.status + "������Ϣ��" + response.statusText)
        }
    });
}

//���ý�ɫ����Ȩ��ѡ�У��Ȼ�ȡ->ѡ��->��ȡ�ڵ�->���ýڵ�ѡ�У�
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
    //���ʧ�ܣ�������ʾ
    var statusCode = ajaxReturn.status;
    var statusReason = ajaxReturn.statusText;
    if (statusCode != 200) {
        layer.msg("����" + statusCode + ",����ԭ��" + statusReason)
        return;
    }
    //����ɹ�����ȡ����
    var authIdList = ajaxReturn.responseJSON.data;

    //����roleId�����Ҷ�Ӧ��Node��Ȼ��ʹ��ѡ��
    //ע�⣺zTreeObjֻ�ܻ�ȡһ�Σ���Ȼ��ʧЧ�ˣ�ǰ�棬չ���ڵ��ʱ���Ѿ���ȡ���ˡ�
    //���ʹ�û�ȡ��zTreeObj
    /* var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");*/
    for (var i = 0; i < authIdList.length; i++) {
        var authId = authIdList[i];
        //��ȡ�ڵ�
        var treeNode = zTreeObj.getNodeByParam("id", authId);

        //checked��ʾ�ڵ㹴ѡ
        var checked = true;
        //checkTypeFlag������Ϊfalse����ʾ������
        var checkTypeFlag = false;

        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);

    }

}

