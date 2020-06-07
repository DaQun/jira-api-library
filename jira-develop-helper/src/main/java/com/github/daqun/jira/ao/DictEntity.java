package com.github.daqun.jira.ao;

import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import net.java.ao.Entity;

/**
 * @Classname DictEntity
 * @Description 字典类
 * @Date 2019/3/22 18:37
 * @Created by chenq
 */
public interface DictEntity extends Entity, BaseColumn, DeleteColumn {

    String getCode();
    void setCode(String code);

}
