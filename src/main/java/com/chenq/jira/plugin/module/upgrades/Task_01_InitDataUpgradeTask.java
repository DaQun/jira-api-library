package com.chenq.jira.plugin.module.upgrades;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.sal.api.message.Message;
import com.atlassian.sal.api.upgrade.PluginUpgradeTask;
import com.chenq.jira.plugin.api.ao.service.GroupService;
import com.chenq.jira.plugin.api.ao.service.UserService;
import com.chenq.jira.plugin.api.constants.PluginConstant;
import com.chenq.jira.plugin.module.upgrades.data.GroupDemoEnum;
import com.chenq.jira.plugin.module.upgrades.data.UserDemoEnum;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * 2020/6/15 9:27
 * Created by chenq
 */
@Component
@ExportAsService(PluginUpgradeTask.class)
public class Task_01_InitDataUpgradeTask implements PluginUpgradeTask {
    private final GroupService groupService;
    private final UserService userService;
    @Inject
    public Task_01_InitDataUpgradeTask(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public int getBuildNumber() {
        return 8;
    }

    @Override
    public String getShortDescription() {
        return "初始化数据";
    }

    @Override
    public Collection<Message> doUpgrade() throws Exception {
        doUpgradeInternal();
        return Collections.emptyList();
    }

    private void doUpgradeInternal() {
        for (GroupDemoEnum groupDemoEnum : GroupDemoEnum.values()) {
            groupService.create(groupDemoEnum.getGroupBean());
        }

        for (UserDemoEnum userDemoEnum : UserDemoEnum.values()) {
            userService.create(userDemoEnum.getUserBean());
        }

    }

    @Override
    public String getPluginKey() {
        return PluginConstant.PLUGIN_KEY;
    }
}
