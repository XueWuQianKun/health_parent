package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotPackage();

    /**
     * 查询预约成功信息
     * @param id
     * @return
     */
    Map<String,Object> findById(int id);

    /**
     * 按时间段统计预约数
     * @param startDate
     * @param endDate
     * @return
     */
    Integer findOrderCountBetweenDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
