package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.HealthException;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 体检预约
     *
     * @param orderInfo
     * @throws HealthException
     */
    @Override
    @Transactional
    public int addOrder(Map<String, String> orderInfo) throws HealthException {
        //3.1 是否可以预约，通过预约日期调用dao查询t_ordersetting
        String orderDate = orderInfo.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        //- 返回空值 不能预约，报错
        if(null == orderSetting){
            throw new HealthException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //- 有记录：已经预约人数是否>=可预约的人数 报错
        if(orderSetting.getReservations() >= orderSetting.getNumber()){
            throw new HealthException(MessageConstant.ORDER_FULL);
        }
        //3.2 先判断是否为会员 通过手机号码查询
        String telephone = orderInfo.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(null == member) {
            //- 不存在，调用Dao插入新会员t_member，获取ID
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setIdCard(orderInfo.get("idCard"));
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            memberDao.add(member);
        }
        //- 存在 取出会员ID
        Integer memberId = member.getId();
        //3.3 判断重复预约, 通过日期、会员的编号查询t_order
        Order order = new Order();
        order.setMemberId(memberId);
        try {
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
        } catch (Exception e) {
            e.printStackTrace();
            throw new HealthException(MessageConstant.ORDER_FAIL);
        }
        List<Order> orders = orderDao.findByCondition(order);
        //- 存在则表重复, 报错
        if(null != orders && orders.size() > 0){
            throw  new HealthException(MessageConstant.HAS_ORDERED);
        }
        //- 不存在，插入t_order, 订单
        //  会员ID，套餐ID，预约日期
        order.setPackageId(Integer.valueOf(orderInfo.get("packageId")));
        //   orderStatus: 未到诊
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderInfo.get("orderType"));
        orderDao.add(order);

        // 增加已预约的人数
        orderSettingDao.editReservationsByOrderDate(1, orderDate);

        return order.getId();
    }

    /**
     * 查询预约成功信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById(id);
    }
}
