package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 通过手机号码查询会员
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    /**
     * 会员数量统计
     * @return
     */
    @Override
    public Map<String, Object> getMemberReport() {
        // 1. 获取上一年的时间, java中操作日期的对象，日历, 默认是获取当前系统时间的日期
        Calendar calendar = Calendar.getInstance();
        // 年的值减去1年，去年
        calendar.add(Calendar.YEAR, -1);
        // 2. 遍历12个月，计算每个月的会员数量
        List<String> months = new ArrayList<String>();
        List<Integer> memberCount = new ArrayList<Integer>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = "";
        for(int i = 0; i < 12; i++){
            // 当前要计算的月份, 是否最后一天
            calendar.add(Calendar.MONTH,1);
            // 3. 封装到月份数所 months
            month = sdf.format(calendar.getTime());
            months.add(month);
            // 4. 封装每个月的会员总数到memberCount
            memberCount.add(memberDao.findMemberCountBeforeDate(month + "-31"));
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("months", months);
        result.put("memberCount",memberCount);
        return result;
    }
}
