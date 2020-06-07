package com.github.daqun.jira.ao.column;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;
import net.java.ao.schema.Default;

/**
 * @Classname DeleteColumn
 * @Description
 * @Date 2019/3/2 10:17
 * @Created by chenq
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
