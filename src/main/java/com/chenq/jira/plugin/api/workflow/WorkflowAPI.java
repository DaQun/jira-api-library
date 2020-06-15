package com.chenq.jira.plugin.api.workflow;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.constant.IConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 2020/5/29 9:37
 * Created by chenq
 */
@Slf4j
@Component
public class WorkflowAPI {

    @ComponentImport
    private final IssueService issueService;

    @Inject
    public WorkflowAPI(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * 执行工作流的转换
     *
     * @param issue 需要执行的issue
     * @param actionId 动作id，需要issue的当前状态链接到这个actionId，否则不会执行
     */
    public void statusTransition(Issue issue, int actionId) {
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();

        IssueService.TransitionValidationResult validationResult = issueService.validateTransition(IConstant.OPERATOR, issue.getId(), actionId, issueInputParameters);
        if (validationResult.isValid()) {
            IssueService.IssueResult issueResult = issueService.transition(IConstant.OPERATOR, validationResult);
            if (!issueResult.isValid()) {
                log.warn("Failed to transition subtask ${parentIssue.key}, errors: ${issueResult.errorCollection}");
            }
        } else {
            log.warn("Could not transition subtask ${parentIssue.key}, errors: ${validationResult.errorCollection}");
        }
    }

}
