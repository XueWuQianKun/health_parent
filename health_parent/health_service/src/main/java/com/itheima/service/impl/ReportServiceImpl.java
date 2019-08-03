package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.PackageDao;
import com.itheima.service.ReportService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PackageDao packageDao;

    /**
     * 获取运营数据统计
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        // 统计的日期，今天
        String today = DateUtils.parseDate2String(new Date(), "yyyy-MM-dd");
        String reportDate = today;
        // 本周星期一
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday(), "yyyy-MM-dd");
        // 本周的星期天
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek(), "yyyy-MM-dd");
        // 本月1号
        String firstDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDayOfThisMonth(), "yyyy-MM-dd");
        // 本月最后一天
        String lastDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getLastDayOfThisMonth(),"yyyy-MM-dd");

        // ============ 会员统计数据 =======================
        // 本日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        // 总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        // 本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayOfThisMonth);

        // ============ 预约到诊数据统计 =======================
        // 今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        // 今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        // 本周预约数, 按日期的范围查询 >=星期一 <=星期天
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(thisWeekMonday, thisWeekSunday);
        // 本周到诊数, 到今天为止
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        // 本月预约数 获取1号的日期，本月最后一天的日期
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(firstDayOfThisMonth, lastDayOfThisMonth);
        // 本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);

        // ===============热门套餐 取前4个套餐=========================
        List<Map<String,Object>> hotPackage = packageDao.findHotPackage();

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("reportDate",reportDate);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotPackage",hotPackage);

        return resultMap;
    }
}
