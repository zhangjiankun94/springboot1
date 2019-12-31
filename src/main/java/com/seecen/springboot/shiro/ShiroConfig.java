package com.seecen.springboot.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangJiankun
 * @date 2019/12/27 10:08
 * 说明：
 * shiro配置类：
 * 1.sessionManager
 * 2.加密方式：算法，加密次数
 * 3.配置Realm
 * 4.
 * 5.filter
 */
@Configuration
public class ShiroConfig {
    /**
     * 配置加密方式
     * @return
     */
    @Bean
    //用来声明bean 相当于在spring配置文件中配置<bean>标签
    HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher matcher =
                new HashedCredentialsMatcher();
        //设置属性值
        //设置加密算法
        matcher.setHashAlgorithmName("MD5");
        //设置加密次数
        matcher.setHashIterations(1024);
        return matcher;
    }

    //配置Realm
    @Bean
    MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        //设置加密方式
        myRealm.setCredentialsMatcher(credentialsMatcher());
        return myRealm;
    }
    //配置安全管理器
    @Bean
    DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();

        securityManager.setRealm(myRealm());

//        securityManager.setSessionManager(redisSessionManager());
//        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition filters =
                new DefaultShiroFilterChainDefinition();
        //设置不需要登录的请求地址
        filters.addPathDefinition("/login","anon");
        filters.addPathDefinition("/login2","anon");
        filters.addPathDefinition("/toLogin","anon");
        filters.addPathDefinition("/index","anon");
        filters.addPathDefinition("/register","anon");
        filters.addPathDefinition("/toRegister","anon");
        filters.addPathDefinition("/sendVerifyCode","anon");

        // 静态资源
        filters.addPathDefinition("/images/**","anon");
        filters.addPathDefinition("/js/**","anon");
        filters.addPathDefinition("/css/**","anon");
        //退出
        filters.addPathDefinition("/logout","logout");
        filters.addPathDefinition("/**","authc");
        return filters;
    }

    //配置shiro方言，整合thymeleaf时使用，使其支持shiro标签
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * shiro 注解在springmvc中生效
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
