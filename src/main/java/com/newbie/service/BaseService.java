package com.newbie.service;

import com.newbie.dao.*;
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
    @Autowired
    protected YicheDao yicheDaoImpl;
}
