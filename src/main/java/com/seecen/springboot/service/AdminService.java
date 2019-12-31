package com.seecen.springboot.service;

import com.seecen.springboot.pojo.Admin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangJiankun
 * @date 2019/12/10 14:22
 * 描述：
 */
public interface AdminService {
    
    Admin selectById(Long id);
    
    List<Admin> selectAll();

    Admin selectByAdmin(String account);

    int insert(Admin admin);

    int update(Admin admin);

    default Admin update2(Admin admin){
        return null;
    };

    int deleteById(Long id);

    //jdk1.8以后，允许在接口中编写static方法和default方法，这两类方法可以有方法体
    default List<Admin> selectByNameOrAccount(String name, String account){
        return null;
    };
}
