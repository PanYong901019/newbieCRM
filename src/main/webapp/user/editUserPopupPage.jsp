<%@ page import="com.newbie.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer editType = (Integer) request.getAttribute("editType"); %>
<% User user = (User) request.getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    ${editType == 0?"<legend>请填写员工信息</legend>":"<legend>请修改员工信息</legend>"}
</fieldset>

<form method="post" class="layui-form editUserForm" ${editType == 0?" action=\"/api/user/createUser\"":" action=\"/api/user/updateUser\""} lay-filter="editUserForm">
    <input type="hidden" name="id" value="${user != null?user.id:""}"/>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="text" name="password" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block">
            <input type="radio" name="type" value="admin" title="管理员">
            <input type="radio" name="type" value="common" title="咨询师" checked="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">启用</label>
        <div class="layui-input-block">
            <input type="checkbox" name="status" lay-skin="switch" lay-text="ON|OFF">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="editUserSubmitBtn">立即提交</button>
        </div>
    </div>
</form>

<script src="/static/layui/layui.all.js"></script>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form, layer = layui.layer;
        form.val('editUserForm', JSON.parse('<%=user != null ?user.toString():""%>'));
        form.on('submit(editUserSubmitBtn)', function (formData) {
            var editUserForm = $(".editUserForm");
            $.ajax({
                url: editUserForm.attr("action"),
                type: editUserForm.attr("method"),
                data: formData.field,
                dataType: "json",
                success: function (result) {
                    if (result.rspCode === 0) {
                        alert(result.rspInfo);
                    } else {
                        layer.msg(result.rspInfo, {
                            time: 2000,
                            btn: ['明白了']
                        }, function () {
                            parent.location.reload();
                        });
                    }
                }
            });
            return false;
        });
    });
</script>

</body>
</html>
