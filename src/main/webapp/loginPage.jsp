<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录 - 泰通大有CRM</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layui/css/admin.css" media="all">
    <link rel="stylesheet" href="/static/layui/css/login.css" media="all">
    <link id="layuicss-layer" rel="stylesheet" href="/static/layui/css/layer.css" media="all">
</head>
<body data-ext-version="1.4.2" layadmin-themealias="default">

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>TTDY CRM</h2>
            <p>泰通大有第三方平台支撑式CRM系统</p>
        </div>

        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <form class="loginForm" action="/api/login" method="post">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
                    <input type="text" name="name" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input username">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
                    <input type="password" name="password" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input password">
                </div>
            </form>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid doLogin">登 入</button>
            </div>
        </div>
    </div>
    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2018 <a href="http://www.ttdygame.com/" target="_blank">ttdygame.com</a></p>
    </div>
</div>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/layui/layui.all.js"></script>

<script language="JavaScript">
    $(".doLogin").on("click", function () {
        var index = layer.load(0, {shade: [0.5, '#000']});
        var form = $(".loginForm");
        var reqData = {
            "name": $(".loginForm .username").val(),
            "password": $(".loginForm .password").val()
        };
        $.ajax({
            url: form.attr("action"),
            type: form.attr("method"),
            data: reqData,
            dataType: "json",
            success: function (data) {
                if (data.rspCode === 0) {
                    layer.close(index);
                    layer.msg(data.rspInfo);
                } else {
                    window.location.reload();
                }
            }
        });
    });
</script>
</body>
</html>