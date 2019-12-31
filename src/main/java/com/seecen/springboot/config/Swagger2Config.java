package com.seecen.springboot.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhangjk
 * @date 2019/12/26 10:36
 *
 */
@Configuration//配置类的注解
@EnableSwagger2//开启swagger2注解
public class Swagger2Config {
    @Value("${swagger.enable}")//获取配置文件中的内容
    private boolean swaggerEnable;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())// 接口文档的基本信息
                .enable(swaggerEnable)//是否开启
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//指定添加了该注解的类为API接口
                .paths(PathSelectors.any())
                .build();
    }

    /*
    * 配置api,基本信息 版本号等
    * */
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("user restful APIs")
                .description("user restful APIs")
                .version("1.0")
                .build();
    }
}
