package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查询用户信息 username数据库 必须唯一
        // 完成了所有的角色和权限
        User user = userService.findByUsername(username);
        if(null == user){
            return null;
        }
        // 得到用户所拥有的角色和权限
        Set<Role> roles = user.getRoles();
        // 授权
        // 用户所拥有的权限集合
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        if(null != roles){
            SimpleGrantedAuthority sga = null;
            for (Role role : roles) {
                // 授予角色
                sga = new SimpleGrantedAuthority(role.getKeyword());
                authorityList.add(sga);
                // 角色下的权限
                Set<Permission> permissions = role.getPermissions();
                if(null != permissions){
                    // 添加权限
                    for (Permission perm : permissions) {
                        sga = new SimpleGrantedAuthority(perm.getKeyword());
                        authorityList.add(sga);
                    }
                }
            }
        }
        // 完成授权
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
        // Security会把认证信息存到session中
        return userDetails;
    }
}
