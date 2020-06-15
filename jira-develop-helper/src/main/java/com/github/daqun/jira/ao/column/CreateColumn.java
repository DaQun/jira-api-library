package com.github.daqun.jira.ao.column;

import net.java.ao.Entity;
import net.java.ao.Generator;
import net.java.ao.Polymorphic;

import java.util.Date;

/**
 * 创建日期、创建者字段
 * 2019/4/22 17:39
 * created by chenq
 */
@Polymorphic
public interface CreateColumn extends Entity {
    @Generator(UserKeyGenerator.class)
    String getCreator(); ///创建者
    void setCreator(String creator);

    @Generator(DateGenerator.class)
    Date getCreateTime(); ///创建时间
    void setCreateTime(Date createTime);

    enum COLUMN {
       CREATE_TIME ,CREATOR;
    }
}
