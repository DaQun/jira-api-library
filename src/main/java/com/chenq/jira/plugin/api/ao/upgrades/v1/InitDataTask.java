package com.chenq.jira.plugin.api.ao.upgrades.v1;

import com.chenq.jira.plugin.api.ao.entity.GroupEntity;
import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.api.ao.upgrades.AbstractUpgradeTask;

/**
 * 2020/4/13 19:50
 * Created by chenq
 */
public class InitDataTask extends AbstractUpgradeTask {

    @Override
    protected int getVersion() {
        return 4;
    }

    @Override
    protected void doUpgrade() {
        // 声明需要操作的表
        ao.migrate(GroupEntity.class, UserEntity.class);


    }


}
