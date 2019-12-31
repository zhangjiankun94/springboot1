package com.seecen.springboot;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seecen.springboot.mapper.AdminMapper;
import com.seecen.springboot.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Resource
    private AdminMapper mapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        List<Admin> admins = mapper.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void insert(){
        Admin admin = new Admin();
        admin.setAccount("zhang1");
        admin.setName("狗蛋1");
        admin.setPassword("123456");
        mapper.insert(admin);
    }

    @Test
    public void pageTest(){
        //设置pageSize 和 pageNum
        PageHelper.startPage(1,2);
        //执行查询
        List<Admin> admins = mapper.selectAll();
        //封装PageInfo对象
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        System.out.println("总页数："+adminPageInfo.getPages());//总页数
        System.out.println("总记录数："+adminPageInfo.getTotal());//总记录数
        adminPageInfo.getList().forEach(admin -> System.out.println(admin));
    }

}
