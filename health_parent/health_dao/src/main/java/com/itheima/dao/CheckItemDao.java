package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 查询检查项的ID是否被检查组引用
     * @param id
     * @return
     */
    int findCountById(int id);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过编号查询检查项
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * // 查询所有检查项，供页面中的列表选择
     * @return
     */
    List<CheckItem> findAll();
}
