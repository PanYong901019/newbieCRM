package com.newbie.service.Impl;


import com.easyond.utils.StringUtil;
import com.newbie.model.Customer;
import com.newbie.service.BaseService;
import com.newbie.service.YicheService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "yicheService")
public class YicheServiceImpl extends BaseService implements YicheService {

    private static int getCount(String source, String sub) {
        int count = 0;
        int length = source.length() - sub.length();
        for (int i = 0; i < length; i++) {
            String sourceBak = source.substring(i, i + sub.length());
            int index = sourceBak.indexOf(sub);
            if (index != -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Map> getAllProvince() {
        return baseDaoImpl.executeSelectSqlQuery("SELECT c.id, c.`name` FROM yiche_city c WHERE c.type = 1");
    }

    @Override
    public List<Map> getCityListByProvinceId(String provinceId) {
        return baseDaoImpl.executeSelectSqlQuery("SELECT c.id, c.`name` FROM yiche_city c WHERE c.type = 2 and parent_id = " + provinceId);
    }

    @Override
    public List<Map> getAllBrand() {
        return baseDaoImpl.executeSelectSqlQuery("SELECT p.id, p.`name` FROM yiche_product p WHERE p.type = 1");
    }

    @Override
    public List<Map> getKidBrandListByBrandId(String brandId) {
        return baseDaoImpl.executeSelectSqlQuery("SELECT p.id, p.`name` FROM yiche_product p WHERE p.type = 2 and parent_id = " + brandId);
    }

    @Override
    public List<Map<String, String>> postDataToYicheServer(List<String> yes) {
        List<Customer> customerListByCustomerIds = yicheDaoImpl.getCustomerListByCustomerIds(yes);
        List<Map<String, String>> postData = new ArrayList<>();
        customerListByCustomerIds.forEach(customer -> {
            String source = customer.getSource();
            String[] sourceSplit = source.split("\\|");
            for (String s : sourceSplit) {
                try {
                    String name = customer.getName();
                    String userName = name.substring(0, name.length() - 3);
                    String sex = "男".equals(name.substring(name.length() - 2, name.length() - 1)) ? "1" : "2";
                    if (getCount(s, "_") == 5) {
                        String[] split = s.split("_");
                        List<Map> cityIdMap = baseDaoImpl.executeSelectSqlQuery("SELECT c.id as id FROM yiche_city c WHERE c.type = 2 and c.name LIKE '%" + split[1] + "%'");
                        String cityId = cityIdMap.size() > 0 ? cityIdMap.get(0).get("id").toString() : "";
                        List<Map> cmkandcmd = baseDaoImpl.executeSelectSqlQuery("SELECT s.masterId AS cmbId, cmb.`name` AS cmbName, s.brandId AS cmkId, cmk.`name` AS cmkName, s.serialId AS cmdId, cmd.`name` AS cmdName FROM ttdy_crm.yc_series s LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 1 ) cmb ON s.masterId = cmb.id LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 2 ) cmk ON s.brandId = cmk.id LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 3 ) cmd ON s.serialId = cmd.id WHERE s.saleStatus = 1 AND s.masterId = " + split[2] + " AND s.serialName = '" + split[5] + "'");
                        String cmbId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmbId").toString() : "";
                        String cmbName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmbName").toString() : "";
                        String cmkId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmkId").toString() : "";
                        String cmkName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmkName").toString() : "";
                        String cmdId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmdId").toString() : "";
                        String cmdName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmdName").toString() : "";
                        postData.add(getPostDataItemMap(customer.getId().toString(), userName, sex, customer.getPhone(), cityId, split[1], StringUtil.invalid(cmbId) ? split[2] : cmbId, StringUtil.invalid(cmbName) ? split[3] : cmbName, cmkId, StringUtil.invalid(cmkName) ? split[4] : cmkName, cmdId, StringUtil.invalid(cmdName) ? split[5] : cmdName, "", "", customer.getAddTimeString()));

                    } else if (getCount(s, "_") == 3) {
                        String[] split = s.split("_");
                        List<Map> cityIdMap = baseDaoImpl.executeSelectSqlQuery("SELECT c.id as id FROM yiche_city c WHERE c.type = 2 and c.name LIKE '%" + split[1] + "%'");
                        String cityId = cityIdMap.size() > 0 ? cityIdMap.get(0).get("id").toString() : "";
                        List<Map> cmkandcmd = baseDaoImpl.executeSelectSqlQuery("SELECT s.masterId AS cmbId, cmb.`name` AS cmbName, s.brandId AS cmkId, cmk.`name` AS cmkName, s.serialId AS cmdId, cmd.`name` AS cmdName FROM ttdy_crm.yc_series s LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 1 ) cmb ON s.masterId = cmb.id LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 2 ) cmk ON s.brandId = cmk.id LEFT JOIN ( SELECT * FROM yiche_product pro WHERE type = 3 ) cmd ON s.serialId = cmd.id WHERE s.saleStatus = 1 AND s.serialName = '" + split[3] + "'");
                        String cmbId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmbId").toString() : "";
                        String cmbName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmbName").toString() : "";
                        String cmkId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmkId").toString() : "";
                        String cmkName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmkName").toString() : "";
                        String cmdId = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmdId").toString() : "";
                        String cmdName = cmkandcmd.size() > 0 ? cmkandcmd.get(0).get("cmdName").toString() : "";
                        postData.add(getPostDataItemMap(customer.getId().toString(), userName, sex, customer.getPhone(), cityId, split[1], cmbId, cmbName, cmkId, StringUtil.invalid(cmkName) ? split[2] : cmkName, cmdId, StringUtil.invalid(cmdName) ? split[3] : cmdName, "", "", customer.getAddTimeString()));
                    } else {
                        s = s.replaceAll("_", "-");
                        String[] split = s.split("-");
                        List<Map> cityIdMap = baseDaoImpl.executeSelectSqlQuery("SELECT c.id as id FROM yiche_city c WHERE c.type = 2 and c.name LIKE '%" + split[1] + "%'");
                        List<Map> pinpai = baseDaoImpl.executeSelectSqlQuery("SELECT p.id FROM yiche_product p WHERE p.type = 1 AND p.`name` LIKE '%" + split[2] + "%'");
                        String cityId = cityIdMap.size() > 0 ? cityIdMap.get(0).get("id").toString() : "";
                        String cmbId = pinpai.size() > 0 ? pinpai.get(0).get("id").toString() : "";
                        postData.add(getPostDataItemMap(customer.getId().toString(), userName, sex, customer.getPhone(), cityId, split[1], cmbId, split[2], "", "", "", split[3], "", "", customer.getAddTimeString()));
                    }
                } catch (Exception e) {
                    System.out.println("处理 " + s + " 时出错");
                }
            }
        });
        return postData;
    }

    private Map<String, String> getPostDataItemMap(String id, String userName, String sex, String phone,
                                                   String cityId, String cityName, String cmbId, String cmbName,
                                                   String cmkId, String cmkName, String cmdId, String cmdName, String carId, String carName, String createTime) {
        return new HashMap<String, String>() {{
            put("id", id);
            put("userName", userName);
            put("sex", sex);
            put("phone", phone);
            put("yicheStandard", "1");
            put("channelName", "ceshitaitongdayou");
            put("cityId", cityId);
            put("cityName", cityName);
            put("cmbId", cmbId);
            put("cmbName", cmbName);
            put("cmkId", cmkId);
            put("cmkName", cmkName);
            put("cmdId", cmdId);
            put("cmdName", cmdName);
            put("carId", carId);
            put("carName", carName);
            put("createTime", createTime);
        }};
    }
}