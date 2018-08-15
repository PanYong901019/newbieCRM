package com.newbie.service.Impl;


import com.newbie.model.CustomerRecord;
import com.newbie.service.BaseService;
import com.newbie.service.CustomerRecordService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "customerRecordService")
public class CustomerRecordServiceImpl extends BaseService implements CustomerRecordService {

    @Override
    public Map<String, Object> getCustomerRecordListByCustomerId(String customerId, String pageNo, String limit) {
        Map<String, Object> result = new HashMap<>();
        Object rowCount = baseDaoImpl.executeSelectSqlQuery("select count(*) as rowCount from cainiao_customer_record " + (customerId != null ? "where customer_id = " + customerId : "")).get(0).get("rowCount");
        List<CustomerRecord> customerList = customerRecordDaoImpl.getCustomerRecordListByCustomerId(Integer.valueOf(customerId), ((Integer.valueOf(pageNo) - 1) * Integer.valueOf(limit)), Integer.valueOf(limit));
        result.put("count", rowCount);
        result.put("data", customerList);
        return result;
    }

    @Override
    public Integer createCustomerRecord(CustomerRecord customerRecord) {

        return customerRecordDaoImpl.createCustomerRecord(customerRecord);
    }

}
