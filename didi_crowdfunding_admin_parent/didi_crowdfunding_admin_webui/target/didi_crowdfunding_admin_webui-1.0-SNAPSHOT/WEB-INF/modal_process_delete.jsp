<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="deleteModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <h4>请确认是否要删除下列流程：</h4>
               <div id="deleteName" style="font-size: 20px; text-align: center"  ></div>
            </div>
            <div class="modal-footer">
                <button id="deleteProcessBtn" type="button" class="btn btn-primary"> 确定删除
                </button>
            </div>
        </div>
    </div>
</div>
