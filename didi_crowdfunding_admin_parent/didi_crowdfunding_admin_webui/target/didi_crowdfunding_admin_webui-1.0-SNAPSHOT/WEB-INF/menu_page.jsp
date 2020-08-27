<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css"/>
    <script type="text/javascript"  src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" charset="utf-8"  src="crowd/my_menu.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#title").text("众筹平台 - 菜单维护");
            generateTree();
            $("#treeDemo").on("click",".addBtn",function () {
                window.pid = this.id;
                $("#menuAddModal").modal("show");
                return false;
            });
            $("#menuSaveBtn").click(function () {
               var name = $.trim($("#menuAddModal [name=name]").val());
               var url = $.trim($("#menuAddModal [name=url]").val());
               var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
               $.ajax({
                   "url":"menu/save.json",
                   "type":"post",
                   "data":{
                       "name":name,
                        "pid":window.pid,
                       "icon":icon,
                       "url":url
                   },
                   "dataType":"json",
                   "success":function (response) {
                       var result =  response.result;
                       if (result == "SUCCESS"){
                           layer.msg("操作成功");
                           generateTree();
                       }
                       if (result=="FAILED"){
                           layer.msg("操作失败"+"失败信息"+response.message);
                       }
                   },
                   "error" : function (response) {
                       layer.msg(response.status+response.statusText);
                   }
               });
                $("#menuAddModal").modal("hide");
                $("#menuResetBtn").click();
            });
            $("#treeDemo").on("click",".editBtn",function () {
                window.id = this.id;
                $("#menuEditModal").modal("show");
                var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var key = "id";
                var value = window.id;
                 var currentNote =  zTreeObj.getNodeByParam(key,value);
                $("#menuEditModal [name=name]").val(currentNote.name);
                $("#menuEditModal [name=url]").val(currentNote.url);
                $("#menuEditModal [name=icon]").val([currentNote.icon]);
                return false;
            });
            $("#menuEditBtn").click(function () {
                var name = $.trim($("#menuEditModal [name=name]").val());
                var url = $.trim($("#menuEditModal [name=url]").val());
                var icon = $.trim($("#menuEditModal [name=icon]:checked").val());
                $.ajax({
                    "url":"menu/update.json",
                    "type":"post",
                    "data":{
                        "id":window.id,
                        "name":name,
                        "icon":icon,
                        "url":url
                    },
                    "dataType":"json",
                    "success":function (response) {
                        var result =  response.result;
                        if (result == "SUCCESS"){
                            layer.msg("操作成功");
                            generateTree();
                        }
                        if (result=="FAILED"){
                            layer.msg("操作失败"+"失败信息"+response.message);
                        }
                    },
                    "error" : function (response) {
                        layer.msg(response.status+response.statusText);
                    }
                });
                $("#menuEditModal").modal("hide");
            });
            $("#treeDemo").on("click",".removeBtn",function () {
                window.id = this.id;
                $("#menuConfirmModal").modal("show");
                var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var key = "id";
                var value = window.id;
                var currentNote =  zTreeObj.getNodeByParam(key,value);
                $("#removeNodeSpan").html(" 【 <i class='"+currentNote.icon+"'></i>"+currentNote.name+"】 ");
                return false;
            });
            $("#confirmBtn").click(function () {
               $.ajax({
                   "url":"menu/delete.json",
                   "data":{
                       "id":window.id
                   },
                   "type":"post",
                   "dataType":"json",
                   "success":function (response) {
                       var result =  response.result;
                       if (result == "SUCCESS"){
                           layer.msg("操作成功");
                           generateTree();
                       }
                       if (result=="FAILED"){
                           layer.msg("操作失败"+"失败信息"+response.message);
                       }
                   },
                   "error" : function (response) {
                       layer.msg(response.status+response.statusText);
                   }
               });
            });
            $("#menuConfirmModal").modal("hide");
        });
    </script>
<body>
    <%@ include file="/WEB-INF/include-nav.jsp" %>
    <div class="container-fluid">
        <div class="row">
            <%@ include file="/WEB-INF/include-sidebar.jsp" %>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="panel panel-default">
                    <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                    <div class="panel-body">
                        <ul id="treeDemo" class="ztree" style="user-select: none;">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/modal_menu_add.jsp" %>
    <%@include file="/WEB-INF/modal_menu_confirm.jsp" %>
    <%@include file="/WEB-INF/modal_menu_edit.jsp" %>
</body>
</html>