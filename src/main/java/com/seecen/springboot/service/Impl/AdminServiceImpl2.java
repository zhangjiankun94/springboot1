package com.seecen.springboot.service.Impl;

import com.seecen.springboot.mapper.AdminMapper;
import com.seecen.springboot.pojo.Admin;
import com.seecen.springboot.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhangJiankun
 * @date 2019/12/10 14:23
 * 描述：使用注解方式操作缓存
 * （1）cacheable:配置在方法上，能够对该方法的结果进行缓存：
 *       如果缓存中存在，则不会进行查询，直接从缓存中获取，如果缓存中不存在，则将结果存入缓存
 * （2）cacheput：更新缓存
 *
 * （3）cacheEvict:清楚缓存
 */
@Slf4j
@Service("adminServiceWithCache")
public class AdminServiceImpl2 implements AdminService {
    
    @Resource
    private AdminMapper mapper;

    @Override
    @Cacheable(cacheNames = "adminCache")//指定缓存注解，指定缓存名（或者""）
           //缓存实际生产的key值为cacheName::方法参数
    public Admin selectById(Long id){
        Admin admin = mapper.selectById(id);
        return admin;
    }

    /*
    * key值指定
    *  #参数名
    *  #name
    *  #admin.id
    *  #p0 p1 ： 根据参数的下标获取
    * */
    @Override
    @Cacheable(cacheNames = "adminCache",key = "{#p0,#p1}")
    public List<Admin> selectByNameOrAccount(String name,String account){
        return mapper.selectByNameOrAccount(name,account);
    }

    @Cacheable(cacheNames = "adminCache")
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
    //更新缓存，会将结果替换
    @CachePut(cacheNames = "adminCache",key = "#admin.id")
    @CacheEvict(cacheNames = "adminCache",allEntries = true)
    public Admin update2(Admin admin){
        int update = mapper.update(admin);
        return mapper.selectById(admin.getId());
    }

    public int update(Admin admin) {
        int update = mapper.update(admin);
        return update;
    }

    //删除缓存,不知道key删除单个,allEntries = true删除所有的缓存
    @CacheEvict(cacheNames = "adminCache",allEntries = true)
    @Override
    public int deleteById(Long id) {
        int i = mapper.deleteById(id);
        return i;
    }


}
