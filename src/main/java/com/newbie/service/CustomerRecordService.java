package com.newbie.service;


import com.newbie.model.CustomerRecord;

import java.util.Map;

public interface CustomerRecordService {

    Map<String, Object> getCustomerRecordListByCustomerId(String customerId, String pageNo, String limit);

    Integer createCustomerRecord(CustomerRecord customerRecord);
}
