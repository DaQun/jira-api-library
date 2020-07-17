package com.chenq.jira.plugin.api.ao.entity;

import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;

/**
 * @Classname UserEntity
 * @Description
 * @Date 2019/2/27 16:22
 * @Created by chenq
 */
public interface UserEntity extends BaseColumn, DeleteColumn {
    String getName();
    void setName(String name);

    String getAddress();
    void setAddress(String address);

    Integer getAge();
    void setAge(Integer age);

    /**
     * 这里会生成GroupEntity外键，
     * 字段名为GROUP_ID
     */
    GroupEntity getGroup();
    void setGroup(GroupEntity group);

    enum COLUMN {
        ADDRESS,NAME,AGE,GROUP_ID
    }

}
