<%@ page import="com.newbie.model.User" %>
<%@ page import="com.newbie.service.UserService" %>
<%@ page import="com.newbie.utils.Constant" %>
<%@ page import="com.newbie.utils.HttpContextUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    UserService userServiceImpl = (UserService) ctx.getBean("userService");
    List<User> userList = userServiceImpl.getUserByType("common");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
    <style>
        .flagSelect .layui-form-select dl dd[lay-value=""] {
            background: #ffffff;
        }

        .flagSelect .layui-form-select dl dd[lay-value="1"] {
            background: #919191;
        }

        .flagSelect .layui-form-select dl dd[lay-value="2"] {
            background: #1e90ff;
        }

        .flagSelect .layui-form-select dl dd[lay-value="3"] {
            background: #41c558;
        }

        .flagSelect .layui-form-select dl dd[lay-value="4"] {
            background: #fe9350;
        }

        .flagSelect .layui-form-select dl dd[lay-value="5"] {
            background: #fe4757;
        }

        .userFlag[data="0"] {
            color: #ffffff;
        }

        .userFlag[data="1"] {
            color: #919191;
        }

        .userFlag[data="2"] {
            color: #1e90ff;
        }

        .userFlag[data="3"] {
            color: #41c558;
        }

        .userFlag[data="4"] {
            color: #fe9350;
        }

        .userFlag[data="5"] {
            color: #fe4757;
        }
    </style>
</head>


<div class="layui-form">
    <div class="layui-form-item searchItems">
        <div class="layui-input-inline">
            <input class="layui-input searchItem" id="nameorphone" name="nameorphone" placeholder="姓名或电话" autocomplete="off">
        </div>
        <% if (HttpContextUtil.getLoginUserAuth(request) == 0) {%>
        <div class="layui-input-inline">
            <select id="counselorId" lay-filter="searchItem" name="counselorId" lay-search="">
                <option value="">咨询师</option>
                <option value="0">未分配</option>
                <% for (User user : userList) { %>
                <option value="<%=user.getId()%>"><%=user.getName()%>
                </option>
                <%}%>
            </select>
        </div>
        <%}%>
        <div class="layui-input-inline flagSelect">
            <select id="flag" lay-filter="searchItem" name="flag" lay-search="">
                <option value="">标识</option>
                <% for (Map.Entry<String, String> entry : Constant.customerFlagConstant.entrySet()) { %>
                <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="layui-input-inline">
            <select id="source" lay-filter="searchItem" name="source" lay-search="">
                <option value="">来源</option>
                <% for (Map.Entry<String, String> entry : Constant.customerSourceConstant.entrySet()) { %>
                <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="layui-input-inline">
            <select id="inputType" lay-filter="searchItem" name="inputType" lay-search="">
                <option value="">录入类型</option>
                <% for (Map.Entry<String, String> entry : Constant.customerInputTypeConstant.entrySet()) { %>
                <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="layui-input-inline">
            <select id="platform" lay-filter="searchItem" name="platform" lay-search="">
                <option value="">平台</option>
                <% for (Map.Entry<String, String> entry : Constant.customerPlatformConstant.entrySet()) { %>
                <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="layui-input-inline">
            <select id="status" lay-filter="searchItem" name="status" lay-search="">
                <option value="">状态</option>
                <option value="0">无效</option>
                <option value="1">有效</option>
            </select>
        </div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="addTime" name="addTime" placeholder=" 开始时间 - 结束时间 ">
        </div>
    </div>
</div>

<table id="customerList" lay-filter="customerList"></table>

<div class="layui-btn-group customerListOption">
    <button class="layui-btn" data-type="allot">指派</button>
    <button class="layui-btn" data-type="exportList">导出此列表</button>
</div>

<script type="text/html" id="usernameTpl">
    <i class="layui-icon layui-icon-star userFlag" style='font-size: 18px; ' data='{{d.flag}}'></i>
    <a href="javascript:;">{{d.name}}</a>
</script>

<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="record">记录</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>

<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/layui/layui.all.js"></script>
<script>
    var currPage = 1;
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, table = layui.table, laydate = layui.laydate;
        table.render({
            elem: '#customerList',
            id: 'customerList',
            height: 'full-280',
            page: true,
            limit: 30,
            url: '/api/customer/getCustomerList',
            cols: [[
                {type: 'checkbox', title: 'ID'},
                {field: 'id', title: '编号', width: 100, align: 'center'},
                {field: 'addTimeString', title: '添加时间', width: 165},
                {field: 'name', title: '姓名', width: 100, templet: '#usernameTpl', unresize: true},
                {field: 'phone', title: '手机号', width: 120},
                {field: 'source', title: '来源'},
                {field: 'platform', title: '平台', width: 80, align: 'right'},
                {field: 'inputTypeName', title: '录入类型', width: 120, align: 'right'},
                <% if (HttpContextUtil.getLoginUserAuth(request) == 0) {%>
                {field: 'counselorName', title: '咨询师', width: 80, align: 'right'},
                <%}%>
                {field: 'statusName', title: '状态', width: 65, align: 'right'},
                {fixed: 'right', title: '', width: 178, align: 'center', toolbar: '#toolBar'}
            ]],
            done: function (res, curr, count) {
                currPage = curr;
            }
        });


        table.on('tool(customerList)', function (obj) {
            var data = obj.data;
            if (obj.event === 'record') {
                layer.open({
                    type: 2,
                    title: '客户信息--' + data.name + "--" + data.phone,
                    fix: false,
                    shadeClose: true,
                    maxmin: true,
                    area: ['700px', '450px'],
                    content: '/admin/recordCustomer?customerId=' + data.id
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    title: '客户信息',
                    fix: false,
                    shadeClose: true,
                    maxmin: true,
                    area: ['700px', '450px'],
                    content: '/admin/editCustomer?editType=1&customerId=' + data.id
                });
            }
        });

        var $ = layui.$, active = {
            allot: function () {
                var data = table.checkStatus('customerList').data;
                if (data.length !== 0) {
                    var customerIds = [];
                    for (var item in data) {
                        customerIds.push(data[item].id);
                    }
                    layer.open({
                        type: 1,
                        shadeClose: true,
                        btn: ['确定'],
                        yes: function (index) {
                            var allotTocounselorId = $("#allotTocounselorId").val();
                            var allotPercent = $("#allotPercent").val();
                            $.ajax({
                                url: '/api/customer/allotCustomer',
                                type: 'POST',
                                data: {customerIds: JSON.stringify(customerIds), counselorId: allotTocounselorId, allotPercent: allotPercent},
                                dataType: "json",
                                success: function (result) {
                                    if (result.rspCode === 0) {
                                        alert(result.rspInfo);
                                    } else {
                                        layer.msg(result.rspInfo, {
                                            time: 2000,
                                            btn: ['好哒']
                                        }, function () {
                                            layer.close(index);
                                            table.reload('customerList', {
                                                page: {curr: currPage}
                                            });
                                        });
                                    }
                                }
                            });
                        },
                        cancel: function () {
                        },
                        shift: 1,
                        content: '<select id="allotTocounselorId" class="layui-form-select layui-input layui-select"><option value="0">选择咨询师</option>' +
                        <% for (User user : userList) { %>
                        '<option value="<%=user.getId()%>" <%= user.getStatus() == 1 ? "" : "disabled" %>><%=user.getName()%></option>' +
                        <% }%>
                        '</select>' +
                        (table.checkStatus('customerList').isAll ?
                            '分配比率' +
                            '<select id="allotPercent" class="layui-form-select layui-input layui-select">' +
                            '<option value="100">100%</option>' +
                            '<option value="90">90%</option>' +
                            '<option value="80">80%</option>' +
                            '<option value="70">70%</option>' +
                            '<option value="60">60%</option>' +
                            '<option value="50">50%</option>' +
                            '<option value="40">40%</option>' +
                            '<option value="30">30%</option>' +
                            '<option value="20">20%</option>' +
                            '<option value="10">10%</option>' +
                            '</select>' : '')

                    });
                } else {
                    layer.msg("您还没选学生呢！")
                }
            },
            exportList: function () {
                var a = document.createElement('a');
                var url = '/api/customer/exportCustomerList?nameorphone=' + $("#nameorphone").val() + '&flag=' + $("#flag").val() + '&counselorId=' + $("#counselorId").val() + '&source=' + $("#source").val() + '&inputType=' + $("#inputType").val() + '&platform=' + $("#platform").val() + '&status=' + $("#status").val();
                if ($("#addTime").attr("data")) {
                    url = url + '&addTime=' + $("#addTime").attr("data")
                }
                a.href = url;
                a.click();
            }
        };


        laydate.render({
            elem: '#addTime',
            type: 'date',
            range: true,
            done: function (value, startDate, endDate) {
                var startDateString = startDate.year + "-" + (JSON.stringify(startDate.month).length < 2 ? "0" + startDate.month : startDate.month) + "-" + (JSON.stringify(startDate.date).length < 2 ? "0" + startDate.date : startDate.date)
                var endDateString = endDate.year + "-" + (JSON.stringify(endDate.month).length < 2 ? "0" + endDate.month : endDate.month) + "-" + (JSON.stringify(endDate.date).length < 2 ? "0" + endDate.date : endDate.date)
                $('#addTime').attr("data", startDateString + "|" + endDateString);
                doSearch();
            }
        });

        form.on('select(searchItem)', function (data) {
            doSearch();
        });

        $('.searchItem').on('input', function () {
            doSearch();
        });

        function doSearch() {
            table.reload('customerList', {
                where: {
                    nameorphone: $("#nameorphone").val(),
                    flag: $("#flag").val(),
                    counselorId: $("#counselorId").val(),
                    source: $("#source").val(),
                    inputType: $("#inputType").val(),
                    platform: $("#platform").val(),
                    status: $("#status").val(),
                    addTime: $("#addTime").attr("data")
                },
                page: {
                    curr: 1
                }
            });
        }

        $('.customerListOption .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>

</body>
</html>