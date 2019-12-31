package com.seecen.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //指定该类所有方法返回json，相当于在每个方法上加@ResponseBody
public class HelloController {

    @GetMapping("/hello")
    public String hello(String name){
        System.out.println("进入spring boot");
        System.out.println("获取到name"+name);
        return "hello:"+name;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
