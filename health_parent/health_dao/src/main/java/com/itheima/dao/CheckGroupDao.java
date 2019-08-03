package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 通过编号查询检查组信息
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组查询选中的检查项编号
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    /**
     * 更新检查组信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项的关系
     * @param checkGroupId
     */
    void deleteByCheckGroupId(Integer checkGroupId);

    /**
     * 查询所有的检查组信息
     * @return
     */
    List<CheckGroup> findAll();
}
