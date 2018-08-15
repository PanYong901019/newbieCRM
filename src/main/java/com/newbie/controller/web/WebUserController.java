package com.newbie.controller.web;

import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebUserController extends BaseController {

    @RequestMapping(value = {"/admin/userListPage"})
    ModelAndView userListPage() {
        ModelAndView modelAndView = new ModelAndView("user/userListPage");
        modelAndView.addObject("userList", userServiceImpl.getAllUser());
        return modelAndView;
    }

    @RequestMapping(value = {"/admin/editUser"})
    ModelAndView editUserPage() {
        ModelAndView modelAndView = new ModelAndView("user/editUserPopupPage");
        Integer editType = StringUtil.invalid(getParameter("editType")) ? 0 : Integer.valueOf(getParameter("editType"));
        modelAndView.addObject("editType", editType);
        if (editType == 1) {
            String userId = getParameter("userId");
            modelAndView.addObject("user", userServiceImpl.getUserById(userId));
        }
        return modelAndView;
    }

}
