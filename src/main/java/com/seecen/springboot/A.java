package com.seecen.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan
//@SpringBootConfiguration
@MapperScan("com.seecen.springboot.mapper")  //扫描mybatisMapper接口注解
public class A {
    public static void main(String[] args) {
        SpringApplication.run(A.class,args);
    }
}
