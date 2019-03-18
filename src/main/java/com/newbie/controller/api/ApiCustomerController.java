package com.newbie.controller.api;

import com.easyond.utils.DateUtil;
import com.easyond.utils.ObjectUtil;
import com.easyond.utils.PoiUtil;
import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import com.newbie.model.Customer;
import com.newbie.utils.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customer", produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value = "exportCustomerList")
    void exportCustomerList(HttpServletResponse res) {
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
            customerList = customerServiceImpl.getCustomerList(loginUserId, search, "1", "1000000");
        } else {
            customerList = customerServiceImpl.getCustomerList(StringUtil.invalid(getParameter("counselorId")) ? null : Integer.valueOf(getParameter("counselorId")), search, "1", "1000000");
        }
        LinkedHashMap<String, LinkedList<List>> map = new LinkedHashMap<>();
        LinkedList<List> rows = new LinkedList<List>() {{
            add(new ArrayList<Object>() {{
                add("dbId");
                add("添加时间");
                add("姓名");
                add("电话号");
            }});
        }};
        ((List<Customer>) customerList.get("data")).forEach(customer -> {
            List cols = new ArrayList();
            cols.add(customer.getId());
            cols.add(customer.getAddTimeString());
            cols.add(customer.getName());
            cols.add(customer.getPhone());
            rows.add(cols);
        });
        map.put("car", rows);
        List<Integer> l = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        try {
            ByteArrayOutputStream byteArrayOutputStream = PoiUtil.doWriterExcel(map, l, ".xlsx");
            res.setHeader("content-type", "application/octet-stream");
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + DateUtil.getDateString(Calendar.getInstance(Locale.CHINA).getTime(), "yyyy-MM-dd") + ".xlsx");
            OutputStream os = res.getOutputStream();
            os.write(byteArrayOutputStream.toByteArray());
            os.flush();
        } catch (IOException e) {
        }
    }

    @RequestMapping(value = "exportCustomerListByDate")
    void exportCustomerListByDate(HttpServletResponse res) {
        String day = getParameter("day");
        if (StringUtil.invalid(day)) {
            day = DateUtil.getDateString(Calendar.getInstance(Locale.CHINA).getTime(), "yyyy-MM-dd");
        }
        List<Customer> customerListByDay = customerServiceImpl.getCustomerListByDay(day);
        List<String> collect = customerListByDay.stream().map(customer -> customer.getId().toString()).collect(Collectors.toList());
        List<Map<String, String>> maps = yicheServiceImpl.postDataToYicheServer(collect);
        LinkedHashMap<String, LinkedList<List>> map = new LinkedHashMap<>();
        LinkedList<List> rows = new LinkedList<List>() {{
            add(new ArrayList<Object>() {{
                add("dbId");
                add("留资时间");
                add("姓名");
                add("性别(1:男 2:女 0:其他)");
                add("手机号");
                add("车款名称");
                add("车款ID");
                add("车系名称");
                add("车系ID");
                add("子品牌名称");
                add("子品牌ID");
                add("品牌名称");
                add("品牌ID");
                add("城市名称");
                add("城市ID");
                add("经销商名称");
            }});
        }};

        maps.forEach(customer -> {
            List cols = new ArrayList();
            cols.add(customer.get("id"));
            cols.add(customer.get("createTime"));
            cols.add(customer.get("userName"));
            cols.add(customer.get("sex"));
            cols.add(customer.get("phone"));
            cols.add(customer.get("carName"));
            cols.add(customer.get("carId"));
            cols.add(customer.get("cmdName"));
            cols.add(customer.get("cmdId"));
            cols.add(customer.get("cmkName"));
            cols.add(customer.get("cmkId"));
            cols.add(customer.get("cmbName"));
            cols.add(customer.get("cmbId"));
            cols.add(customer.get("cityName"));
            cols.add(customer.get("cityId"));
            cols.add("");
            rows.add(cols);
        });
        map.put("car", rows);
        List<Integer> l = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        try {
            ByteArrayOutputStream byteArrayOutputStream = PoiUtil.doWriterExcel(map, l, ".xlsx");
            res.setHeader("content-type", "application/octet-stream");
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + DateUtil.getDateString(Calendar.getInstance(Locale.CHINA).getTime(), "yyyy-MM-dd") + "day" + ".xlsx");
            OutputStream os = res.getOutputStream();
            os.write(byteArrayOutputStream.toByteArray());
            os.flush();
        } catch (IOException e) {
        }


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
        String allotPercent = getParameter("allotPercent");
        if (!StringUtil.invalid(customerIds) && customerIds.length() >= 2) {
            List<String> customerIdList = ObjectUtil.jsonStringToArray(customerIds, String.class);
            Double i = customerIdList.size() * (StringUtil.invalid(allotPercent) ? 1 : (Double.valueOf(allotPercent) / 100));
            if (i.intValue() != customerIdList.size()) {
                Collections.shuffle(customerIdList);
                List<String> yes = customerIdList.subList(0, i.intValue());
                List<String> no = customerIdList.subList(i.intValue(), customerIdList.size());
                customerServiceImpl.allotCustomer(counselorId, yes);
                customerServiceImpl.allotCustomer("-1", no);
            } else {
                customerServiceImpl.allotCustomer(counselorId, customerIdList);
            }
        }
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }
}
