package com.newbie.dao;


import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BaseDao {

    @Insert("${sql}")
    Object executeInsertSqlQuery(@Param("sql") String sql);

    @Delete("${sql}")
    Object executeDeleteSqlQueryl(@Param("sql") String sql);

    @Update("${sql}")
    Object executeUpdateSqlQuery(@Param("sql") String sql);

    @Select("${sql}")
    List<Map> executeSelectSqlQuery(@Param("sql") String sql);
}
