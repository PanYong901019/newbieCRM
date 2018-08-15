package com.newbie.controller.web;

import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebCustomerController extends BaseController {

    @RequestMapping(value = {"/admin/customerListPage"})
    ModelAndView customerListPage() {
        return new ModelAndView("customer/customerListPage");
    }

    @RequestMapping(value = {"/admin/editCustomer"})
    ModelAndView editUserPage() {
        ModelAndView modelAndView = new ModelAndView("customer/editCustomerPopupPage");
        Integer editType = StringUtil.invalid(getParameter("editType")) ? 0 : Integer.valueOf(getParameter("editType"));
        modelAndView.addObject("editType", editType);
        if (editType == 1) {
            String customerId = getParameter("customerId");
            modelAndView.addObject("customer", customerServiceImpl.getCustomerById(customerId));
        }
        return modelAndView;
    }


    @RequestMapping(value = {"/admin/recordCustomer"})
    ModelAndView recordCustomerPopup() {
        ModelAndView modelAndView = new ModelAndView("customer/recordCustomerPopupPage");
        String customerId = getParameter("customerId");
        modelAndView.addObject("customer", customerServiceImpl.getCustomerById(customerId));
        return modelAndView;
    }

}
