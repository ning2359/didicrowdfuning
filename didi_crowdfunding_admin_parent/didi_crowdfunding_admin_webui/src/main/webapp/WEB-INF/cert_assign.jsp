<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include-head.jsp" %>
    <script type="text/javascript">
        $(function () {
            <c:forEach items="${certAccttypeList }" var="cert">
            $(":checkbox[certid='${cert.certid}'][accttype='${cert.accttype}']")[0].checked=true ;
            </c:forEach>
            /*
            $(":checkbox[certid='6'][accttype='2']")[0].checked=true ;

            $(":checkbox[certid='7'][accttype='2']")[0].checked=true ;
             */
            $(":checkbox").click(function(){
                var flg = this.checked;
                layer.msg(flg);
                console.log(flg);
                //通过this.certid能否获取自定义的属性值?
                var certid = $(this).attr("certid");
                var accttype = $(this).attr("accttype");
                if ( flg ) {
                    // 增加账户类型和资质的关系
                    $.ajax({
                        type : "POST",
                        url  : "certtype/insertAcctTypeCert.json",
                        data : {
                            certid : certid,
                            accttype : accttype
                        },
                        success : function(response) {
                            var result =  response.result;
                            if (result=="SUCCESS"){
                                layer.msg("分配成功");
                            }
                            if (result=="FAILED"){
                                layer.msg("分类关系删除失败", {time:1000, icon:5, shift:6});
                            }
                        },
                        error:function (response) {
                            layer.msg("分类关系删除失败"+response.message(), {time:1000, icon:5, shift:6});
                        }
                    });
                } else {
                    // 删除账户类型和资质的关系
                    $.ajax({
                        type : "POST",
                        url  : "certtype/deleteAcctTypeCert.json",
                        data : {
                            certid : certid,
                            accttype : accttype
                        },
                        success : function(response) {
                            var result =  response.result;
                            if (result=="SUCCESS"){
                                layer.msg("分配成功");
                            }
                            if (result=="FAILED"){
                                layer.msg("分类关系删除失败", {time:1000, icon:5, shift:6});
                            }
                        },
                        error:function (response) {
                            layer.msg("分类关系删除失败"+response.message(), {time:1000, icon:5, shift:6});
                        }
                    });
                }
            });
        });

    </script>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}

        input[type=checkbox] {
            width:18px;
            height:18px;
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
                        <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th>名称</th>
                                    <th >商业公司</th>
                                    <th >个体工商户</th>
                                    <th >个人经营</th>
                                    <th >政府及非营利组织</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${certs }" var="cert">
                                    <tr>
                                        <td>${cert.name }</td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="0"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="1"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="2"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="3"></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>