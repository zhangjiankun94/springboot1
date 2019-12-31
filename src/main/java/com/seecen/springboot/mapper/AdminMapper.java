package com.seecen.springboot.mapper;

import com.seecen.springboot.pojo.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper   //Mybatis mapper注解
public interface AdminMapper {

    @Select("select * from t_admin where id = #{id}")
    Admin selectById(Long id);

    @Select("select * from t_admin order by id")
    List<Admin> selectAll();

    int insert(Admin admin);

    Admin selectByAdmin(String account);

//    int update(Admin admin);

    @Delete("delete from T_ADMIN where ID=#{id}")
    int deleteById(Long id);

    @Update("update T_ADMIN set ACCOUNT=#{account},PASSWORD=#{password},NAME=#{name},SEX=#{sex} where ID=#{id}")
    int update(Admin admin);

    @Select("select * from t_admin where name=#{name} or account=#{account}")
    List<Admin> selectByNameOrAccount(@Param("name") String name,@Param("account") String account);
}
