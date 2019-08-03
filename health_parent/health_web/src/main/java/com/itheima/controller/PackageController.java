package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private PackageService packageService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        // 1 获取文件的后缀名
        // 原文件名称 xxx.jpg
        String originalFilename = imgFile.getOriginalFilename();
        // 文件后缀名
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //2. 产生唯一的文件名（重复）
        UUID uuid = UUID.randomUUID();// 产生随机ID，不可重复
        // 保存到数据库
        String filename = uuid + extensionName;
        //3. 再把图片传给七牛
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(), filename);
            // 上传成功，就要保存到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            //4. 文件名返回给前端，在七牛中的domain也要一起返回给前端
            Map<String,String> imageMap = new HashMap<String,String>();
            // 七牛中图片的域名
            imageMap.put("domain",QiNiuUtil.DOMAIN);
            // 七牛的图片文件名，也是以后存到数据库的文件名
            imageMap.put("imgName", filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imageMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回失败
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Package pkg, Integer[] checkgroupIds){
        // 调用套餐业务服务
        packageService.add(pkg, checkgroupIds);
        // 添加套餐成功，保存图片到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pkg.getImg());
        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }
/*
    public static void main(String[] args) {
        // 原文件名称 xxx.jpg
        String originalFilename = "xxxx.jpg";
        // 文件后缀名
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(extensionName);
    }*/
}
