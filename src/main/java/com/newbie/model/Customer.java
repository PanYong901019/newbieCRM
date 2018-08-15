package com.newbie.model;

import com.alibaba.fastjson.TypeReference;
import com.easyond.utils.ObjectUtil;
import com.newbie.utils.Common;
import com.newbie.utils.Constant;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Customer implements Serializable {

    private Integer id;
    private Integer counselorId;
    private String counselorName;
    private String name;
    private String phone;
    private String source;
    private String sourceName;
    private String isDispose;
    private String inputType;
    private String inputTypeName;
    private String flag;
    private String platform;
    private String platformName;
    private String addTime;
    private String addTimeString;
    private String lastRecordTime;
    private String lastRecordTimeString;
    private String status;
    private String statusName;

    public Customer() {
    }

    public Customer(Integer counselorId, String name, String phone, String source, String inputType, String platform) {
        this.counselorId = counselorId;
        this.name = name;
        this.phone = phone;
        this.source = source;
        this.inputType = inputType;
        this.platform = platform;
        this.isDispose = "1";
        this.flag = "0";
        this.addTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        this.status = "1";
    }

    @Override
    public String toString() {
        Map<String, Object> result = ObjectUtil.jsonStringToObject(ObjectUtil.objectToJsonString(this), new TypeReference<Map<String, Object>>() {
        });
        for (String s : source.split("\\|")) {
            result.put("source[" + s + "]", "on");
        }
        return ObjectUtil.mapToJsonString(result);
    }

    public Integer getId() {
        return id;
    }

    public Customer setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCounselorId() {
        return counselorId;
    }

    public Customer setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
        return this;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public Customer setCounselorName(String counselorName) {
        this.counselorName = counselorName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Customer setSource(String source) {
        this.source = source;
        return this;
    }

    public String getSourceName() {
        return String.join("|", Arrays.stream(source.split("\\|")).map(s -> Constant.customerSourceConstant.get(s)).collect(Collectors.toList()));
    }

    public String getIsDispose() {
        return isDispose;
    }

    public Customer setIsDispose(String isDispose) {
        this.isDispose = isDispose;
        return this;
    }

    public String getInputType() {
        return inputType;
    }

    public Customer setInputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    public String getInputTypeName() {
        return Constant.customerInputTypeConstant.get(inputType);
    }

    public String getFlag() {
        return flag;
    }

    public Customer setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public Customer setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getPlatformName() {
        return Constant.customerPlatformConstant.get(platform);
    }

    public String getAddTime() {
        return addTime;
    }

    public Customer setAddTime(String addTime) {
        this.addTime = addTime;
        return this;
    }

    public String getAddTimeString() {
        return Common.stampToDate(addTime);
    }

    public String getLastRecordTime() {
        return lastRecordTime;
    }

    public Customer setLastRecordTime(String lastRecordTime) {
        this.lastRecordTime = lastRecordTime;
        return this;
    }

    public String getLastRecordTimeString() {
        return Common.stampToDate(lastRecordTime);
    }

    public String getStatus() {
        return status;
    }

    public Customer setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusName() {
        return Constant.statusConstant.get(status);
    }
}
