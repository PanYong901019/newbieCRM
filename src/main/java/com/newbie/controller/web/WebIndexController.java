package com.newbie.controller.web;

import com.newbie.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebIndexController extends BaseController {

    @RequestMapping(value = {"/admin", "/admin/index"})
    ModelAndView index() {
        if (isLogin()) {
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("loginUser", getLoginUser());
            return modelAndView;
        } else {
            return new ModelAndView("loginPage");
        }
    }

    @RequestMapping(value = {"/admin/login"})
    ModelAndView loginPage() {
        return new ModelAndView("loginPage");
    }

    @RequestMapping("/admin/logout")
    ModelAndView logout() {
        session.invalidate();
        return new ModelAndView("redirect:/admin");
    }

}
