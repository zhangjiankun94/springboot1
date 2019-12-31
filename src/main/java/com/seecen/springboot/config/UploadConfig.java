package com.seecen.springboot.config;

import com.seecen.springboot.aop.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZhangJiankun
 * @date 2019/12/12 14:16
 * 描述：
 */
@Configuration //配置类注解
public class UploadConfig implements WebMvcConfigurer {
    @Value("${uploadpath}")
    private String uploadpath;
    @Value("${vpath}")
    private String vpath;

    /*
    *  ResourceHandlers相当于spring MVC中配置resources 配置项
    * */
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
// /uploadFile/**
        registry.addResourceHandler(vpath + "**")    //指定请求URL
//" file:E:/upload"
                .addResourceLocations("file:"+uploadpath);//指定映射到的资源
        }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())//添加拦截器类
                .addPathPatterns("/**")//指定拦截的请求地址
                .excludePathPatterns("/toLogin","/login");//排除的请求地址
        }


    }
