package com.newbie.service;

import java.util.List;
import java.util.Map;

public interface YicheService {

    List<Map> getAllProvince();

    List<Map> getCityListByProvinceId(String provinceId);

    List<Map> getAllBrand();

    List<Map> getKidBrandListByBrandId(String brandId);

    List<Map<String, String>> postDataToYicheServer(List<String> yes);
}
