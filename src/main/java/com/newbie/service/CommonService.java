package com.newbie.service;

public interface CommonService {
    Integer sendSms(String phone);

    Boolean verifyCode(String phone, String code);
}
