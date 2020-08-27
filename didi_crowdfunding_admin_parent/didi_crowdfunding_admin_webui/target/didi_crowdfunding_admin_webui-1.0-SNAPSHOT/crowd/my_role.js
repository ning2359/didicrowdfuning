function showDeleteModel(roleArray) {
    $("#deleteModal").modal("show");
    $("#deleteName").empty();
    window.roleIdArray=[];
    for (var i = 0;i<roleArray.length;i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#deleteName").append(roleName+"<br/>");
        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}
function generatePage() {
    $("#summaryBox").prop("checked",false);

// 1.获取分页数据
    var pageInfo = getPageInfoRemote();
// 2.填充表格
    fillTableBody(pageInfo);
}
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url":"to/role/get/page.json",
        "type":"post",
        "data":{
            "current" : window.current,
            "pageSize" : window.pageSize,
            "keyWord" : window.keyWord
        },
        "dataType":"json",
        "async":false
    });
    console.log(ajaxResult);
    var statusCode = ajaxResult.status;
    if (statusCode != 200){
        layer.msg("查询失败"+"响应状态码"+statusCode+"说明信息"+ajaxResult.statusText);;
        return null;
    }
    var resultEntrty  = ajaxResult.responseJSON;
    var result = resultEntrty.result;
    if (result=="FAILED"){
        layer.msg(resultEntrty.message);
        return null;
    }
var pageInfo = resultEntrty.data;
    return pageInfo;
}
function fillTableBody(pageInfo) {
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    if (pageInfo == null ||pageInfo == undefined || pageInfo.records ==null || pageInfo.records.length == 0){
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>Sorry! The data you searched for has not been found!</td></tr>");
        return ;
    }
    for (var i = 0;i<pageInfo.records.length;i++){
        var role = pageInfo.records[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+roleId+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button'  class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);

    }
    generateNavigator(pageInfo);
}
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    var properties = {
        num_edge_entries: 3,								    // 边缘页数
        num_display_entries: 5,								// 主体页数
        callback: paginationCallBack,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
        items_per_page: pageInfo.size,	                    // 每页要显示的数据的数量
        current_page: pageInfo.current-1,	                // Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
        prev_text: "Previous",									// 上一页按钮上显示的文本
        next_text: "Next page"									// 下一页按钮上显示的文本
    };
    $("#Pagination").pagination(totalRecord, properties);
}
function paginationCallBack(pageIndex, jQuery) {
    window.current = pageIndex+1;
    generatePage();
    return false;
}
function fillAuthTree() {
    var ajaxResult  =$.ajax({
        "url":"assign/get/all/auth.json",
        "type":"post",
        "dataType":"json",
        "async":false
    });
    if (ajaxResult.status !=200){
        layer.msg(ajaxResult.status+" "+ajaxResult.statusText);
        return ;
    }
    var result = ajaxResult.responseJSON.data;
    var setting = {
        "data" : {
          "simpleData":{
              "enable":true,
              "pIdKey": "categoryId"
          },
            "key":{
              "name":"title"
            }
        },
        "check":{
            "enable":true
        }
    };
    $.fn.zTree.init($("#authTreeDemo"), setting, result);
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);
    ajaxResult = $.ajax({
        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json",
        "async":false
    });
    if (ajaxResult.status!=200){
        layer.msg(ajaxResult.status+"  "+ajaxResult.statusText);
        return;
    }
    var authIdArray = ajaxResult.responseJSON.data;
    for (var i = 0;i<authIdArray.length;i++){
        var authId = authIdArray[i];
        var treeNode = zTreeObj.getNodeByParam("id", authId);
        zTreeObj.checkNode(treeNode,true,false);
    }
}