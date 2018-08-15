package com.newbie.dao;

import com.newbie.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select(" SELECT * FROM cainiao_admin_user ORDER BY `status` DESC")
    @Results(id = "userMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "type", column = "type"),
            @Result(property = "status", column = "status"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    List<User> getAllUser();

    @Select(" SELECT * FROM cainiao_admin_user WHERE id = #{id}")
    @ResultMap("userMap")
    User getUserById(@Param("id") int id);

    @Select(" SELECT * FROM cainiao_admin_user WHERE name = #{name}")
    @ResultMap("userMap")
    User getUserByName(@Param("name") String name);

    @Select("SELECT * FROM cainiao_admin_user WHERE type = 'common' ORDER BY `status` DESC")
    @ResultMap("userMap")
    List<User> getUserByType(@Param("type") String type);

    @Update("update cainiao_admin_user set name=#{name},password=#{password},avatar=#{avatar},type=#{type},status=#{status},last_login_time=#{lastLoginTime} where id=#{id}")
    Integer update(User user);


    @Options(useGeneratedKeys = true)
    @Insert("insert into cainiao_admin_user(name,password,avatar,type,status) values(#{name},#{password},#{avatar},#{type},1)")
    Integer createUser(User user);
}
