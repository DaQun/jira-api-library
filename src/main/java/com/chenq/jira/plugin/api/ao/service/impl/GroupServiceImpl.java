package com.chenq.jira.plugin.api.ao.service.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.api.ao.entity.GroupEntity;
import com.chenq.jira.plugin.api.ao.service.GroupService;
import com.chenq.jira.plugin.api.constants.GroupTypeEnum;
import com.chenq.jira.plugin.module.rest.vo.GroupBean;
import com.github.daqun.jira.ao.service.BaseService;
import net.java.ao.DBParam;
import org.apache.commons.lang3.EnumUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 2020/6/14 21:27
 * Created by chenq
 */
@Component
public class GroupServiceImpl extends BaseService<GroupEntity> implements GroupService {
    @Inject
    public GroupServiceImpl(@ComponentImport ActiveObjects ao) {
        super(ao);
    }

    @Override
    public GroupEntity create(GroupBean groupBean) {
        // ao.create会立即在数据库中插入一条数据，如果有非空字段，需要写在第二个参数里
        GroupEntity groupEntity = ao.create(GroupEntity.class,
                new DBParam(GroupEntity.COLUMN.GROUP_NO.name(), groupBean.getGroupNo())
        );
        groupEntity.setGroupName(groupBean.getGroupName());
        groupEntity.setGroupNo(groupBean.getGroupNo());
        groupEntity.setParent(groupBean.getParent());
        groupEntity.setType(EnumUtils.getEnum(GroupTypeEnum.class, groupBean.getType()));
        // 记得调用save方法，保存到数据库中
        groupEntity.save();

        return groupEntity;
    }

    @Override
    public GroupEntity getByGroupNo(String groupNo) {
        GroupEntity[] groupEntities = ao.find(GroupEntity.class, GroupEntity.COLUMN.GROUP_NO + " = " + groupNo);

        return groupEntities.length == 1 ? groupEntities[0] : null;
    }
}
