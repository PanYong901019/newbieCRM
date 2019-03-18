package com.newbie.dao;

import com.newbie.model.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerDao {

    @Select("<script>SELECT c.*, u.`name` AS counselorName FROM cainiao_customer c LEFT JOIN cainiao_admin_user u ON u.id = c.counselor WHERE 1=1<if test='counselorId != null '> AND c.counselor = #{counselorId}</if><if test='nameorphone != null '> AND ( c.NAME LIKE '%${nameorphone}%' OR c.phone LIKE '%${nameorphone}%' )</if><if test='source != null '> AND c.source LIKE '%${source}%'</if><if test='flag != null '> AND c.flag = #{flag}</if><if test='inputType != null '> AND c.input_type = #{inputType}</if><if test='platform != null '> AND c.platform = #{platform}</if><if test='status != null '> AND c.status = #{status}</if><if test='startAddTime != null and endAddTime != null'> AND c.add_time BETWEEN #{startAddTime} AND #{endAddTime}</if> ORDER BY c.add_time DESC <if test='sinceId != null and limit != null '> LIMIT #{sinceId},#{limit}</if></script>")
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
    List<Customer> getCustomerList(@Param(value = "counselorId") Integer counselorId, @Param(value = "sinceId") Integer sinceId, @Param(value = "limit") Integer limit, @Param(value = "nameorphone") String nameorphone, @Param(value = "flag") String flag, @Param(value = "source") String source, @Param(value = "inputType") String inputType, @Param(value = "platform") String platform, @Param(value = "status") String status, @Param(value = "startAddTime") String startAddTime, @Param(value = "endAddTime") String endAddTime);

    @Select(" SELECT c.*, u.`name` AS counselorName FROM cainiao_customer c LEFT JOIN cainiao_admin_user u ON u.id = c.counselor WHERE c.id = #{id}")
    @ResultMap("customerMap")
    Customer getCustomerById(@Param("id") int id);

    @Select(" SELECT c.*, u.`name` AS counselorName FROM cainiao_customer c LEFT JOIN cainiao_admin_user u ON u.id = c.counselor WHERE c.phone = #{phone}")
    @ResultMap("customerMap")
    Customer getCustomerByPhone(@Param("phone") String phone);

    @Update("update cainiao_customer set name=#{name},phone=#{phone},counselor=#{counselorId},input_type=#{inputType},platform=#{platform},flag=#{flag},status=#{status},source=#{source},add_time=#{addTime} where id=#{id}")
    Integer update(Customer customer);

    @Update("update cainiao_customer set counselor=#{counselorId} where id in (${customerIds})")
    Integer allotCustomer(@Param("counselorId") String counselorId, @Param("customerIds") String customerIds);

    @Options(useGeneratedKeys = true)


    @Insert("INSERT INTO cainiao_customer ( `name`, phone, source, input_type, platform, `is_ dispose`, flag, add_time, `status` ) VALUES (#{name},#{phone},#{source},#{inputType},#{platform},#{isDispose},#{flag},#{addTime},#{status})")
    Integer addCustomer(Customer customer);

    @Select("SELECT c.*, u.`name` AS counselorName FROM cainiao_customer c LEFT JOIN cainiao_admin_user u ON u.id = c.counselor WHERE c.add_time BETWEEN #{startAddTime} AND #{endAddTime} ORDER BY c.add_time DESC")
    @ResultMap("customerMap")
    List<Customer> getCustomerListByDate(@Param(value = "startAddTime") String startAddTime, @Param(value = "endAddTime") String endAddTime);
}
