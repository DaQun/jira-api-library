package com.github.daqun.jira.ao.column;

import net.java.ao.Polymorphic;

/**
 * 创建日期、创建者
 * 修改日期、修改者
 * 2019/3/1 13:42
 * created by chenq
 */
@Polymorphic
public interface BaseColumn extends CreateColumn, UpdateColumn {

}
