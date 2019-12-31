package com.seecen.springboot.service.Impl;

import com.seecen.springboot.mapper.AdminMapper;
import com.seecen.springboot.pojo.Admin;
import com.seecen.springboot.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhangJiankun
 * @date 2019/12/10 14:23
 * 描述：
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    
    @Resource
    private AdminMapper mapper;

    //注入redis模板
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    
    @Override
    public Admin selectById(Long id){
        if (redisTemplate.hasKey("admin:"+id)){//如果缓存中存在对应的key,则直接返回
            //从缓存中获取对象
            Admin o = (Admin) redisTemplate.opsForValue().get("admin:"+id);
            log.info("从缓存中获取到数据，id:{},admin:{}",id,o);
            return o;
        }
        //如果缓存中没有，则去数据库查询
        Admin admin = mapper.selectById(id);
        log.info("缓存中不存在，从数据库查询,id:{},admin:{}",id,admin);
        //存入缓存
        redisTemplate.opsForValue().set("admin:"+id,admin);
        return admin;
    }

    @Override
    public List<Admin> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public Admin selectByAdmin(String account) {
        return mapper.selectByAdmin(account);
    }

    @Override
    public int insert(Admin admin) {
        return mapper.insert(admin);
    }

    //Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED) //配置隔离级别和传播特性
    @Transactional   //事务注解，加了该注解后，该方法中的所有sql会绑定在一个事务中
    @Override
    public int update(Admin admin) {
        int update = mapper.update(admin);
       //admin.setAccount("张三");
       // admin.setName("张三");
        //mapper.insert(admin);
        //redisTemplate.opsForValue().set("admin:"+admin.getId(),admin);
        return update;
    }

    @Override
    public int deleteById(Long id) {
        int i = mapper.deleteById(id);
        //删除缓存
        redisTemplate.delete("admin"+id);
        return i;
    }


}
