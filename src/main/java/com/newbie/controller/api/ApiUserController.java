package com.newbie.controller.api;

import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import com.newbie.model.User;
import com.newbie.utils.AppException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class ApiUserController extends BaseController {

    @RequestMapping(value = "changePassword")
    String changePassword() {
        String userId = getLoginUserId().toString();
        String oldPassword = getParameter("oldPassword");
        String newPassword = getParameter("newPassword");
        if (StringUtil.invalid(userId) || StringUtil.invalid(oldPassword) || StringUtil.invalid(newPassword)) {
            rspCode = FAIL;
            rspInfo = "The parameter can not be empty";
        } else {
            try {
                userServiceImpl.changePassword(userId, oldPassword, newPassword);
                rspCode = OK;
                rspInfo = "success";
            } catch (AppException e) {
                rspCode = e.getErrorCode();
                rspInfo = e.getMessage();
            }
        }
        return getResultJsonString();
    }


    @RequestMapping(value = "createUser")
    String createUser() {
        String name = getParameter("name");
        String password = getParameter("password");
        String type = getParameter("type");
        String status = StringUtil.invalid(getParameter("status")) ? "0" : "1";
        User user = new User(null, name, password, type, status);
        userServiceImpl.createUser(user);
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }

    @RequestMapping(value = "updateUser")
    String updateUser() {
        String userId = getParameter("id");
        String name = getParameter("name");
        String password = getParameter("password");
        String type = getParameter("type");
        String status = StringUtil.invalid(getParameter("status")) ? "0" : "1";
        userServiceImpl.updateUser(userId, name, password, type, status);
        rspCode = OK;
        rspInfo = "success";
        return getResultJsonString();
    }
}
