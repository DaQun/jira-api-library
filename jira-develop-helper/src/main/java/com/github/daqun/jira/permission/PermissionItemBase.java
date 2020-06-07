package com.github.daqun.jira.permission;

import java.util.Set;

/**
 * 权限控制的单元
 * @Date 2019/4/12 10:55
 * @Created by chenq
 */
public interface PermissionItemBase {

    /**
     * 视图项
     * @return
     */
    ViewItem getViewItem();

    /**
     * 页面上有哪些功能，需要对其进行控制
     * @return
     */
    Set<String> getActions();

}
