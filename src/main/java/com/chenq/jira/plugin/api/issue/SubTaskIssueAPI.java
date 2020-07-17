package com.chenq.jira.plugin.api.issue;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.config.SubTaskManager;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.*;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.api.constants.ConstantsAPI;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 子任务的api
 * 2020/6/11 9:41
 * Created by chenq
 */
@Component
public class SubTaskIssueAPI extends IssueAPI{
    @ComponentImport
    private final SubTaskManager subTaskManager;
    private final ConstantsAPI constantsAPI;

    @Inject
    public SubTaskIssueAPI(JiraAuthenticationContext jiraAuthenticationContext, IssueManager issueManager, IssueService issueService, IssueFactory issueFactory,
            CustomFieldManager customFieldManager, SubTaskManager subTaskManager, ConstantsAPI constantsAPI) {
        super(jiraAuthenticationContext, issueManager, issueService, issueFactory, customFieldManager);
        this.subTaskManager = subTaskManager;
        this.constantsAPI = constantsAPI;
    }

    /**
     * 创建子任务
     * 示例中子任务的信息都是从父任务复制过来的
     */
    public void createSubTaskIssue(Issue issueObject) throws CreateException {
        MutableIssue subIssue = issueFactory.cloneIssue(issueObject);
        subIssue.setSummary(issueObject.getSummary() + "-sub task" );
        subIssue.setIssueType(constantsAPI.getSubTaskIssueType());
        subIssue.setParentId(issueObject.getId());

        Issue newSubTask = issueManager.createIssueObject(ConstantsAPI.OPERATOR,  subIssue);
        // 子任务创建好后，需要建立link
        subTaskManager.createSubTaskIssueLink(issueObject, newSubTask, ConstantsAPI.OPERATOR);
    }


}
