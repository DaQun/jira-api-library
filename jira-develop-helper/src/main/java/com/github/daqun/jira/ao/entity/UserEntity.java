package com.github.daqun.jira.ao.entity;

import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import net.java.ao.Entity;

/**
 * @Classname UserEntity
 * @Description
 * @Date 2019/2/27 16:22
 * @Created by chenq
 */
public interface UserEntity extends Entity, BaseColumn, DeleteColumn {
    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    Integer getAge();

    void setAge(Integer age);

    enum COLUMN {
        ADDRESS,NAME,AGE,ID,MODIFIER,CREATOR,CREATE_TIME,MODIFY_TIME;
    }

}
