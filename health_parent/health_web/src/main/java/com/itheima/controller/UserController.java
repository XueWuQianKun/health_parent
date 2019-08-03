package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        // SecurityContextHolder, 存入上下文的内容，
        // getAuthentication.getPrincipal() 获取认证信息userDetail
        //getAuthentication().getName(); 获取登陆的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = userDetails.getUsername();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
    }
}
