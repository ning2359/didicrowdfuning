function generateTree() {
    $.ajax({
        "url":"menu/get/whole/tree.json",
        "type":"post",
        "dataType":"json",
        "success":function (response) {
            var  result = response.result;
            if (result=="SUCCESS"){
                var setting = {
                   "view":{
                       "addDiyDom": myAddDiyDom,
                       "addHoverDom": myAddHoverDom,
                       "removeHoverDom": myRemoveHoverDom
                   },
                    "data":{
                        "key":{
                            "url":"didi"
                        }
                    }
                };
                var zNodes = response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result=="FAILED"){
                layer.msg("Failed to generate data table"+"Failure information"+response.message);
            }
        },
        "error":function (response) {
            layer.msg(response.status+response.statusText);
        }
    });
}
function myAddDiyDom(treeId,treeNode) {
    console.log("treeId"+treeId)
    console.log(treeNode);
    var spanId = treeNode.tId+"_ico";
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}

function myRemoveHoverDom(treeId,treeNode) {
    // 拼接按钮组的id
    var btnGroupId = treeNode.tId + "_btnGrp";

    // 移除对应的元素
    $("#"+btnGroupId).remove();
}
function myAddHoverDom(treeId,treeNode) {
    var btnGroupId = treeNode.tId+"_btnGrp";
    console.log(btnGroupId);
    if ($("#"+btnGroupId).length > 0){
        return ;
    }
    var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='Add child nodes'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var deleteBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='Delete a node'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var updateBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='Modify node'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var level = treeNode.level;
    var btnXML = "";
    if (level==0){
        btnXML = addBtn;
    }
    if (level==1){
        btnXML = addBtn+"  "+updateBtn;
         var length = treeNode.children().length;
        if (length==0){
            btnXML = updateBtn+"  "+deleteBtn;
        }
    }
    if (level==2){
        btnXML = updateBtn+"  "+deleteBtn;
    }
    var anchorId = treeNode.tId+"_a";
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnXML+"</span>");
}