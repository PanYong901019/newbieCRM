package com.newbie.model;

import com.newbie.utils.Common;
import com.newbie.utils.Constant;

import java.io.Serializable;

public class CustomerRecord implements Serializable {

    private Integer id;
    private Integer counselorId;
    private String counselorName;
    private Integer customerId;
    private String customerName;
    private String recordTime;
    private String recordTimeString;
    private String content;
    private String status;
    private String statusName;

    public CustomerRecord() {
    }

    public CustomerRecord(Integer id, Integer counselorId, Integer customerId, String content) {
        this.id = id;
        this.counselorId = counselorId;
        this.customerId = customerId;
        this.recordTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        this.content = content;
        this.status = "1";
    }

    public Integer getId() {
        return id;
    }

    public CustomerRecord setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCounselorId() {
        return counselorId;
    }

    public CustomerRecord setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
        return this;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public CustomerRecord setCounselorName(String counselorName) {
        this.counselorName = counselorName;
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public CustomerRecord setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerRecord setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public CustomerRecord setRecordTime(String recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public String getRecordTimeString() {
        return Common.stampToDate(recordTime);
    }

    public String getContent() {
        return content;
    }

    public CustomerRecord setContent(String content) {
        this.content = content;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CustomerRecord setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusName() {
        return Constant.statusConstant.get(status);
    }
}
