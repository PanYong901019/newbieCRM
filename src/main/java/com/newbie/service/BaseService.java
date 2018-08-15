package com.newbie.service;

import com.newbie.dao.BaseDao;
import com.newbie.dao.CustomerDao;
import com.newbie.dao.CustomerRecordDao;
import com.newbie.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class BaseService {
    @Autowired
    protected Map<String, Object> appCache;
    @Autowired
    protected BaseDao baseDaoImpl;
    @Autowired
    protected UserDao userDaoImpl;
    @Autowired
    protected CustomerDao customerDaoImpl;
    @Autowired
    protected CustomerRecordDao customerRecordDaoImpl;
}
