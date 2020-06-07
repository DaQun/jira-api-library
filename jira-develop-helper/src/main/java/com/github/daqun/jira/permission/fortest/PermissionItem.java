package com.github.daqun.jira.permission.fortest;

import com.github.daqun.jira.permission.PermissionItemBase;
import com.github.daqun.jira.permission.ViewItem;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @Description
 * @Date 2019/4/12 11:35
 * @Created by chenq
 */
public enum PermissionItem implements PermissionItemBase {
    ORGANZIATION_MANAGE(ViewItem.builder().key("organization-manage").name("组织机构管理").build(),
            Sets.newHashSet("duty-person", "edit", "import")
            ),
    PERMISSION_MANAGE(ViewItem.builder().key("permission_manage").name("权限管理").build(),
            Sets.newHashSet("delete-role", "edit", "add")
                    )

    ;

    PermissionItem(ViewItem viewItem, Set<String> actions) {
        this.viewItem = viewItem;
        this.actions = actions;
    }

    private ViewItem viewItem;
    private Set<String> actions;

    @Override
    public ViewItem getViewItem() {
        return null;
    }

    @Override
    public Set<String> getActions() {
        return null;
    }
}
