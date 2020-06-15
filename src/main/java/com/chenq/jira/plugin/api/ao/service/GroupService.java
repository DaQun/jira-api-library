package com.chenq.jira.plugin.api.ao.service;

import com.chenq.jira.plugin.api.ao.entity.GroupEntity;
import com.chenq.jira.plugin.module.rest.vo.GroupBean;
import com.github.daqun.jira.ao.service.IDaoService;
import com.github.daqun.jira.ao.service.ILogicalDeleteService;

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

}
