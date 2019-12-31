package com.seecen.springboot.controller;

import com.seecen.springboot.aop.MyLog;
import com.seecen.springboot.pojo.Admin;
import com.seecen.springboot.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ZhangJiankun
 * @date 2019/12/10 14:31
 * 描述：
 */
@Controller
public class AdminController {
    //定义日志对象
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Resource(name = "adminServiceWithCache")
    private AdminService adminService;
    //注入redis模板
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /*
    * 使用redis做缓存方式
    * 直接使用redisTemplate 手动操作缓存
    * */
    @ResponseBody
    @GetMapping("/admin")
    public Admin selectById(Long id){
        Admin admin = adminService.selectById(id);
        return admin;
    }

    @ResponseBody
    @GetMapping("/adminAll")
    public List<Admin> selectAll(){
        return adminService.selectAll();
    }

    //跳转页面，跳转到templates目录下
    @GetMapping("index")
    public String index(String name, ModelMap map){
        map.put("name",name);
        map.put("htmlTab","<span style='color:blue'>我是span</span>");
        return "/index.html";
    }

    //登录页面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "/login.html";
    }


    //验证登录
    @MyLog(logType = "0",connect = "用户登录")  //自定义的注解
    @ResponseBody
    @PostMapping("/login")
    public String login(Admin admin,HttpSession session ){
        Admin admin1 = adminService.selectByAdmin(admin.getAccount());
        if (admin1==null){
            LOGGER.error("用户名不存在account:%s",admin.getAccount());
            return "2";
        }
        if (admin.getPassword().equals(admin1.getPassword())){
            session.setAttribute("admin",admin);
            return "1";
        }else{
            return "0";
        }
    }

    //shiro加密之后的登录
    @ResponseBody
    @RequestMapping("/login2")
    public String login2(String account,String password,HttpSession session){
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //登录失败
        try{
            subject.login(new UsernamePasswordToken(account,password));
        }catch (Exception e){
            LOGGER.error("登录失败",e);
            return "4";
        }
        Admin admin = (Admin) subject.getPrincipal();
        session.setAttribute("admin",admin);
        return "1";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "/register.html";
    }
    //注册
    @PostMapping("/register")
    public String register(Admin admin) {
        System.out.println(admin);
        SimpleHash md5 = new SimpleHash("MD5",
                                            admin.getPassword(), admin.getAccount()+"",1024);
        admin.setPassword(String.valueOf(md5));
        adminService.insert(admin);
        //opsForValue 操作string类型
        redisTemplate.opsForValue().set("admin:"+admin.getAccount(),admin);
        return "/login.html";
    }

    @RequiresPermissions("admin:list")
    @GetMapping("list")
    public String list(ModelMap map){
        List<Admin> admins = adminService.selectAll();
        map.put("admins",admins);
        return "/list.html";
    }

    @RequiresPermissions("admin:update")
    @GetMapping("/update")
    public String update(ModelMap map,Long id){
        Admin admin = adminService.selectById(id);
        map.put("admin",admin);
        return "/edit.html";
    }
    @PostMapping("toUpdate")
    public String toUpdate(Admin admin){
        adminService.update2(admin);
        return "redirect:list";
    }

    @RequiresPermissions("admin:delete")
    @MyLog(logType = "3",connect = "用户删除")
    @GetMapping("delete")
    public String delete(Long id){
        int i = adminService.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/function")
    public String function(ModelMap map){
        map.put("now",new Date());
        map.put("strList", Arrays.asList("a","b","c","d"));
        return "function.html";
    }

    @ResponseBody
    @GetMapping("/selectByNameOrAccount")
    public List<Admin> selectByNameOrAccount(String name,String account){
        return adminService.selectByNameOrAccount(name,account);
    }

    @GetMapping("/nullPoint")
    public String nullPoint(){
        throw new NullPointerException("");
    }

}
