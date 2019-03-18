package com.newbie.service;

import com.newbie.model.YicheCity;
import com.newbie.model.YicheProduct;

import java.util.List;

public interface CommonService {
    Integer sendSms(String phone);

    Boolean verifyCode(String phone, String code);

    void insertYicheCity(List<YicheCity> yicheCityList);

    void insertYicheProduct(List<YicheProduct> yicheProductList);
}
