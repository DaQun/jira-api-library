package com.github.daqun.jira.ao.column;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;

import java.util.Date;

/**
 * 修改者、修改时间
 * 2019/4/22 17:40
 * created by chenq
 */
@Polymorphic
public interface UpdateColumn extends Entity {
    String getModifier(); /// 修改者
    void setModifier(String modifier);

    Date getModifyTime(); /// 修改时间
    void setModifyTime(Date modifyTime);

    enum COLUMN {
        MODIFIER ,MODIFY_TIME;
    }
}
