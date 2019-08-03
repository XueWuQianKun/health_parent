package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.text.ParseException;
import java.util.List;

public interface OrderSettingService {
    /**
     * 批量导入预约设置
     * @param list
     */
    void doImport(List<OrderSetting> list);

    /**
     * 预约信息日历展示
     * @param month
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(String month);

    /**
     * 设置预约信息
     * @param orderDate
     * @param number
     */
    void editNumberByDate(String orderDate, int number) throws ParseException;
}
