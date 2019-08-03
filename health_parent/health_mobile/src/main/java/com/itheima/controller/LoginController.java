package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result loginCheck(@RequestBody Map<String,String> map, HttpServletResponse res){
        String telephone = map.get("telephone");
        String code = map.get("validateCode");
        // 验证验证码
        Jedis jedis = jedisPool.getResource();
        // key要与存进去时一致
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        String codeInRedis = jedis.get(key);
        if(null == codeInRedis) {
            //提示发送验证
            return new Result(false, MessageConstant.SEND_VALIDATECODE);
        }
        // 判断验证码是否相同
        if(!codeInRedis.equals(code)){
            // 不同
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 判断会员是否存在
        Member member = memberService.findByTelephone(telephone);
        if(null == member){
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        // 跟踪用户, 采用cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setMaxAge(30 * 24 * 60 * 60);// 一个星期
        cookie.setPath("/");// 只要用户访问了网站
        res.addCookie(cookie); // 只要用户访问了网站，就会在请求头的Header中带上这个cookie参数
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
