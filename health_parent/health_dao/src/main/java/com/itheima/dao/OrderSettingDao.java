package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    /**
     * 通过日期查询预约信息是否存在
     * @param orderDate
     * @return
     */
    int findCountByOrderDate(Date orderDate);

    /**
     * 更新可预约的数量
     * @param os
     */
    void editNumberByOrderDate(OrderSetting os);

    /**
     * 添加预约信息
     * @param os
     */
    void add(OrderSetting os);

    /**
     * 预约信息日历展示
     * @param startDate
     * @param endDate
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    //更新已预约人数
    void editReservationsByOrderDate(@Param("num") int num, @Param("orderDate") String orderDate);

    /**
     * 通过日期查询预约信息
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(String orderDate);
}
