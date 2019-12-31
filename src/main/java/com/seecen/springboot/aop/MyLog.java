package com.seecen.springboot.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhangJiankun
 * @date 2019/12/13 11:37
 * 描述：自定义注解
 */
@Target({ElementType.METHOD})  //指定可以将注解加在什么地方  METHOD表示在方法上加注解（或类上，属性,参数，注解上。。。。）
@Retention(RetentionPolicy.RUNTIME)  //指定在运行时
public @interface MyLog {
    /*
    * 日志类型： 0 登录 1 新增 2 修改 3 删除 4 注销 5 推出登录
    * */
    String logType();  //定义方法，对应注解中的属性
    /*
    * 操作描述：如删除用户/修改用户
    * */
    String connect();  //操作描述
}
