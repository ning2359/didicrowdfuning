<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="crowd/menu.js"></script>
<script type="text/javascript" src="crowd/my_process.js"></script>
<script type="text/javascript" src="js/jquery.pagination.js"></script>
<style>
    .tu img {
        max-height: 200px;
         max-width: 200px;
         width: expression(this.width > 200 && this.height < this.width ? 200: true);
         height: expression(this.height > 200 ? 200: true);
        float: left;
    }
</style>
<script type="text/javascript">
    $(function(){
        $("#title").text("众筹平台 - 流程管理");
        // 1.为分页操作准备初始化数据
        window.current = 1;
        window.pageSize = 5;
        window.keyWord = "";
        window.loadingIndex = -1 ;
        // 2.调用执行分页的函数， 显示分页效果
        generatePage();
        $("#searchBtn").click(function () {
            window.keyWord = $("#keyWordInput").val();
            generatePage();
        });

        $("#uploadPrcDefBtn").click(function () {
            $("#addModal").modal("show");
        });
        $("#saveProcessBtn").click(function () {
            // 将上传的文件封装到FormData对象中
            var formData = new FormData();
            formData.append("bpmnFile",bpmnFile);
            formData.append("viewFile",viewFile);
            // 发送Ajax请求上传文件
            $.ajax({
                "url":"process/upload.json",
                beforeSubmit : function(){
                    loadingIndex = layer.msg('数据正在部署中', {icon: 6});
                    return true ; //必须返回true,否则,请求终止.
                },
                "type":"post",
                "data":formData,
                "contentType":false,
                "processData":false,
                "dataType":"json",
                "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS") {
                        layer.msg("部署成功", {time:1000, icon:6});
                        // 如果上传成功，则从响应体数据中获取图片的访问路径
                        generatePage();
                    }
                    if(result == "FAILED") {
                        layer.msg(response.message, {time:1000, icon:5, shift:6});
                    }
                },
                "error":function(response){
                    layer.msg(response.statusText, {time:1000, icon:5, shift:6});
                }
            });
            $("#addModal").modal("hide");
        });

        $("#processPageBody").on("click",".removeBtn",function () {
            var processName = $(this).parent().prev().prev().prev().text();
            var processArray = [{
                "processId":this.id,
                "processName" : processName
            }];
            showDeleteModel(processArray);
        });
        $("#summaryBox").click(function () {
            var currentStatus = this.checked;
            $(".itemBox").prop("checked",currentStatus);
        });

        $("#processPageBody").on("click",".itemBox",function () {
            var checkedBoxCount = $(".itemBox:checked").length;
            var totalBoxCount = $(".itemBox").length;
            $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);
        });

        $("#batchRemoveBtn").click(function () {
            var processArray= [];
            $(".itemBox:checked").each(function () {
                var processId = this.id;
                var processName = $(this).parent().next().text();
                console.log("processId"+processId);
                console.log("processName"+processName);
                processArray.push({
                    "processId" : processId,
                    "processName" : processName
                });
            });
            if (processArray.length==0){
                layer.msg("请至少选择一个");
                return ;
            }
            showDeleteModel(processArray);
        });
        $("#deleteProcessBtn").click(function () {
            var requestBody = JSON.stringify(processIdArray);
            $.ajax({
                "url":"process/delete/array.json",
                beforeSend : function() {
                    return true ;
                },
                "data":requestBody,
                "type":"post",
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function (response) {
                    var result = response.result;
                    if (result=="SUCCESS"){
                        generatePage();
                    }
                    if (result=="FAILED"){
                        layer.msg("删除流程定义失败", {time:1000, icon:5, shift:6});
                    }
                },
                "error":function (response) {
                    layer.msg("失败说明"+response.statusText, {time:1000, icon:5, shift:6});
                    console.log(response);
                }
            });
            $("#deleteModal").modal("hide");
        });

        $("#processPageBody").on("click",".queryViewBtn",function () {
            $("#showViewModal").modal("show");
            window.processId = this.id;
            $("#pdImg").attr("src", "process/show/view.html?processId="+processId);
        });
        $("#okBtn").click(function () {
            $("#showViewModal").modal("hide");
        });

    });
</script>
<body>

<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
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
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn"
                            class="btn btn-danger" style="float:right;margin-left:10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除</button>

                    <button id="uploadPrcDefBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-upload"></i> 上传流程定义文件
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">

                        <form id="deployForm" action="" method="POST" enctype="multipart/form-data">
                            <input id="processDefFile" style="display:none" type="file" name="processDefFile">
                        </form>


                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义Key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="processPageBody">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
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
<%@include file="/WEB-INF/modal_process_add.jsp" %>
<%@include file="/WEB-INF/modal_process_delete.jsp" %>
<%@include file="/WEB-INF/modal_process_show_view.jsp" %>
</body>
</html>