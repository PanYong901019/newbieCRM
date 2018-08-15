package com.newbie.service.Impl;


import com.easyond.utils.StringUtil;
import com.newbie.model.Customer;
import com.newbie.service.BaseService;
import com.newbie.service.CustomerService;
import com.newbie.utils.AppException;
import com.newbie.utils.Common;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "customerService")
public class CustomerServiceImpl extends BaseService implements CustomerService {

    @Override
    public Map<String, Object> getCustomerList(Integer counselorId, Map<String, String> search, String pageNo, String limit) {
        Map<String, Object> result = new HashMap<>();
        Object rowCount = baseDaoImpl.executeSelectSqlQuery("select count(*) as rowCount from cainiao_customer " + (counselorId != null ? "where counselor = " + counselorId : "")).get(0).get("rowCount");
        String[] addTime = StringUtil.invalid(search.get("addTime")) ? new String[2] : search.get("addTime").split("\\|");
        List<Customer> customerList = customerDaoImpl.getCustomerList(counselorId, ((Integer.valueOf(pageNo) - 1) * Integer.valueOf(limit)), Integer.valueOf(limit), search.get("nameorphone"), search.get("flag"), search.get("source"), search.get("inputType"), search.get("platform"), search.get("status"), StringUtil.invalid(addTime[0]) ? null : Common.dateToStamp(addTime[0]).substring(0, 10), StringUtil.invalid(addTime[1]) ? null : Common.dateToStamp(addTime[1]).substring(0, 10));
        result.put("count", rowCount);
        result.put("data", customerList);
        return result;
    }

    @Override
    public Customer getCustomerById(String customerId) {
        return customerDaoImpl.getCustomerById(Integer.valueOf(customerId));
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        return customerDaoImpl.getCustomerByPhone(phone);
    }

    @Override
    public Integer updateCustomer(String customerId, String counselorId, String name, String phone, String inputType, String platform, String flag, String status, List<String> sourceList) {
        Customer customer = customerDaoImpl.getCustomerById(Integer.valueOf(customerId));
        if (customer != null) {
            if (!StringUtil.invalid(name)) {
                customer.setName(name);
            }
            if (!StringUtil.invalid(phone)) {
                customer.setPhone(phone);
            }
            if (!StringUtil.invalid(inputType)) {
                customer.setInputType(inputType);
            }
            if (!StringUtil.invalid(platform)) {
                customer.setPlatform(platform);
            }
            if (!StringUtil.invalid(status)) {
                customer.setStatus(status);
            }
            if (!StringUtil.invalid(flag)) {
                customer.setFlag(flag);
            }
            if (sourceList != null && sourceList.size() > 0) {
                customer.setSource(String.join("|", sourceList));
            }
            return customerDaoImpl.update(customer);
        } else {
            throw new AppException(404, "customer not found");
        }
    }

    @Override
    public Integer updateCustomer(Customer customer) {
        return customerDaoImpl.update(customer);
    }

    @Override
    public Integer allotCustomer(String counselorId, List<String> customerIdList) {
        return customerDaoImpl.allotCustomer(counselorId, String.join(",", customerIdList));
    }

    @Override
    public Integer addCustomer(Customer customer) {
        return customerDaoImpl.addCustomer(customer);
    }

}
