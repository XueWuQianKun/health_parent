package com.itheima.service;

import com.itheima.exception.HealthException;

import java.util.Map;

public interface OrderService {
    /**
     * 体检预约
     * @param orderInfo
     * @throws HealthException
     */
    int addOrder(Map<String,String> orderInfo) throws HealthException;

    /**
     * 查询预约成功信息
     * @param id
     * @return
     */
    Map<String,Object> findById(int id);
}
