package com.github.daqun.jira.ao.service;

import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import net.java.ao.Entity;
import net.java.ao.Query;

import java.util.List;

/**
 * @Classname ILogicalDeleteService
 * @Description
 * @Date 2019/3/2 20:12
 * @Created by chenq
 */
public interface ILogicalDeleteService<T extends Entity> {
    /**
     * 删除实体
     * @param t 实体
     */
    void deleteLogical(T t);
    /**
     * 删除实体
     * @param id 实体id
     */
    void deleteLogical(int id);

    List<T> allLogical();
    /**
     * 根据id获取实体
     * TODO 这个方法没什么必要
     * @param id 主键
     * @return 实体
     */
    T getLogical(int id);

    Page<T> queryLogical(Query query, PageRequest pageRequest);

}
