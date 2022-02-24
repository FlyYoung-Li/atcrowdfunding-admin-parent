<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--update--%>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">众筹网系统弹窗</h4>
            </div>

            <div class="modal-body">
                <%--update--%>
                        <input name="name" type="text" class="form-control"
                               id="roleUpdate" value="" autofocus>
            </div>
            <div class="modal-footer">
                <%--update--%>
                <button id="updateRoleBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
