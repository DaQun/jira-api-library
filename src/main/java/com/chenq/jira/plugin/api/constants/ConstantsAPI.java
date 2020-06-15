package com.chenq.jira.plugin.api.constants;

import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * jira提供了一些常用对象的工具类
 * 2020/6/11 9:52
 * Created by chenq
 */
@Component
public class ConstantsAPI {
    /**
     * demo示例中的操作者
     * 这里我设置成管理员，因为他的权限高
     */
    public static final ApplicationUser OPERATOR = UserUtils.getUser("admin");

    @ComponentImport private final ConstantsManager constantsManager;

    @Inject
    public ConstantsAPI(ConstantsManager constantsManager) {
        this.constantsManager = constantsManager;
    }

    public IssueType getSubTaskIssueType() {
        return constantsManager.getSubTaskIssueTypeObjects().stream()
                .filter(e -> StringUtils.equals(e.getName(), "子任务") || StringUtils.equals(e.getName(), "Sub-task")).findFirst().get();
    }


}
