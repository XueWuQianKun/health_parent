package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.OrderSetting;
import com.itheima.dao.OrderSettingDao;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置
     *
     * @param list
     */
    @Override
    @Transactional
    public void doImport(List<OrderSetting> list) {
        if (null != list) {
            for (OrderSetting os : list) {
                // 判断是否有设置信息
                int count = orderSettingDao.findCountByOrderDate(os.getOrderDate());
                // 有则更新
                if (count > 0) {
                    orderSettingDao.editNumberByOrderDate(os);
                } else {
                    // 无则插入
                    orderSettingDao.add(os);
                }
            }
        }
    }

    /**
     * 预约信息日历展示
     *
     * @param month
     * @return
     */
    @Override
    public List<OrderSetting> getOrderSettingByMonth(String month) {
        //Service通过月份拼接开始日期与结束日期，
        String startDate = month + "-01";
        String endDate = month + "-31";
        // 调用dao查询，得到结果封装List<Map>，返回给controller
        return orderSettingDao.getOrderSettingByMonth(startDate, endDate);
    }

    /**
     * 设置预约信息
     * @param orderDate
     * @param number
     */
    @Override
    public void editNumberByDate(String orderDate, int number) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OrderSetting os = new OrderSetting(sdf.parse(orderDate),number);
        // 判断是否有设置信息
        int count = orderSettingDao.findCountByOrderDate(os.getOrderDate());
        // 有则更新
        if (count > 0) {
            orderSettingDao.editNumberByOrderDate(os);
        } else {
            // 无则插入
            orderSettingDao.add(os);
        }
    }
}
