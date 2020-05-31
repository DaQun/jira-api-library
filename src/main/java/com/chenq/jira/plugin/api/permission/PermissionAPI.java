package com.chenq.jira.plugin.api.permission;

import com.atlassian.jira.permission.GlobalPermissionKey;
import com.atlassian.jira.security.GlobalPermissionManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.constant.IConstant;
import lombok.RequiredArgsConstructor;

/**
 * 2020/4/17 16:09
 * Created by chenq
 */
@RequiredArgsConstructor
public class PermissionAPI {

    @ComponentImport
    private final GlobalPermissionManager globalPermissionManager;

    public boolean hasPermission(){
        return globalPermissionManager.hasPermission(GlobalPermissionKey.SYSTEM_ADMIN, IConstant.OPERATOR);
    }

}
