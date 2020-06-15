package com.chenq.jira.plugin.api.ao.service;

import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.module.rest.vo.UserBean;
import com.github.daqun.jira.ao.service.IDaoService;

/**
 * 2020/6/14 21:45
 * Created by chenq
 */
public interface UserService extends IDaoService<UserEntity> {
    UserEntity create(UserBean userBean);
}
