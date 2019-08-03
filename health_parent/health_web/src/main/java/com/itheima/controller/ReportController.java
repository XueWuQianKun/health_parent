package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.PackageService;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private PackageService packageService;

    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        // 调用memberService完成报表数据的查询 {months, memberCount}
        Map<String,Object> result = memberService.getMemberReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, result);
    }

    @GetMapping("/getPackageReport")
    public Result getPackageReport(){
        // 获取套餐占比数据
        List<Map<String,Object>> packageCount = packageService.getPackageReport();
        // 获取套餐名称的集合
        List<String> packageNames = new ArrayList<String>();
        for (Map<String, Object> map : packageCount) {
            packageNames.add((String)map.get("name"));
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("packageNames",packageNames);
        resultMap.put("packageCount", packageCount);

        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS, resultMap);
    }

    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        // 获取运营数据统计
        Map<String,Object> reportData = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,reportData);
    }

    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest req, HttpServletResponse res){
        // 1.获取模板, getRealPath 获取运行在电脑上的路径 webapp目录
        String templatePath = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        // 2.获取运营数据
        Map<String, Object> reportData = reportService.getBusinessReportData();
        // 3.操作模板，把运营数据填入模板中
        //  操作excel
        // 设置http头信息
        String filename = "运营统计数据.xlsx";
        try {
            // filename.getBytes() UTF-8字符打散，字节数组，ISO-8859-1  latin-1就能支持
            filename = new String(filename.getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        res.addHeader("Content-Disposition","attachment;filename=" + filename);
        // 设置http的内容体的格式
        res.setContentType("application/vnd.ms-excel");

        try(
                XSSFWorkbook wk = new XSSFWorkbook(templatePath);
                OutputStream os = res.getOutputStream();
        ){
            // 工作表
            XSSFSheet sht = wk.getSheetAt(0);
            //日期
            sht.getRow(2).getCell(5).setCellValue((String)reportData.get("reportDate"));
            //新增会员数
            sht.getRow(4).getCell(5).setCellValue(reportData.get("todayNewMember").toString());
            //总会员数
            sht.getRow(4).getCell(7).setCellValue(reportData.get("totalMember").toString());

            //本周新增会员数
            sht.getRow(5).getCell(5).setCellValue(reportData.get("thisWeekNewMember").toString());
            //本月新增会员数
            sht.getRow(5).getCell(7).setCellValue(reportData.get("thisMonthNewMember").toString());

            //今日预约数
            sht.getRow(7).getCell(5).setCellValue(reportData.get("todayOrderNumber").toString());
            //今日到诊数
            sht.getRow(7).getCell(7).setCellValue(reportData.get("todayVisitsNumber").toString());

            //本周预约数
            sht.getRow(8).getCell(5).setCellValue(reportData.get("thisWeekOrderNumber").toString());
            //本周到诊数
            sht.getRow(8).getCell(7).setCellValue(reportData.get("thisWeekVisitsNumber").toString());

            //本月预约数
            sht.getRow(9).getCell(5).setCellValue(reportData.get("thisMonthOrderNumber").toString());
            //本月到诊数
            sht.getRow(9).getCell(7).setCellValue(reportData.get("thisMonthVisitsNumber").toString());

            int rowCount = 12;// 热门套餐的开始行下标
            List<Map<String,Object>> hotPackage = (List<Map<String,Object>>)reportData.get("hotPackage");
            if(null != hotPackage && hotPackage.size() > 0){
                XSSFRow row = null;
                for (Map<String, Object> pkg : hotPackage) {
                    row = sht.getRow(rowCount);
                    row.getCell(4).setCellValue((String)pkg.get("name"));
                    row.getCell(5).setCellValue(pkg.get("count").toString());
                    row.getCell(6).setCellValue(((BigDecimal)pkg.get("proportion")).doubleValue());
                    row.getCell(7).setCellValue((String)pkg.get("remark"));
                    rowCount++;
                }
            }
            // 4.输出给输出流实现下载
            wk.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
