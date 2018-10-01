package com.newbie.controller;

import com.easyond.utils.ObjectUtil;
import com.easyond.utils.SendEmail;
import com.easyond.utils.StringUtil;
import com.newbie.model.Customer;
import com.newbie.utils.Common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        return ObjectUtil.objectToJsonString(getResult());
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
}
