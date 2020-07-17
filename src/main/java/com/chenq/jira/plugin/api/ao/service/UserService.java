package com.chenq.jira.plugin.api.ao.service;

import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.module.rest.vo.UserBean;
import com.github.daqun.jira.ao.service.IDaoService;

/**
 * 2020/6/14 21:45
 * Created by chenq
 */
public interface UserService extends IDaoService<UserEntity> {
    /**
     * 创建
     *
     * @param userBean 前台传过来的bean
     * @return 创建后的entity
     */
    UserEntity create(UserBean userBean);

    /**
     * 根据用户名模糊查询
     *
     * @param name 用户名
     * @return 符合条件的用户
     */
    UserEntity[] queryByName(String name);

    /**
     * 分页查询用户
     *
     * @param name 用户名（可为null）
     * @param pageRequest 分页信息
     * @return 分页数据
     */
    Page<UserEntity> pageUser(String name, PageRequest pageRequest);
}
