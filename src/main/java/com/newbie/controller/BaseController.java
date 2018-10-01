package com.newbie.controller;

import com.easyond.utils.ObjectUtil;
import com.newbie.model.User;
import com.newbie.service.CommonService;
import com.newbie.service.CustomerRecordService;
import com.newbie.service.CustomerService;
import com.newbie.service.UserService;
import com.newbie.utils.AppCache;
import com.newbie.utils.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class BaseController {
    protected static Integer FAIL = 0;
    protected static Integer OK = 1;
    protected Integer rspCode = FAIL;
    protected String rspInfo = "fail";
    protected LinkedHashMap<String, Object> rspResult = new LinkedHashMap<>();
    @Autowired
    protected AppCache appCache;
    @Autowired
    protected Environment envir;
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected UserService userServiceImpl;
    @Autowired
    protected CustomerService customerServiceImpl;
    @Autowired
    protected CustomerRecordService customerRecordServiceImpl;
    @Autowired
    protected CommonService commonServiceImpl;

    protected String getParameter(String parameterKey) {
        return WebUtils.findParameterValue(request, parameterKey);
    }

    protected User getLoginUser() {
        return HttpContextUtil.getLoginUser(request);
    }

    protected Integer getLoginUserId() {
        return HttpContextUtil.getLoginUserId(request);
    }

    protected Integer getLoginUserAuth() {
        return HttpContextUtil.getLoginUserAuth(request);
    }

    protected Boolean isLogin() {
        return getLoginUser() != null;
    }

    protected Map<String, Object> getResult() {
        Map<String, Object> result = new LinkedHashMap<String, Object>() {{
            put("rspCode", rspCode);
            put("rspInfo", rspInfo);
            put("rspResult", rspResult);
        }};
        return result;
    }

    protected String getResultJsonString() {
        Map<String, Object> result = new LinkedHashMap<String, Object>() {{
            put("rspCode", rspCode);
            put("rspInfo", rspInfo);
            put("rspResult", rspResult);
        }};
        return ObjectUtil.mapToJsonString(result);
    }
}
