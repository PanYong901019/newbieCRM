package com.newbie.dao;

import com.newbie.model.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface YicheDao {

    @Select("<script>SELECT c.*, u.`name` AS counselorName FROM cainiao_customer c LEFT JOIN cainiao_admin_user u ON u.id = c.counselor WHERE c.id in <foreach item='item' index='index' collection='customerIds' open='(' separator=',' close=')'>#{item}</foreach></script>")
    @Results(id = "customerMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "source", column = "source"),
            @Result(property = "isDispose", column = "is_ dispose"),
            @Result(property = "inputType", column = "input_type"),
            @Result(property = "counselorId", column = "counselor"),
            @Result(property = "counselorName", column = "counselorName"),
            @Result(property = "flag", column = "flag"),
            @Result(property = "platform", column = "platform"),
            @Result(property = "status", column = "status"),
            @Result(property = "addTime", column = "add_time"),
            @Result(property = "lastRecordTime", column = "record_time")
    })
    List<Customer> getCustomerListByCustomerIds(@Param(value = "customerIds") List<String> customerIds);
}
