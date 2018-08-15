<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<button class="layui-btn addUserBtn">
    <i class="layui-icon">&#xe608;</i> 添加
</button>
<table class="layui-table" lay-size="sm">
    <colgroup>
        <col>
        <col>
        <col>
        <col>
        <col>
        <col>
        <col>
    </colgroup>
    <thead>
    <tr>
        <th>ID</th>
        <th>名字</th>
        <th>角色</th>
        <th>最后登录时间</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="userList" scope="request" type="java.util.List"/>
    <c:forEach items="${userList}" var="user">
        <tr class="userInfoRow" data-id="${user.id}">
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.type}</td>
            <td>${user.lastLoginTimeString}</td>
            <td>${user.status== 1?"启用":"禁用"}</td>
            <td>
                <a href="javascript:;" class="editUserBtn">编辑</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/layui/layui.all.js"></script>
<script>
    $('.addUserBtn').on('click', function () {
        layer.open({
            type: 2,
            title: '用户信息',
            fix: false,
            shadeClose: true,
            maxmin: true,
            area: ['700px', '400px'],
            content: '/admin/editUser?editType=0'
        });
    });

    $('.editUserBtn').on('click', function () {
        var userId = $(this).parents('.userInfoRow').attr('data-id');
        layer.open({
            type: 2,
            title: '用户信息',
            fix: false,
            shadeClose: true,
            maxmin: true,
            area: ['700px', '400px'],
            content: '/admin/editUser?editType=1&userId=' + userId
        });
    });
</script>

</body>
</html>