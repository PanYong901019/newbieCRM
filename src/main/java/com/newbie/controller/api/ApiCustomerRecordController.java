package com.newbie.controller.api;

import com.easyond.utils.ObjectUtil;
import com.newbie.controller.BaseController;
import com.newbie.model.CustomerRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/customerRecord")
public class ApiCustomerRecordController extends BaseController {

    @RequestMapping(value = "getCustomerRecordList")
    String getCustomerList() {
        String pageNo = getParameter("page");
        String limit = getParameter("limit");
        String customerId = getParameter("customerId");
        Map<String, Object> customerList = customerRecordServiceImpl.getCustomerRecordListByCustomerId(customerId, pageNo, limit);
        customerList.put("code", 0);
        customerList.put("msg", "");
        return ObjectUtil.mapToJsonString(customerList);
    }

    @RequestMapping(value = "addCustomerRecord")
    String addCustomerRecord() {
        String content = getParameter("content");
        Integer customerId = Integer.valueOf(getParameter("customerId"));
        Integer counselorId = getLoginUserId();
        CustomerRecord customerRecord = new CustomerRecord(null, counselorId, customerId, content);
        customerRecordServiceImpl.createCustomerRecord(customerRecord);
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }
}
