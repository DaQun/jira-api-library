package com.chenq.jira.plugin.api.ao.service.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.api.ao.service.GroupService;
import com.chenq.jira.plugin.api.ao.service.UserService;
import com.chenq.jira.plugin.module.rest.vo.UserBean;
import com.github.daqun.jira.ao.service.BaseService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 2020/6/14 21:46
 * Created by chenq
 */
@Component
public class UserServiceImpl  extends BaseService<UserEntity> implements UserService{

    private final GroupService groupService;

    @Inject
    public UserServiceImpl(@ComponentImport ActiveObjects ao, GroupService groupService) {
        super(ao);
        this.groupService = groupService;
    }

    @Override
    public UserEntity create(UserBean userBean) {
        UserEntity userEntity = ao.create(UserEntity.class);
        userEntity.setName(userBean.getName());
        userEntity.setAddress(userBean.getAddress());
        userEntity.setGroup(groupService.getByGroupNo(userBean.getGroupNo()));
        userEntity.setAge(userBean.getAge());
        userEntity.save();

        return userEntity;
    }
}
