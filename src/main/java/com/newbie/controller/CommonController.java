package com.newbie.controller;

import com.easyond.utils.ObjectUtil;
import com.easyond.utils.PoiUtil;
import com.easyond.utils.SendEmail;
import com.easyond.utils.StringUtil;
import com.newbie.model.Customer;
import com.newbie.model.YicheCity;
import com.newbie.model.YicheProduct;
import com.newbie.utils.Common;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CommonController extends BaseController {


    @ResponseBody
    @RequestMapping(value = "/apiError")
    String error() {
        String type = getParameter("type");
        switch (type) {
            case "1":
                rspInfo = "没有权限";
                break;
            case "2":
                rspInfo = "访问异常";
                break;
            default:
        }
        return getResultJsonString();
    }

    @RequestMapping(value = "/webError")
    String errorPage() {
        return "errorPage";
    }

    @ResponseBody
    @RequestMapping(value = "/v1/sms/sendSms")
    String sendSms() {
        String phone = getParameter("phone");
        commonServiceImpl.sendSms(phone);
        Map<String, Object> result = new HashMap<>();
        result.put("success", 1);
        result.put("msg", "验证码发送成功!");
        return ObjectUtil.mapToJsonString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/v1/customer/addCustomer")
    String addCustomer() {
        Map<String, Object> result = new HashMap<>();
        String name = getParameter("name");
        String phone = getParameter("phone");
        String code = getParameter("code");
        String source = getParameter("source");
        String platform = getParameter("platform");
        if (commonServiceImpl.verifyCode(phone, code)) {
            Customer customer = customerServiceImpl.getCustomerByPhone(phone);
            if (customer != null) {
                if (StringUtil.isHave(customer.getSource().split("\\|"), source)) {
                    result.put("success", 1);
                    result.put("msg", "您已经申请过了，请等待我们与您联系");
                    return ObjectUtil.mapToJsonString(result);
                } else {
                    customer.setSource(customer.getSource() + "|" + source).setAddTime(String.valueOf(System.currentTimeMillis()).substring(0, 10));
                    customerServiceImpl.updateCustomer(customer);
                }
            } else {
                customerServiceImpl.addCustomer(new Customer(null, name, phone, source, "form", platform));
            }
        } else {
            result.put("success", 1);
            result.put("msg", "您验证码貌似输入错了");
            return ObjectUtil.mapToJsonString(result);
        }

        result.put("success", 1);
        result.put("msg", "我们已收到您的信息，请随时保持电话畅通，我们将尽快与您联系，感谢您的支持！");
        return ObjectUtil.mapToJsonString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/api/doCooperation")
    String doCooperation() {
        Map<String, Object> result = new HashMap<>();
        String companyName = getParameter("companyName");
        String email = getParameter("email");
        String context = getParameter("context");
        SendEmail.builder(envir.getProperty("mailHost"), envir.getProperty("senderUserName"), envir.getProperty("senderPassWrod"), envir.getProperty("nickName")).doSendHtmlEmail("官网合作信息", Common.doCooperationContent(companyName, email, context), new ArrayList<String>() {{
            add("abc@abc.abc");
        }}, null);
        result.put("success", 1);
        result.put("msg", "我们已收到您的信息，请随时保持电话畅通，我们将尽快与您联系，感谢您的支持！");
        return ObjectUtil.mapToJsonString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/api/insertYicheCity")
    String insertYicheCity() throws Exception {
        Resource resource = new ClassPathResource("/1.xlsx");
        LinkedHashMap<String, LinkedList<List>> stringLinkedListLinkedHashMap = PoiUtil.doReadExcel(resource.getFile(), ".xlsx");
        LinkedList<List> 省市 = stringLinkedListLinkedHashMap.get("省市");
        Map<Integer, String> map1 = new LinkedHashMap<>();
        Map<Integer, String> map2 = new LinkedHashMap<>();
        省市.forEach(list -> {
            map1.put(new BigDecimal((String) list.get(1)).intValue(), list.get(0).toString());
            if (!list.get(3).toString().equals("NULL")) {
                map2.put(new BigDecimal((String) list.get(3)).intValue(), list.get(2).toString() + "--" + list.get(1).toString());
            }
        });
        Set<Integer> keySet = map1.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        Collections.sort(keyList);
        Map<Integer, String> 省 = new LinkedHashMap<>();
        keyList.forEach(key -> {
            省.put(key, map1.get(key));
        });

        Set<Integer> keySet2 = map2.keySet();
        List<Integer> keyList2 = new ArrayList<>(keySet2);
        Collections.sort(keyList2);
        Map<Integer, String> 市 = new LinkedHashMap<>();
        keyList2.forEach(key -> {
            市.put(key, map2.get(key));
        });
        List<YicheCity> collect = 省.entrySet().stream().map(entry -> new YicheCity(entry.getKey(), null, 1, entry.getValue())).collect(Collectors.toList());
        collect.addAll(市.entrySet().stream().map(entry -> {
            String value = entry.getValue();
            String[] split = value.split("--");
            return new YicheCity(entry.getKey(), new BigDecimal(split[1]).intValue(), 2, split[0]);
        }).collect(Collectors.toList()));
        commonServiceImpl.insertYicheCity(collect);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value = "/api/insertYicheProduct")
    String insertYicheProduct() throws Exception {
        List<YicheProduct> yicheProductList = new ArrayList<>();
        Resource resource = new ClassPathResource("/2.xlsx");
        LinkedHashMap<String, LinkedList<List>> stringLinkedListLinkedHashMap = PoiUtil.doReadExcel(resource.getFile(), ".xlsx");
        stringLinkedListLinkedHashMap.get("品牌").forEach(list -> yicheProductList.add(new YicheProduct(new BigDecimal(list.get(1).toString()).intValue(), null, 1, list.get(0).toString())));
        stringLinkedListLinkedHashMap.get("子品牌").forEach(list -> yicheProductList.add(new YicheProduct(new BigDecimal(list.get(3).toString()).intValue(), new BigDecimal(list.get(1).toString()).intValue(), 2, list.get(2).toString())));
        stringLinkedListLinkedHashMap.get("车系").forEach(list -> yicheProductList.add(new YicheProduct(new BigDecimal(list.get(3).toString()).intValue(), new BigDecimal(list.get(1).toString()).intValue(), 3, list.get(2).toString())));
        stringLinkedListLinkedHashMap.get("车型").forEach(list -> yicheProductList.add(new YicheProduct(new BigDecimal(list.get(3).toString()).intValue(), new BigDecimal(list.get(1).toString()).intValue(), 4, list.get(2).toString())));
        commonServiceImpl.insertYicheProduct(yicheProductList);
        return "success";
    }


}
