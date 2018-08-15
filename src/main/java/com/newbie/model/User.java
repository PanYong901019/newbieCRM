package com.newbie.model;

import com.alibaba.fastjson.TypeReference;
import com.easyond.utils.ObjectUtil;
import com.easyond.utils.StringUtil;
import com.newbie.utils.Common;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String avatar;
    private String type;
    private String status;
    private String lastLoginTime;
    private String lastLoginTimeString;

    public User() {
    }

    public User(Integer id, String name, String password, String type, String status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        Map<String, Object> result = ObjectUtil.jsonStringToObject(ObjectUtil.objectToJsonString(this), new TypeReference<Map<String, Object>>() {
        });
        return ObjectUtil.mapToJsonString(result);
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAvatar() {
        return StringUtil.invalid(avatar) ? " " : avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getStatus() {
        return Integer.valueOf(status);
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public User setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getLastLoginTimeString() {
        return Common.stampToDate(lastLoginTime);
    }
}
