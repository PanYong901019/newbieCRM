package com.newbie.controller.api.yiche;

import com.easyond.utils.StringUtil;
import com.newbie.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pan on 2018/11/15 上午3:39
 */
@RestController
@RequestMapping(value = "/api/yiche", produces = "application/json;charset=UTF-8")
public class ApiYicheCommonController extends BaseController {


    @RequestMapping(value = "test")
    String test() {
        List<String> yes = new ArrayList<>();
        yes.add("1");
        yes.add("2");
        yes.add("3");
        yes.add("4");
        yicheServiceImpl.postDataToYicheServer(yes);
        return getResultJsonString();
    }

    @RequestMapping(value = "getAllProvince")
    String getAllProvince() {
        rspCode = OK;
        rspInfo = "success";
        rspResult.put("provinceList", yicheServiceImpl.getAllProvince());
        return getResultJsonString();
    }

    @RequestMapping(value = "getCityListByProvinceId")
    String getCityListByProvinceId() {
        String provinceId = getParameter("provinceId");
        if (StringUtil.invalid(provinceId)) {
            rspCode = FAIL;
            rspInfo = "参数错误";
        } else {
            rspCode = OK;
            rspInfo = "success";
            rspResult.put("cityList", yicheServiceImpl.getCityListByProvinceId(provinceId));
        }
        return getResultJsonString();
    }

    @RequestMapping(value = "getAllBrand")
    String getAllBrand() {
        rspCode = OK;
        rspInfo = "success";
        rspResult.put("brandList", yicheServiceImpl.getAllBrand());
        return getResultJsonString();
    }

    @RequestMapping(value = "getKidBrandListByBrandId")
    String getKidBrandListByBrandId() {
        String brandId = getParameter("brandId");
        if (StringUtil.invalid(brandId)) {
            rspCode = FAIL;
            rspInfo = "参数错误";
        } else {
            rspCode = OK;
            rspInfo = "success";
            rspResult.put("kidBrandList", yicheServiceImpl.getKidBrandListByBrandId(brandId));
        }
        return getResultJsonString();
    }
}
