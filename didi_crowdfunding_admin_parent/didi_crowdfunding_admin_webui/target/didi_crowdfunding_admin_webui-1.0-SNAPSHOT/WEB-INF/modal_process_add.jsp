<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addModal"  class="modal fade" tabindex="-1" role="dialog">
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
                <form  class="form-signin" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">bpmn文件</label>
                        <div class="col-sm-10">
                            <input type="file" name="headerPicture" style="display: none;" />
                            <button id="uploadBpmnBtn" type="button" class="btn btn-primary  active">上传图片</button>
                            <label class="control-label">文件上无乱码,条理清晰,大小不超过2M。</label>
                            <span id="fileInfo" class="control-label"></span>
                        </div>
                    </div>
                    <br><br><br>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">流程图</label>
                        <div class="col-sm-10">
                            <input type="file"  name="detailPictureList" style="display: none;" />
                            <button type="button" id="uploadViewBtn" class="btn btn-primary  active">上传图片</button>
                            <label class="control-label">支持jpg、jpeg、png、gif格式，大小不超过2M，建议尺寸：宽740px</label>
                            <br><br>
                            <img id="showView" style="display: none;">
                        </div>
                    </div>
                </form>
            </div>
            <script type="text/javascript">

                $("#uploadBpmnBtn").click(function() {
                    // 调用click()函数，相当于被点击了一下
                    document.getElementById("fileInfo").innerText="";
                    $("[name=headerPicture]").click();

                });
                $("[name=headerPicture]").change(function(event) {
                    // 获取用户选中的文件
                    var files = event.target.files;
                    // 使用下标0，选择唯一的一个文件
                    window.bpmnFile = files[0];
                    var fileSize = 0;
                    var fileName = null;
                    fileName = bpmnFile.name;
                    fileSize = bpmnFile.size;
                    console.log(fileName);
                    console.log(fileSize);
                    var info = "文件名:"+fileName+"文件大小:"+fileSize/1024+"MB";
                    document.getElementById("fileInfo").innerText = info;
                    // 获取URL对象
                    // var url = window.url || window.webkitURL;
                    // 调用url对象的createObjectURL()方法获取当前选中的文件在系统中的路径
                    // var path = url.createObjectURL(bpmnFile);
                    // 使用path修改img标签的src属性
                    // $(this).next().next().next().next().attr("value",info).show();
                });
                $("#uploadViewBtn").click(function() {
                    $("[name=detailPictureList]").click();
                });
                $("[name=detailPictureList]").change(function(event) {
                    // 获取用户选中的文件
                    var files = event.target.files;
                    // 使用下标0，选择唯一的一个文件
                    window.viewFile = files[0];
                    // 获取URL对象
                    var url = window.url || window.webkitURL;
                    // 调用url对象的createObjectURL()方法获取当前选中的文件在系统中的路径
                    var path = url.createObjectURL(viewFile);
                    // 使用path修改img标签的src属性
                    $(this).next().next().next().next().next().attr("src", path).attr("class", "tu img").show();
                });
            </script>
            <div class="modal-footer">
                <button id="saveProcessBtn" type="button" class="btn btn-primary"> 保 存
                </button>
            </div>
        </div>
    </div>
</div>
