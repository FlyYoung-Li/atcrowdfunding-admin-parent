//��zTree������ÿһ���ڵ��ʱ�򣬸�ÿһ���ڵ������ʽ
function myAddDiyDom(treeId,treeNode){
  /*  console.log("treeId�ֵ�"+treeId);
    console.log(treeNode);*/
    //����ÿһ���ڵ��ʾ��ʽ��span��id
    var spanId = treeNode.tId+"_ico";
    //�ҵ����id
    //ɾ��class�����е���ʽ
    //����Լ�����ʽ���Լ�����ʽ�洢��icon�У��������Դ�treeNode�л�ȡ
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}
//������ڵ㷶Χ��ʱ����Ӱ�ť�飨��ǩ��
function myAddHoverDom(reeId,treeNode){
    //��ʾ��ť�����<span��ʾ��ť��><a��ʾ��ת><i��ʾ��ʽ>
    //�����ť�鸨���ľ�̬Ԫ����a��ǩ��id��treeId_n_a(Ҳ����treeNode.tId+"_a")
    var anchorId = treeNode.tId+"_a";
    //Ϊ�˷�������Ƴ��ڵ㷶Χɾ�����������id����
    var btnGroupId = treeNode.tId+"_btnGrp";

    if($("#"+btnGroupId).length > 0){
        return;
    }
    var editBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;'  title='�޸�'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var removeBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;'  title='ɾ��'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var addBtn="<a  id='"+treeNode.id+"'class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' href='#' title='����'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";

    var btns="";
    if(treeNode.level ==0){
        btns = addBtn;
    }if(treeNode.level ==1){
        if(treeNode.children.length ==0){
            btns =editBtn+" "+removeBtn+" "+addBtn;
        }else{
            btns =editBtn+" "+addBtn;
        }
    }if(treeNode.level ==2){
        btns =editBtn+" "+removeBtn;
    }
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btns+"</span>");
}
//������Ƴ��ڵ㷶Χ��ʱ��ɾ����ť�飨��ǩ��
function myRemoveHoverDom(reeId,treeNode){
    var btnGroupId = treeNode.tId+"_btnGrp";
    $("span#"+btnGroupId).remove();
}

//����menu-page.jsp,�������Խṹ�ĺ�������װ�������У�������ɾ�Ĳ飬�����Ҫ�ض���ʹ������������������νṹ��
function generateTree(){
    $.ajax({
        "url":"menu/get/whole/tree.json",
        "type":"post",
        "dataType":"json",
        "success":function (response){
            var result = response.result;
            if(result =="SUCCESS"){
                // 1.�Ӻ���õ�������
                var zNodes = response.data;
                // 2.json����洢zTree���������
                var setting ={
                    "view":{
                        "addDiyDom":myAddDiyDom,
                        "addHoverDom":myAddHoverDom,
                        "removeHoverDom":myRemoveHoverDom
                    },
                    "data":{
                        "key":{
                            url:"dontSkip"
                        }
                    }
                };
                // 3.zTree��ʼ�����������νṹ��
                $.fn.zTree.init($("#treeDemo"),setting,zNodes);

            }if(result == "FAILED"){
                layer.msg("�����ˣ�������Ϣ��"+response.message);
                return;
            }
        },
        "error":function (response){
            layer.msg("��Ӧ�룺"+response.status+"������Ϣ��"+response.statusText);
        }
    });
}