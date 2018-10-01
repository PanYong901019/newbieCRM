package com.newbie.dao;


import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BaseDao {

    @Insert("${sql}")
    void executeInsertSqlQuery(@Param("sql") String sql);

    @Delete("${sql}")
    void executeDeleteSqlQueryl(@Param("sql") String sql);

    @Update("${sql}")
    void executeUpdateSqlQuery(@Param("sql") String sql);

    @Select("${sql}")
    List<Map> executeSelectSqlQuery(@Param("sql") String sql);
}
