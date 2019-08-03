package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @PostMapping("/add")
    //@RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@Validated @RequestBody CheckItem checkItem) {
        // 调用业务服务
        checkItemService.add(checkItem);
        // 返回结果给前端
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用业务服务查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        // 返回给浏览器
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @GetMapping("/findById")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result findById(int id) {
        CheckItem checkitem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkitem);
    }

    @PostMapping("/update")
    //@RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result update(@RequestBody CheckItem checkItem) {
        // 调用业务服务更新
        checkItemService.update(checkItem);
        // 返回结果给前端
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        // 查询所有检查项，供页面中的列表选择
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
