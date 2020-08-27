<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include-head.jsp" %>
    <script type="text/javascript" src="crowd/my_realname.js"></script>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="js/jquery.pagination.js"></script>
    <script type="text/javascript">
        $(function () {
                $("#title").text("众筹平台 - 实名审核");
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
            $("#realnamePageBody").on("click",".auditBtn",function () {
                $("#auditModal").modal("show");
                window.taskId = this.id;
                window.memberId = this.name;
                window.prodef = this.prodef;
                console.log(prodef);
                console.log("aaa"+memberId);
                console.log(taskId);
                generateAuditModal()
            });
            $("#passBtn").click(function(){
                $.ajax({
                    type : "POST",
                    url  : "realname/cert/pass.json",
                    data : {
                        taskId : window.taskId,
                        memberId : window.memberId
                        // prodef : window.prodef
                    },
                    success : function(response) {
                        layer.msg("处理成功", {time:1000, icon:6});
                        generatePage();
                    },
                    error:function (response) {
                        layer.msg(response.message, {time:1000, icon:5, shift:6});
                    }
                });
                $("#auditModal").modal("hide");
            });
            $("#refuseBtn").click(function(){
                $.ajax({
                    type : "POST",
                    url  : "realname/cert/refuse.json",
                    data : {
                        taskId : window.taskId,
                        memberId : window.memberId
                        // prodef : window.prodef
                    },
                    success : function(response) {
                        layer.msg("处理成功", {time:1000, icon:6});
                        generatePage();
                    },
                    error:function (response) {
                        layer.msg(response.message, {time:1000, icon:5, shift:6});
                    }
                });
                $("#auditModal").modal("hide");
            });
        });
    </script>
    <style>
        .tu img{
            max-height:200px;
             max-width:200px;
             width:expression(this.width > 200 && this.height < this.width ? 200: true);
             height:expression(this.height > 200 ? 200: true);
            float:left;
        }
    </style>
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
                                    <input id="keyWordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                                </div>
                            </div>
                            <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr>
                                    <th width="30">#</th>
                                    <th>流程定义名称</th>
                                    <th>流程定义版本</th>
                                    <th>任务名称</th>
                                    <th>会员名称</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody id="realnamePageBody">

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
    <%@include file="/WEB-INF/modal_realname_audit.jsp" %>
</body>
</html>