<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <link rel="stylesheet" href="ztree/zTreeStyle.css"/>
    <script type="text/javascript" src="js/jquery.pagination.js"></script>
    <script type="text/javascript"  src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript"  src="crowd/my_role.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#title").text("众筹平台 - 角色维护");
        // 1.为分页操作准备初始化数据
            window.current = 1;
            window.pageSize = 5;
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
            $("#saveRoleBtn").click(function () {
                var roleName = $.trim($("#addModal [name=roleName]").val());

                $.ajax({
                    "url" : "role/save.json",
                    "type" : "post",
                    "data" : {
                        "name":roleName
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
                $("#addModal [name = roleName]").val("");
            });
           $("#rolePageBody").on("click",".pencilBtn",function () {
                $("#updateModal").modal("show");
                var roleNmae = $(this).parent().prev().text();
                window.roleId = this.id;
                $("#updateModal [name = roleName]").val(roleNmae);
            });
            $("#updateRoleBtn").click(function () {
                var roleNmae = $("#updateModal [name = roleName]").val();
                $.ajax({
                    "url":"role/update.json",
                    "type":"post",
                    "data":{
                        "id":window.roleId,
                        "name":roleNmae
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
           $("#rolePageBody").on("click",".removeBtn",function () {
               var roleName = $(this).parent().prev().text();
               var roleArray = [{
                   "roleId":this.id,
                   "roleName" : roleName
               }];
               showDeleteModel(roleArray);
           });
           $("#summaryBox").click(function () {
               var currentStatus = this.checked;
               $(".itemBox").prop("checked",currentStatus);
           });

           $("#rolePageBody").on("click",".itemBox",function () {
               var checkedBoxCount = $(".itemBox:checked").length;
               var totalBoxCount = $(".itemBox").length;
               $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);
           });
           $("#batchRemoveBtn").click(function () {
               var roleArray= [];
               $(".itemBox:checked").each(function () {
                   var roleId = this.id;
                   var roleName = $(this).parent().next().text();
                   roleArray.push({
                       "roleId" : roleId,
                       "roleName" : roleName
                   });
               });
               if (roleArray.length==0){
                   layer.msg("请至少选择一个");
                   return ;
               }
               showDeleteModel(roleArray);
           });
            $("#deleteRoleBtn").click(function () {
               var requestBody = JSON.stringify(roleIdArray);
                $.ajax({
                    "url":"role/delete/array.json",
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
            $("#rolePageBody").on("click",".checkBtn",function () {
               $("#assignModal").modal("show");
               window.roleId = this.id;
                fillAuthTree();
            });
            $("#assignBtn").click(function () {
               var authIdArray = [];
                var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                var auths= zTreeObj.getCheckedNodes();
                for (var i = 0;i<auths.length;i++){
                    var auth =auths[i];
                    var authId = auth.id;
                    authIdArray.push(authId);
                }
                var requestBody  = {
                  "authIdArray" : authIdArray,
                  "roleId":[window.roleId]
                };
                 requestBody = JSON.stringify(requestBody);
                $.ajax({
                    "url":"assign/do/role/assign/auth.json",
                    "type":"post",
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function (response) {
                      var result =  response.result;
                        if (result=="SUCCESS"){
                            layer.msg("权限分配成功");
                        }
                        if (result=="FAILED"){
                            layer.msg("操作失败"+response.message);
                        }
                    },
                    "error":function (response) {
                        console.log(response);
                        layer.msg("操作失败，失败码"+response.status+"失败说明"+response.statusText)
                    }
                });
                $("#assignModal").modal("hide");
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
                                <input  id="keyWordInput"  class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead >
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">

                            </tbody>
                            <tfoot>
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
    <%@include file="/WEB-INF/modal_role_add.jsp" %>
    <%@include file="/WEB-INF/modal_role_update.jsp" %>
    <%@include file="/WEB-INF/modal_role_delete.jsp" %>
    <%@include file="/WEB-INF/modal_role_assign_auth.jsp" %>
    </body>
</html>