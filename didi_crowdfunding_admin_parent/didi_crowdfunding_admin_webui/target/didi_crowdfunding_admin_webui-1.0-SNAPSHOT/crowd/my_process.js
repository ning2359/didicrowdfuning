function showDeleteModel(processArray) {
    $("#deleteModal").modal("show");
    $("#deleteName").empty();
    window.processIdArray=[];
    for (var i = 0;i<processArray.length;i++){
        var process = processArray[i];
        var processName = process.processName;
        console.log("processName222"+processName);
        $("#deleteName").append(processName+"<br/>");
        var processId = process.processId;
        window.processIdArray.push(processId);
        console.log("processKey3333"+processId);
    }
}

function generatePage() {
    $("#summaryBox").prop("checked",false);
// 1.获取分页数据
    var pageInfo = getPageInfoRemote();
// 2.填充表格
    fillTableBody(pageInfo);
}
var loadingIndex = -1;
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url":"process/deploy.json",
        beforeSend : function(){
            loadingIndex = layer.load(2, {time: 10*1000});
            return true ;
        },
        "type":"post",
        "data":{
            "current" : window.current,
            "pageSize" : window.pageSize,
            "keyWord" : window.keyWord
        },
        "dataType":"json",
        "async":false
    });
    layer.close(loadingIndex);
    console.log(ajaxResult);
    var statusCode = ajaxResult.status;
    if (statusCode != 200){
        layer.msg(ajaxResult.statusText.message, {time:1000, icon:5, shift:6});
        return null;
    }
    var resultEntrty  = ajaxResult.responseJSON;
    var result = resultEntrty.result;
    if (result=="FAILED"){
        layer.msg(resultEntrty.message,{time:1000, icon:5, shift:6});
        return null;
    }
    var pageInfo = resultEntrty.data;
    return pageInfo;
}
function fillTableBody(pageInfo) {
    $("#processPageBody").empty();
    $("#Pagination").empty();
    if (pageInfo == null ||pageInfo == undefined || pageInfo.records ==null || pageInfo.records.length == 0){
        $("#processPageBody").append("<tr><td colspan='5' align='center'>Sorry! The data you searched for has not been found!</td></tr>");
        return ;
    }
    var content = '';
    for (var i = 0;i<pageInfo.records.length;i++){
        var n = pageInfo.records[i];
        content+='<tr>';
        content+='  <td>'+(i+1)+'</td>';
        content+="<td><input id='"+n.id+"' class='itemBox' type='checkbox'></td>";
        content+='  <td>'+n.name+'</td>';
        content+='  <td>'+n.version+'</td>';
        content+='  <td>'+n.key+'</td>';
        content+='  <td>';
        content+='	  <button type="button" id="'+n.id+'"  class="btn btn-success btn-xs queryViewBtn "><i class=" glyphicon glyphicon-eye-open"></i></button>';
        content+='	  <button type="button" id="'+n.id+'" class="btn btn-danger btn-xs removeBtn"><i class=" glyphicon glyphicon-remove"></i></button>';
        content+='  </td>';
        content+='</tr>';
    }
    $("#processPageBody").html(content);
    generateNavigator(pageInfo);
}
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    console.log("页面总数"+totalRecord);
    console.log("每页要显示的数据的数量"+pageInfo.size);
    console.log("当前页"+pageInfo.current-1);
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
