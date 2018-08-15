package com.newbie.dao;

import com.newbie.model.CustomerRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerRecordDao {

    @Select("<script>SELECT cr.*, u.`name` AS counselorName, c.`name` AS customerName FROM cainiao_customer_record cr LEFT JOIN cainiao_admin_user u ON cr.user_id = u.id LEFT JOIN cainiao_customer c ON cr.customer_id = c.id WHERE cr.customer_id = #{customerId} ORDER BY cr.record_time DESC  <if test='sinceId != null and limit != null '> LIMIT #{sinceId},#{limit} </if></script>")
    @Results(id = "customerMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "recordTime", column = "record_time"),
            @Result(property = "counselorId", column = "user_id"),
            @Result(property = "counselorName", column = "counselorName"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "customerName", column = "customerName"),
            @Result(property = "content", column = "content"),
            @Result(property = "status", column = "status"),
    })
    List<CustomerRecord> getCustomerRecordListByCustomerId(@Param(value = "customerId") Integer customerId, @Param(value = "sinceId") Integer sinceId, @Param(value = "limit") Integer limit);


    @Options(useGeneratedKeys = true)
    @Insert("insert into cainiao_customer_record(record_time,user_id,customer_id,content,status) values(#{recordTime},#{counselorId},#{customerId},#{content},#{status})")
    Integer createCustomerRecord(CustomerRecord customerRecord);
}
