package com.itheima.dao;

import com.itheima.pojo.User;

public interface UserDao {
    /**
     * 查询用户信息，同时查询出用户所拥有的角色及权限
     * @param username
     * @return
     */
    User findByUsername(String username);
}
