package com.newbie.controller.api;

import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import com.newbie.model.User;
import com.newbie.utils.AppException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiIndexController extends BaseController {


    @RequestMapping(value = "login")
    String login() throws InterruptedException {
        String name = getParameter("name");
        String password = getParameter("password");
        if (StringUtil.invalid(name) || StringUtil.invalid(password)) {
            rspCode = FAIL;
            rspInfo = "The account ro password can not be empty";
        } else {
            try {
                User user = userServiceImpl.doLogin(name, password);
                session.setAttribute("loginUser", user);
                rspCode = OK;
                rspInfo = "success";
                rspResult.put("user", user);
            } catch (AppException e) {
                rspCode = e.getErrorCode();
                rspInfo = e.getMessage();
            }
        }
        Thread.sleep(500L);
        return getResultJsonString();
    }
}
