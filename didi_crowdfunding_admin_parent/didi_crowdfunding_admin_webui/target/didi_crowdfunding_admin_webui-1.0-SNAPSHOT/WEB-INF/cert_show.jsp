<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include-head.jsp" %>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="js/jquery.pagination.js"></script>
    <script type="text/javascript"  src="crowd/my_cert.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#title").text("众筹平台 -  资质维护");
            // 1.为分页操作准备初始化数据
            window.current = 1;
            window.pageSize = 10;
            window.keyWord = "";
            // 2.调用执行分页的函数， 显示分页效果
            generatePage();
            $("#searchBtn").click(function () {
                window.keyWord = $("#keyWordInput").val();
                generatePage();
            });
            $("#showAddModalBtn").click(function () {
                $("#addModal").modal("show");
            });
            $("#saveCertBtn").click(function () {
                var certName = $.trim($("#addModal [name=certName]").val());
                $.ajax({
                    "url" : "cert/save.json",
                    "type" : "post",
                    "data" : {
                        "name":certName
                    },
                    "dataType" : "json",
                    "success" : function (response) {
                        console.log(response);
                        var result =  response.result;
                        if (result == "SUCCESS"){
                            layer.msg("操作成功");
                            window.current = 8888;
                            generatePage();
                        }
                        if (result=="FAILED"){
                            layer.msg("操作失败"+"失败信息"+response.message);
                        }
                    },
                    "error" : function (response) {
                        console.log(response);
                        layer.msg(response.status+response.statusText);
                    }
                });
                $("#addModal").modal("hide");
                $("#addModal [name = certName]").val("");
            });
            $("#certPageBody").on("click",".pencilBtn",function () {
                $("#updateModal").modal("show");
                var certName = $(this).parent().prev().text();
                console.log("certName"+certName);
                window.certId = this.id;
                $("#updateModal [name = certName]").val(certName);
            });
            $("#updateCertBtn").click(function () {
                var certName = $("#updateModal [name = certName]").val();
                $.ajax({
                    "url":"cert/update.json",
                    "type":"post",
                    "data":{
                        "id":window.certId,
                        "name":certName
                    },
                    "dataType":"json",
                    "success":function (response) {
                        var result = response.result;
                        if (result=="SUCCESS"){
                            layer.msg("更新成功");
                            generatePage();
                        }
                        if (result=="FAILED"){
                            layer.msg("操作失败"+"失败信息"+response.message);
                        }
                    },
                    "error":function (response) {
                        layer.msg("失败码"+response.status+"失败说明"+response.statusText);
                        console.log(response);
                    }
                });
                $("#updateModal").modal("hide");
            });
            $("#certPageBody").on("click",".removeBtn",function () {
                var certName = $(this).parent().prev().text();
                var certArray = [{
                    "certId":this.id,
                    "certName" : certName
                }];
                showDeleteModel(certArray);
            });
            $("#summaryBox").click(function () {
                var currentStatus = this.checked;
                $(".itemBox").prop("checked",currentStatus);
            });

            $("#certPageBody").on("click",".itemBox",function () {
                var checkedBoxCount = $(".itemBox:checked").length;
                var totalBoxCount = $(".itemBox").length;
                $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);
            });

            $("#batchRemoveBtn").click(function () {
                var certArray= [];
                $(".itemBox:checked").each(function () {
                    var certId = this.id;
                    var certName = $(this).parent().next().text();
                    console.log("certName"+certName);
                    certArray.push({
                        "certId" : certId,
                        "certName" : certName
                    });
                });
                if (certArray.length==0){
                    layer.msg("请至少选择一个");
                    return ;
                }
                showDeleteModel(certArray);
            });
            $("#deleteCertBtn").click(function () {
                var requestBody = JSON.stringify(certIdArray);
                $.ajax({
                    "url":"cert/delete/array.json",
                    "data":requestBody,
                    "type":"post",
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function (response) {
                        var result = response.result;
                        if (result=="SUCCESS"){
                            layer.msg("删除成功");
                            generatePage();
                        }
                        if (result=="FAILED"){
                            layer.msg("操作失败"+"失败信息"+response.message);
                        }
                    },
                    "error":function (response) {
                        layer.msg("失败码"+response.status+"失败说明"+response.statusText);
                        console.log(response);
                    }
                });
                $("#deleteModal").modal("hide");
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
                        <form class="form-inline" name="form" style="float:left;">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input id="keyWordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                                </div>
                            </div>
                            <button id="searchBtn" type="button" class="btn btn-warning" ><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                        <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input id="summaryBox" type="checkbox"></th>
                                    <th>名称</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody id="certPageBody">

                                </tbody>
                                <tfoot>
                                <tr >
                                    <td colspan="4" align="center">
                                        <ul class="pagination"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4" align="center">
                                        <div id="Pagination" class="pagination"></div>
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
    <%@include file="/WEB-INF/modal_cert_add.jsp" %>
    <%@include file="/WEB-INF/modal_cert_update.jsp" %>
    <%@include file="/WEB-INF/modal_cert_delete.jsp" %>
</body>

</html>