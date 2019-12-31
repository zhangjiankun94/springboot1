package com.seecen.springboot.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seecen.springboot.pojo.Admin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ZhangJiankun
 * @date 2019/12/13 11:24
 * 描述：
 */
@Aspect //切面注解
@Component
@Slf4j  //lombok日志注解
public class LogAspect {
    //通过注解定义切入点
    @Pointcut("execution(public * com.seecen.springboot.controller.*.*(..))")
    public void log(){ }

//    @Before("log()")
    public void before(){
        System.out.println("前置通知");
    }

    //指定注解的切入点，会切入到添加了该注解的所有方法
    @Pointcut("@annotation(com.seecen.springboot.aop.MyLog)")
    public void logAnnotation(){}

    @Before("logAnnotation()")
    public void logbefore(JoinPoint joinPoint){
        System.out.println("注解日志");
        //获取注解中的信息
        //1.获取链接点的方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //2.通过方法签名得到方法
        Method method = signature.getMethod();
        //3.获取方法上面定义的注解               注解类型
        MyLog annotation = method.getAnnotation(MyLog.class);
        //4.根据注解获取注解定义的属性
        String logType = annotation.logType(); //日志类型
        String connect = annotation.connect(); //日志的内容
        System.out.printf("获取到注解信息:\nlogType:%s,content:%s\n",logType,connect);
        //获取请求数据  request对象
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();//获取请求的对象
        //获取客户端IP地址
        String ip = request.getRemoteAddr();
        log.info("获取到请求IP:{}",ip);
        //获取请求数据,key value
        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(parameterMap);
            log.info("获取到请求数据"+jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("获取请求数据失败:{}",e);
        }
        //获取登录用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin!=null) {
            String account = admin.getAccount();
            log.info("操作人："+account);
        }
}







    //模拟事务注解切入
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactional(){}

//    @Around("transactional()")
    public void around(ProceedingJoinPoint joinPoint){
        System.out.println("开启事务");
        try {
            Object proceed = joinPoint.proceed();
            System.out.println("提交事务");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("事务回滚");
        }
    }

}
