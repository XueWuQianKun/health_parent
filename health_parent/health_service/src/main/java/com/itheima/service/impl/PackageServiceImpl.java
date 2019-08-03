package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PackageDao;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Override
    @Transactional
    public void add(Package pkg, Integer[] checkgroupIds) {
        // 添加套餐，插入套餐表
        packageDao.add(pkg);
        // 获取套餐的ID
        Integer pkgId = pkg.getId();
        // 循环遍历检查组的编号，插入关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageAndCheckGroup(pkgId, checkgroupId);
            }
        }
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Package> findAll() {
        return packageDao.findAll();
    }

    /**
     * 获取套餐详情信息
     * @param id
     * @return
     */
    @Override
    public Package getPackageDetail(int id) {
        return packageDao.getPackageDetail(id);
    }

    /**
     * 通过编号获取套餐信息
     * @param id
     * @return
     */
    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }

    /**
     * 获取套餐预约数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getPackageReport() {
        return packageDao.getPackageReport();
    }
}
