package com.itheima.service;

import java.util.Map;

public interface ReportService {
    /**
     * 获取运营数据统计
     * @return
     */
    Map<String,Object> getBusinessReportData();
}
