<%@ page import="com.newbie.model.Customer" %>
<%@ page import="com.newbie.utils.Constant" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Customer customer = (Customer) request.getAttribute("customer"); %>
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
            background: #fac35e;
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
    </style>
</head>
<body>
<table lay-filter="customerRecordList" class="layui-table" lay-data="{id:'customerList',height: 'full-80',width:'full', cellMinWidth: 80, page: true, limit:10, url:'/api/customerRecord/getCustomerRecordList?customerId=<%=customer.getId()%>'}">
    <thead>
    <tr>
        <th lay-data="{field:'recordTimeString', width:170}">添加时间</th>
        <th lay-data="{field:'counselorName'}">录入人</th>
        <th lay-data="{field:'content',width:445}">信息</th>
    </tr>
    </thead>
</table>

<div class="layui-form" style="text-align: left; padding-left: 30px;" lay-filter="flagSelectForm">
    <div class="layui-btn-group customerListOption">
        <button class="layui-btn" data-type="addCustomerRecord">添加</button>
    </div>
    <div class="layui-input-inline">
        <div class="layui-form-item flagSelect" style="margin-bottom: 0;">
            <select id="flag" lay-filter="searchItem" name="flag" lay-search="">
                <option value="">标识</option>
                <% for (Map.Entry<String, String> entry : Constant.customerFlagConstant.entrySet()) { %>
                <option value="<%= entry.getKey()%>"><%= entry.getValue()%>
                </option>
                <% }%>
            </select>
        </div>
    </div>
</div>


<script src="/static/layui/layui.all.js"></script>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var table = layui.table, form = layui.form;

        var $ = layui.$, active = {
            addCustomerRecord: function () {
                layer.prompt({title: '随便写点啥，并确认', formType: 2}, function (text, index) {
                    $.ajax({
                        url: '/api/customerRecord/addCustomerRecord',
                        type: 'POST',
                        data: {content: text, customerId: '<%=customer.getId()%>'},
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
                                    parent.layui.table.reload('customerList', {
                                        page: {curr: parent.currPage}
                                    });
                                });
                            }
                        }
                    });
                    table.reload('customerRecordList');
                });
            }
        };
        form.val('flagSelectForm', {flag:<%=customer.getFlag()%>});
        form.on('select(searchItem)', function (data) {
            $.ajax({
                url: '/api/customer/updateCustomer',
                type: 'POST',
                data: {id: '<%=customer.getId()%>', flag: $("#flag").val()},
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
                        });
                    }
                }
            });
        });

        $('.customerListOption .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>

</body>
</html>
