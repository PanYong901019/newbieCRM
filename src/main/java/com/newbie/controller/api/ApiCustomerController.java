package com.newbie.controller.api;

import com.easyond.utils.ObjectUtil;
import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import com.newbie.utils.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customer")
public class ApiCustomerController extends BaseController {

    @RequestMapping(value = "getCustomerList")
    String getCustomerList() {
        String pageNo = getParameter("page");
        String limit = getParameter("limit");
        Map<String, String> search = new HashMap<String, String>() {{
            if (!StringUtil.invalid(getParameter("nameorphone"))) {
                put("nameorphone", getParameter("nameorphone"));
            }
            if (!StringUtil.invalid(getParameter("flag"))) {
                put("flag", getParameter("flag"));
            }
            if (!StringUtil.invalid(getParameter("source"))) {
                put("source", getParameter("source"));
            }
            if (!StringUtil.invalid(getParameter("inputType"))) {
                put("inputType", getParameter("inputType"));
            }
            if (!StringUtil.invalid(getParameter("platform"))) {
                put("platform", getParameter("platform"));
            }
            if (!StringUtil.invalid(getParameter("status"))) {
                put("status", getParameter("status"));
            }
            if (!StringUtil.invalid(getParameter("addTime"))) {
                put("addTime", getParameter("addTime"));
            }
        }};
        Map<String, Object> customerList;
        if (getLoginUserAuth() != 0) {
            Integer loginUserId = getLoginUserId();
            customerList = customerServiceImpl.getCustomerList(loginUserId, search, pageNo, limit);
        } else {
            customerList = customerServiceImpl.getCustomerList(StringUtil.invalid(getParameter("counselorId")) ? null : Integer.valueOf(getParameter("counselorId")), search, pageNo, limit);
        }
        customerList.put("code", 0);
        customerList.put("msg", "");
        return ObjectUtil.mapToJsonString(customerList);
    }

    @RequestMapping(value = "updateCustomer")
    String updateCustomer() {
        String customerId = getParameter("id");
        String counselorId = getParameter("counselorId");
        String name = getParameter("name");
        String phone = getParameter("phone");
        String inputType = getParameter("inputType");
        String platform = getParameter("platform");
        String flag = getParameter("flag");
        String status = getParameter("status");
        List<String> sourceList = Constant.customerSourceConstant.keySet().stream().filter(s -> !StringUtil.invalid(getParameter("source[" + s + "]"))).collect(Collectors.toList());
        customerServiceImpl.updateCustomer(customerId, counselorId, name, phone, inputType, platform, flag, status, sourceList);
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }

    @RequestMapping(value = "allotCustomer")
    String allotCustomer() {
        String customerIds = getParameter("customerIds");
        String counselorId = getParameter("counselorId");
        if (!StringUtil.invalid(customerIds) && customerIds.length() >= 2) {
            List<String> customerIdList = ObjectUtil.jsonStringToArray(customerIds, String.class);
            customerServiceImpl.allotCustomer(counselorId, customerIdList);
        }
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }
}
