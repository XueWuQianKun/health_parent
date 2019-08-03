package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.util.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result update(@RequestParam("excelFile")MultipartFile excelFile){
        // 解析excel
        try {
            // 读取excel的内容，stirngs, 每一行就是一个String数组
            List<String[]> strings = POIUtils.readExcel(excelFile);
            // 转成List<ordersetting>
            List<OrderSetting> list = new ArrayList<OrderSetting>();
            OrderSetting os = null;
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] string : strings) {
                // 每一行，每一条记录，实体对象
                os = new OrderSetting(sdf.parse(string[0]), Integer.valueOf(string[1]));
                list.add(os);
            }
            // 调用业务实现
            orderSettingService.doImport(list);
            // 返回结果
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);

    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        List<OrderSetting> list = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(String orderDate, int number){
        try {
            orderSettingService.editNumberByDate(orderDate, number);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ORDERSETTING_FAIL);
    }
}
