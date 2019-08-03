package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户认证与授权
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        // 查询用户信息，同时查询出用户所拥有的角色及权限
        return userDao.findByUsername(username);
    }
}
