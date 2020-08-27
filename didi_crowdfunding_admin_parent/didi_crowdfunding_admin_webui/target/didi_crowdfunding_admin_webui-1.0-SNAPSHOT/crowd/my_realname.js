function generatePage() {
// 1.获取分页数据
    var pageInfo = getPageInfoRemote();
// 2.填充表格
    fillTableBody(pageInfo);
}
var loadingIndex = -1;
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url":"realname/show/list.json",
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
    $("#realnamePageBody").empty();
    $("#Pagination").empty();
    if (pageInfo == null ||pageInfo == undefined || pageInfo.records ==null || pageInfo.records.length == 0){
        $("#realnamePageBody").append("<tr><td colspan='5' align='center'>Sorry! The data you searched for has not been found!</td></tr>");
        return ;
    }
    var content = '';
    for (var i = 0;i<pageInfo.records.length;i++){
        var task = pageInfo.records[i];
        content += '<tr>';
        content += '  <td>'+(i+1)+'</td>';
        content += '  <td>'+task.procDefName+'</td>';
        content += '  <td>'+task.procDefVersion+'</td>';
        content += '  <td>'+task.name+'</td>';
        content += '  <td>'+task.memberName+'</td>';
        content += '  <td  align="center">';
        content += '      <button type="button" id="'+task.id+'"  name="'+task.memberid+'" prodef="'+task.procDefId+'" class="btn btn-success btn-xs auditBtn"><i class=" glyphicon glyphicon-check"></i></button>';
        content += '  </td>';
        content += '</tr>';
    }
    $("#realnamePageBody").html(content);
    generateNavigator(pageInfo);
}
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    console.log("页面总数"+totalRecord);
    console.log("每页要显示的数据的数量"+pageInfo.size);
    console.log("当前页"+pageInfo.current);
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



function generateAuditModal() {
// 1.获取数据
    var pageInfo = getAuditModalInfoRemote();
// 2.填充表格
    fillAuditModal(pageInfo);
}
function getAuditModalInfoRemote() {
    var ajaxResult = $.ajax({
        "url":"realname/get/all/info.json",
        beforeSend : function(){
            loadingIndex = layer.load(2, {time: 10*1000});
            return true ;
        },
        "type":"post",
        "data":{
            "memberId" : window.memberId,
            "taskId":window.taskId
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
function fillAuditModal(pageInfo) {
    console.log(pageInfo);
    console.log(pageInfo.length);
    $("#auditForm").empty();
    if (pageInfo == null ||pageInfo == undefined || pageInfo ==null || pageInfo.length == 0){
        $("#auditForm").append("<tr><td colspan='5' align='center'>Sorry! The data you searched for has not been found!</td></tr>");
        return ;
    }
    var content = '';
    var  audit = pageInfo;
        content += '<div class="form-group">';
        content += '  <label for="phone">账号名称：</label>';
        content += '  <span id="loginAcct">'+audit.loginAcct+'</span><br>';
        content += '  <label for="phone">用户昵称：</label>';
        content += '  <span id="userName">'+audit.userName+'</span><br>';
        content += '  <label for="phone">用户邮箱：</label>';
        content += '  <span id="email">'+audit.email+'</span><br>';
        content += '<label for="realname">真实名称：</label>';
        content += '  <span id="realname">'+audit.realname+'</span><br>';
        content += '  <label for="cardnum">身份证号：</label>';
        content += ' <span id="cardnum">'+audit.cardnum+'</span><br>';
        content += '  <label for="phone">电话号码：</label>';
        content += '  <span id="phone">'+audit.phone+'</span><br>';
        for (var i = 0;i<pageInfo.certImgList.length;i++){
            var certImg = pageInfo.certImgList[i];
            content += '  <label class="tu img" for="qualificationpcture">"'+certImg.certName+'"：</label>';
            content += '<img alt="'+certImg.certName+'" id="qualificationpcture" class="tu img"  src="'+certImg.qualificationpcture+'" /> ';
         }
        content += '</div>';
    $("#auditForm").html(content);
}