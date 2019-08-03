package com.itheima.service;

import com.itheima.pojo.Package;

import java.util.List;
import java.util.Map;

public interface PackageService {
    /**
     * 添加套餐
     * @param pkg
     * @param checkgroupIds
     */
    void add(Package pkg, Integer[] checkgroupIds);

    /**
     * 查询所有的套餐
     * @return
     */
    List<Package> findAll();

    /**
     * 获取套餐详情信息
     * @param id
     * @return
     */
    Package getPackageDetail(int id);

    /**
     * 通过编号获取套餐信息
     * @param id
     * @return
     */
    Package findById(int id);

    /**
     * 获取套餐预约数据
     * @return
     */
    List<Map<String,Object>> getPackageReport();
}
