package com.newbie.service;


import com.newbie.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Map<String, Object> getCustomerList(Integer counselorId, Map<String, String> search, String pageNo, String limit);

    List<Customer> getCustomerListByDay(String date);

    Customer getCustomerById(String customerId);

    Customer getCustomerByPhone(String phone);

    Integer updateCustomer(String customerId, String counselorId, String name, String phone, String inputType, String platform, String flag, String status, List<String> sourceList);

    Integer updateCustomer(Customer customer);

    Integer allotCustomer(String counselorId, List<String> customerIdList);

    Integer addCustomer(Customer customer);

}
