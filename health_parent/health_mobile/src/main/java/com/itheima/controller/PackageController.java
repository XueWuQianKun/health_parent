package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.apache.poi.hpsf.ReadingNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    private static final Logger log = LoggerFactory.getLogger(PackageController.class);

    @Reference
    private PackageService packageService;

    @GetMapping("/getPackage")
    public Result getPackage() {
        // 流程性质，代码走到哪里了
        log.info("getPackage.do ....");
        // 调用业务服务查询所有的套餐列表
        List<Package> list = packageService.findAll();
        // 拼接图片list.forEach  $.each, js list.forEach
        // 把集合中的每个元素取出来处理一下
        /*for (Package pkg : list) {

        }*/
        log.info("getPackage.do finish size=" + (null == list?0:list.size()));
        list.forEach(pkg -> {
            pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        });
        // 返回给前端
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, list);
    }

    @GetMapping("/getPackageDetail")
    public Result getPackageDetail(int id){
        // 魔鬼数字，字符串
        /*if(id.equals("id")){

        }*/
        log.debug("pkgId="+ id);
        // 调用业务查询
        Package pkg = packageService.getPackageDetail(id);
        // 拼接图片地址
        pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, pkg);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        // 调用业务查询
        Package pkg = packageService.findById(id);
        // 拼接图片地址
        pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, pkg);
    }
}
