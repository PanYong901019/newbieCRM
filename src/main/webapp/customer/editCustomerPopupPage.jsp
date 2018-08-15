<%@ page import="com.newbie.model.Customer" %>
<%@ page import="com.newbie.model.User" %>
<%@ page import="com.newbie.service.UserService" %>
<%@ page import="com.newbie.utils.Constant" %>
<%@ page import="com.newbie.utils.HttpContextUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer editType = (Integer) request.getAttribute("editType"); %>
<% Customer customer = (Customer) request.getAttribute("customer"); %>

<%
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    UserService userServiceImpl = (UserService) ctx.getBean("userService");
%>
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
    ${editType == 0?"<legend>填写客户信息</legend>":"<legend>修改客户信息</legend>"}
</fieldset>

<form method="post" class="layui-form editCustomerForm" ${editType == 0?" action=\"/api/customer/createCustomer\"":" action=\"/api/customer/updateCustomer\""} lay-filter="editCustomerForm">
    <input type="hidden" name="id" value="${customer != null?customer.id:""}"/>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">手机号码</label>
            <div class="layui-input-inline">
                <input type="text" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input" ${editType == 0?"":" disabled"}>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">平台</label>
            <div class="layui-input-inline">
                <select name="platform" lay-verify="required" lay-search="">
                    <option value="">直接选择或搜索选择</option>
                    <% for (Map.Entry<String, String> entry : Constant.customerPlatformConstant.entrySet()) { %>
                    <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                    </option>
                    <% }%>

                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">录入类型</label>
            <div class="layui-input-inline">
                <select name="inputType" lay-verify="required" lay-search="">
                    <option value="">直接选择或搜索选择</option>
                    <% for (Map.Entry<String, String> entry : Constant.customerInputTypeConstant.entrySet()) { %>
                    <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                    </option>
                    <% }%>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">咨询师</label>
            <div class="layui-input-inline">
                <select name="counselorId" lay-verify="required" lay-search="">
                    <option value="0"  <%= HttpContextUtil.getLoginUserAuth(request) == 0 ? "" : "disabled" %>>直接选择或搜索选择</option>
                    <% for (User user : userServiceImpl.getUserByType("common")) { %>
                    <option value="<%=user.getId()%>" <%= HttpContextUtil.getLoginUserAuth(request) == 0 ? "" : "disabled" %>><%=user.getName()%>
                    </option>
                    <%}%>

                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-inline">
                <select name="status" lay-verify="required" lay-search="">
                    <option value="">直接选择或搜索选择</option>
                    <option value="0">无效</option>
                    <option value="1">有效</option>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">来源</label>
        <div class="layui-input-block">
            <% for (Map.Entry<String, String> entry : Constant.customerSourceConstant.entrySet()) { %>
            <input type="checkbox" name="source[<%=entry.getKey()%>]" title="<%=entry.getValue()%>" ${editType == 0?"":" disabled"}>
            <% }%>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="editCustomerSubmitBtn">立即提交</button>
        </div>
    </div>
</form>

<script src="/static/layui/layui.all.js"></script>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form, layer = layui.layer;
        form.val('editCustomerForm', JSON.parse('<%=customer != null?customer.toString():""%>'));
        form.on('submit(editCustomerSubmitBtn)', function (formData) {
            var editCustomerForm = $(".editCustomerForm");
            $.ajax({
                url: editCustomerForm.attr("action"),
                type: editCustomerForm.attr("method"),
                data: formData.field,
                dataType: "json",
                success: function (result) {
                    if (result.rspCode === 0) {
                        alert(result.rspInfo);
                    } else {
                        layer.msg(result.rspInfo, {
                            time: 2000,
                            btn: ['好哒']
                        }, function () {
                            parent.layui.table.reload('customerList', {
                                page: {curr: parent.currPage}
                            });
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
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
