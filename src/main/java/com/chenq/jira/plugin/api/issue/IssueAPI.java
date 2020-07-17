package com.chenq.jira.plugin.api.issue;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.*;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.constant.IConstant;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;

/**
 * 2020/4/16 17:57
 * Created by chenq
 */
@Component
public class IssueAPI {
    @ComponentImport
    protected final JiraAuthenticationContext jiraAuthenticationContext;
    @ComponentImport
    protected final IssueManager issueManager;
    @ComponentImport
    protected final IssueService issueService;
    @ComponentImport
    protected final IssueFactory issueFactory;
    @ComponentImport
    protected final CustomFieldManager customFieldManager;

    @Inject
    public IssueAPI(JiraAuthenticationContext jiraAuthenticationContext, IssueManager issueManager, IssueService issueService, IssueFactory issueFactory,
            CustomFieldManager customFieldManager) {
        this.jiraAuthenticationContext = jiraAuthenticationContext;
        this.issueManager = issueManager;
        this.issueService = issueService;
        this.issueFactory = issueFactory;
        this.customFieldManager = customFieldManager;
    }

    /**
     * 通过issueManager创建issue
     * @param project project从外部穿过来
     * @return issue
     * @throws CreateException
     */
    public Issue createIssueByIssueManager(Project project) throws CreateException {
        ApplicationUser user = jiraAuthenticationContext.getLoggedInUser();
        MutableIssue issue = issueFactory.getIssue();
        issue.setIssueTypeId("10000");
        issue.setProjectId(project.getId());
        issue.setDescription("description");
        issue.setSummary("issue summary");
        issue.setReporter(user);
        issue.setAssigneeId(user.getKey());
        // 自定义字段的赋值,可以通过id或者name获取
//        CustomField customField = customFieldManager.getCustomFieldObjectByName("自定义字段名");
        CustomField customFieldObject = customFieldManager.getCustomFieldObject(10000L);
        if (customFieldObject != null) {
            issue.setCustomFieldValue(customFieldObject, "123");
        }

        return issueManager.createIssueObject(jiraAuthenticationContext.getLoggedInUser(), issue);
    }

    /**
     * // todo 验证
     * 通过issueService创建issue
     * @param project
     * @return issue
     */
    public Issue createIssueByIssueService(Project project) {
        IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
        issueInputParameters.setProjectId(10000L);
        issueInputParameters.setIssueTypeId("10000");
        IssueService.CreateValidationResult createValidationResult = issueService
                .validateCreate(null, issueInputParameters);
        if (createValidationResult.isValid()) {
            return issueService.create(IConstant.OPERATOR, createValidationResult).getIssue();
        }

        return null;
    }

    /**
     * 通过issueManager使用map放方式创建issue
     * @param project project
     * @throws CreateException
     */
    public void createIssueByIssueManagerWithMap(Project project) throws CreateException {
        Map<String, Object> issueMap = Maps.newHashMap();
        issueMap.put("projectObject", project);
        issueMap.put("summary", "第一个 issue");
        issueMap.put("assignee", IConstant.OPERATOR);
        issueMap.put("reporter", IConstant.OPERATOR);
        issueManager.createIssueObject(IConstant.OPERATOR, issueMap);
    }
}
