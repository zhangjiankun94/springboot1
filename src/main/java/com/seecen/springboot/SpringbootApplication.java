package com.seecen.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动类
//@SpringBootApplication
//@MapperScan("com.seecen.springboot.mapper")  //扫描mybatisMapper接口注解
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
