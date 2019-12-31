package com.seecen.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangJiankun
 * @date 2019/12/13 16:53
 * 描述：springboot拦截器写法和springMVC一样，只是配置通过Java类来配置
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      log.info("进入拦截器");
      return true;
    }
}
