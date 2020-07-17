package com.chenq.jira.plugin.api.ao.service;

import com.chenq.jira.plugin.api.ao.entity.GroupEntity;
import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.module.rest.vo.GroupBean;
import com.github.daqun.jira.ao.service.IDaoService;
import com.github.daqun.jira.ao.service.ILogicalDeleteService;

import java.util.Set;

/**
 * 2020/6/14 21:21
 * Created by chenq
 */
public interface GroupService extends IDaoService<GroupEntity>, ILogicalDeleteService<GroupEntity> {

    /**
     * 根据groupBean创建entity
     */
    GroupEntity create(GroupBean groupBean);

    /**
     * 根据groupNo(唯一的)获取groupEntity
     */
    GroupEntity getByGroupNo(String groupNo);

    /**
     * 根据部门no获取用户
     * @param groupNo 部门no
     * @return 部门下用户集合
     */
    Set<UserEntity> getUsersByGroupNo(String groupNo);

    /**
     * 获取部门下的用户数
     * @param groupId 部门id
     * @return 用户数
     */
    long countUsersOfGroup(Integer groupId);

}
