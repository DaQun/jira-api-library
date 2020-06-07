package com.github.daqun.jira.permission;

import java.util.Collection;

/**
 * @Description
 * @Date 2019/4/12 11:26
 * @Created by chenq
 */
@Deprecated
public interface AuthorizationInfo {

    /**
     * 获取用户在插件角色表中定义的角色
     * @return
     */
    Collection<String> getRoles();

    /**
     * 获取用户在jira中的角色
     */
    Collection<String> getJiraRoles();

    /**
     * 获取用户权限
     * @return 用户权限的字符串集合
     */
    Collection<String> getStringPermissions();

    /**
     * 获取用户在插件权限表中定义的权限
     * @return
     */
    Collection<Permission> getObjectPermissions();


}
