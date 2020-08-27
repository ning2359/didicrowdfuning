function showDeleteModel(certArray) {
    $("#deleteModal").modal("show");
    $("#deleteName").empty();
    window.certIdArray=[];
    for (var i = 0;i<certArray.length;i++){
        var cert = certArray[i];
        var certName = cert.certName;
        $("#deleteName").append(certName+"<br/>");
        var certId = cert.certId;
        window.certIdArray.push(certId);
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
        "url":"cert/get/page.json",
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
    $("#certPageBody").empty();
    $("#Pagination").empty();
    if (pageInfo == null ||pageInfo == undefined || pageInfo.records ==null || pageInfo.records.length == 0){
        $("#certPageBody").append("<tr><td colspan='4' align='center'>Sorry! The data you searched for has not been found!</td></tr>");
        return ;
    }
    var content = "";
    for (var i = 0;i<pageInfo.records.length;i++){
        var cert = pageInfo.records[i];
        content = content +  '<tr>';
        content = content +  '  <td>'+(i+1)+'</td>';
        content = content +  "<td><input id='"+cert.id+"' class='itemBox' type='checkbox'></td>";
        content = content +  '  <td>'+cert.name+'</td>';
        content = content +  '  <td>';
        content = content +  '	<button type="button" id="'+cert.id+'" style="float:right;" class="btn btn-danger btn-xs removeBtn"><i class=" glyphicon glyphicon-remove "></i></button>';
        content = content +  '   <button type="button" id="'+cert.id+'" style="margin-left:10px;" class="btn btn-primary btn-xs pencilBtn"><i class=" glyphicon glyphicon-pencil "></i></button>';
        content = content +  '  </td>';
        content = content +  '</tr>';
    }
    $("#certPageBody").append(content);
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

