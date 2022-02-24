//在zTree，生成每一个节点的时候，给每一个节点添加样式
function myAddDiyDom(treeId,treeNode){
  /*  console.log("treeId兄弟"+treeId);
    console.log(treeNode);*/
    //生成每一个节点表示样式的span的id
    var spanId = treeNode.tId+"_ico";
    //找到这个id
    //删除class属性中的样式
    //添加自己的样式（自己的样式存储在icon中），都可以从treeNode中获取
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}
//在移入节点范围的时候，添加按钮组（标签）
function myAddHoverDom(reeId,treeNode){
    //表示按钮组的是<span表示按钮组><a表示跳转><i表示样式>
    //这个按钮组辅佐的静态元素是a标签，id是treeId_n_a(也就是treeNode.tId+"_a")
    var anchorId = treeNode.tId+"_a";
    //为了方便后面移除节点范围删除，这里加入id属性
    var btnGroupId = treeNode.tId+"_btnGrp";

    if($("#"+btnGroupId).length > 0){
        return;
    }
    var editBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;'  title='修改'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var removeBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;'  title='删除'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var addBtn="<a  id='"+treeNode.id+"'class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' href='#' title='增加'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";

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
//在鼠标移出节点范围的时候，删除按钮组（标签）
function myRemoveHoverDom(reeId,treeNode){
    var btnGroupId = treeNode.tId+"_btnGrp";
    $("span#"+btnGroupId).remove();
}

//将在menu-page.jsp,生成属性结构的函数，封装到函数中（后面增删改查，最后都需要重定向，使用这个来重新生成树形结构）
function generateTree(){
    $.ajax({
        "url":"menu/get/whole/tree.json",
        "type":"post",
        "dataType":"json",
        "success":function (response){
            var result = response.result;
            if(result =="SUCCESS"){
                // 1.从后端拿到的数据
                var zNodes = response.data;
                // 2.json对象存储zTree所需的设置
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
                // 3.zTree初始化（生成树形结构）
                $.fn.zTree.init($("#treeDemo"),setting,zNodes);

            }if(result == "FAILED"){
                layer.msg("出错了：错误信息："+response.message);
                return;
            }
        },
        "error":function (response){
            layer.msg("响应码："+response.status+"错误信息："+response.statusText);
        }
    });
}