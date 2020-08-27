<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="auditModal"  class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 900px;height:900px;">
        <div class="modal-content" style=“width:900px";height:900px;”>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body col-md-12 column">
                <form id="auditForm" role="form">

                </form>
            </div>
            <div class="modal-footer">
                <button id="passBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 审核通过</button>
                <button id="refuseBtn" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 审核拒绝</button>
            </div>
        </div>
    </div>
</div>
