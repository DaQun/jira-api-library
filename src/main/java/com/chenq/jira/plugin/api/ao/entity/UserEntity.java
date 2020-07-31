package com.chenq.jira.plugin.api.ao.entity;

import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import net.java.ao.schema.StringLength;

/**
 * 2019/2/27 16:22
 * created by chenq
 */
public interface UserEntity extends BaseColumn, DeleteColumn {
    String getName();
    void setName(String name);

    String getAddress();
    void setAddress(String address);

    Integer getAge();
    void setAge(Integer age);

    @StringLength(StringLength.UNLIMITED)
    String desc();
    void setDesc(String desc);

    /**
     * 这里会生成GroupEntity外键，
     * 字段名为GROUP_ID
     */
    GroupEntity getGroup();
    void setGroup(GroupEntity group);

    enum COLUMN {
        ADDRESS,NAME,AGE,GROUP_ID, DESC
    }

}
