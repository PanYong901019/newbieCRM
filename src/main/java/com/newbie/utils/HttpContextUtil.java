package com.newbie.utils;

import com.newbie.model.User;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtil {

    public static User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loginUser");
    }

    public static Integer getLoginUserId(HttpServletRequest request) {
        return getLoginUser(request) == null ? null : getLoginUser(request).getId();
    }

    public static Integer getLoginUserAuth(HttpServletRequest request) {
        return getLoginUser(request).getType().equals("admin") ? 0 : 1;
    }
}
