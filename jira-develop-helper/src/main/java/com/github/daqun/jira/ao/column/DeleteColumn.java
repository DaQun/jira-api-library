package com.github.daqun.jira.ao.column;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;
import net.java.ao.schema.Default;

/**
 * 标识记录是否被删除（逻辑删除）
 * 2019/3/2 10:17
 * created by chenq
 */
@Polymorphic
public interface DeleteColumn extends Entity {
    @Default("false")
    boolean isDeleted();
    void setDeleted(boolean deleted);

    enum COLUMN {
        DELETED;
    }

}
