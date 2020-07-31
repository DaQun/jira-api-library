package com.chenq.jira.plugin.api.worklog;

import com.atlassian.jira.bc.JiraServiceContextImpl;
import com.atlassian.jira.bc.issue.worklog.*;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.worklog.Worklog;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * 记录工作日志的api
 * 2020/7/30 14:17
 * Created by chenq
 */
@Named
public class WorklogAPI {

    @ComponentImport
    private final WorklogService worklogService;

    @Inject
    public WorklogAPI(WorklogService worklogService) {
        this.worklogService = worklogService;
    }

    /**
     * 创建工作日志
     * 创建工作日志之前，需要先创建一个JiraServiceContextImpl，然后使用worklogService#validateCreate方法（分为validateCreateWithManuallyAdjustedEstimate和validateCreateWithNewEstimate）
     * 接着通过判断JiraServiceContextImpl#getErrorCollection#hasAnyErrors是否有错误值，如果没有则执行创建日志的方法
     * jira的创建日志，有四个方法，分别是
     *  createWithNewRemainingEstimate
     *  createWithManuallyAdjustedEstimate
     *  createAndRetainRemainingEstimate
     *  createAndAutoAdjustRemainingEstimate
     * @param issue 需要创建日志的issue
     */
    public void createWorklog(Issue issue) {
        final WorklogInputParametersImpl.Builder builder = WorklogInputParametersImpl
                .issue(issue)
                .timeSpent("2h")
                .startDate(new Date())
                .comment("参加培训");
        JiraServiceContextImpl jiraServiceContext =
                new JiraServiceContextImpl(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());

        WorklogNewEstimateResult worklogNewEstimateResult = worklogService.validateCreateWithNewEstimate(
                jiraServiceContext,
                // 2d 为新的剩余预估时间
                builder.newEstimate("2d").buildNewEstimate()
        );
        ErrorCollection errorCollection = jiraServiceContext.getErrorCollection();
        if (!errorCollection.hasAnyErrors()) {
            Worklog withNewRemainingEstimate =
                    worklogService.createWithNewRemainingEstimate(jiraServiceContext, worklogNewEstimateResult, true);
        } else {
            throw new RuntimeException("创建worklog失败");
        }
    }

    /**
     * 删除日志
     * @param worklogId 日志id
     */
    public void deleteWorklog(Long worklogId) {
        JiraServiceContextImpl jiraServiceContext = new JiraServiceContextImpl(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
        WorklogResult worklogResult = worklogService.validateDelete(jiraServiceContext, worklogId);

        worklogService.deleteAndRetainRemainingEstimate(jiraServiceContext, worklogResult, true);
    }

    /**
     * 更新worklog
     * @param worklogId 日志id
     */
    public void updateWorklog(Long worklogId) {
        JiraServiceContextImpl jiraServiceContext = new JiraServiceContextImpl(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
        Worklog worklog = worklogService.getById(jiraServiceContext, worklogId);
        final WorklogInputParametersImpl.Builder builder = WorklogInputParametersImpl.builder()
                .startDate(new Date())
                .worklogId(worklogId)
                .timeSpent("3h");
        WorklogInputParameters build = builder.build();

        WorklogResult worklogResult = worklogService.validateUpdate(jiraServiceContext, build);
        worklogService.updateAndAutoAdjustRemainingEstimate(jiraServiceContext, worklogResult, true);
    }
}
