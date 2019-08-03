package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SecurityUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        User user = findByUsername(username);
        //String username, 用户名
        //String password, 密码
        //Collection<? extends GrantedAuthority> authorities 集合，List,set
        //authorities 登陆用户所拥有的权限集合 授权
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // String role 角色名/权限名
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(sga);
        sga = new SimpleGrantedAuthority("add");
        authorities.add(sga);

        // 登陆用户信息
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
        //return null; // 认证失败
        return userDetails;
    }

    /**
     * 通过用户名查询数据库
     * 模拟
     */
    private User findByUsername(String username){
        if("admin".equals(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword("$2a$10$9SZR4C.NUbSVeDXwMkmnfOIsvET87mdgKqYHk3jM7QwGW3/nsIWnS");
            return user;
        }
        return null;
    }
}
