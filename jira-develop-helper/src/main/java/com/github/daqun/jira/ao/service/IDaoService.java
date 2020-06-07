package com.github.daqun.jira.ao.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import net.java.ao.DBParam;
import net.java.ao.Entity;
import net.java.ao.Query;

import java.util.List;
import java.util.Map;

/**
 * @Classname IDaoService
 * @Description
 * @Date 2019/2/27 17:16
 * @Created by chenq
 */
@Transactional
public interface IDaoService<T extends Entity> {
    /**
     * 根据id获取实体
     * @param id 主键
     * @return 实体
     */
    T get(int id);

    /**
     * 删除实体
     * @param id 实体id
     */
    void delete(int id);


    /**
     * 删除实体
     * @param t 实体
     */
    void delete(T t);

    /**
     * 查询实体所有记录
     * @return list集合
     */
    List<T> all();

    Page<T> query(Query query, PageRequest pageRequest);

    T create(Map<String, Object> map);

    T create(DBParam... dbParams);

    void update(Entity entity, Object bean);

    void updateAndSave(Entity entity, Object bean);
}
