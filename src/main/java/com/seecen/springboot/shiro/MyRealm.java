package com.seecen.springboot.shiro;

/**
 * @author ZhangJiankun
 * @date 2019/12/27 10:08
 * 说明：
 */
import com.seecen.springboot.pojo.Admin;
import com.seecen.springboot.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述:
 *     权限认证类，验证权限信息及用户身份信息
 * @author bigpeng
 * @create 2019-07-16 17:26
 */
public class MyRealm extends AuthorizingRealm{

    @Resource(name = "adminServiceWithCache")
    @Lazy// 让shiro先于service加载
    private AdminService adminService;
    /**
     * 权限认证 ，告知shiro当前登录用户拥有的权限及角色信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录的用户信息
        Admin admin = (Admin)
                principalCollection.getPrimaryPrincipal();
        //1 创建权限信息对象
        SimpleAuthorizationInfo info =
                new SimpleAuthorizationInfo();
        //2 设置用户拥有的角色信息
        HashSet<String> roles = new HashSet<>();
        //===================写死用户权限============
        roles.add("admin");
        info.setRoles(roles);
        //权限编码集合
        Set<String> permissions = new HashSet<>();
        permissions.add("admin:list");
        permissions.add("admin:update");

//        if(admin.getRole()!=null)
//            roles.add(admin.getRole().getRolename());
//        info.setRoles(roles);
//        //3 设置用户权限信息
//        Set<String> permissions=new HashSet<>();
//        for (OMenu oMenu : admin.getMenuList()) {
//            permissions.add(oMenu.getUrl());
//        }
        info.setStringPermissions(permissions);
        return info;
    }
    
    /**
     * 身份认证 登陆时判定用户身份信息
     * @param authenticationToken 身份令牌
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.authenticationToken 中保存了用户的登陆信息，
        // 即前台登陆页面用户输入的用户名 密码
        String username = (String) authenticationToken.getPrincipal();
        //2.根据用户名查询用户信息
        Admin oAdmin = adminService.selectByAdmin(username);
        //如果用户不存在，返回null 登陆验证不通过
        if(oAdmin==null){
            return null;
        }
        //3.如果存在，则返回一个AuthenticationInfo对象，
        // shiro会根据返回对象进行身份认证
        /**
         * 身份认证对象构造
         * 参数1：指定需要保存到session中的对象
         * 参数2：数据库中存储的密码
         * 参数3：盐值  md5加密中使用的盐(一个字符串)，该值需要保存到数据库
         * 参数4：realm的名称
         */
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(oAdmin,
                        oAdmin.getPassword(),
                        new SimpleByteSource(
                                oAdmin.getAccount()+""),
                        getName());
        return info;
    }

    /**
     * 设置realm名称
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("myRealm");
    }
}