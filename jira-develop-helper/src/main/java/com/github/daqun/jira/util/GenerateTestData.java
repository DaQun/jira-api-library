package com.github.daqun.jira.util;

import com.atlassian.jira.bc.project.ProjectCreationData;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.IssueFactory;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.project.AssigneeTypes;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserDetails;
import com.atlassian.jira.user.util.UserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDate;

/**
 * @Classname GenerateTestData
 * @Description 创建一些测试数据
 * @Date 2019/3/15 10:27
 * @Created by chenq
 */
@RequiredArgsConstructor
@Slf4j
public class GenerateTestData {
    private final JiraAuthenticationContext jiraAuthenticationContext;
    private final IssueManager issueManager;
    private final ProjectService projectService;
    private final UserManager userManager;

    public void generateProjectForTest(int num) {
        ApplicationUser loggedInUser = jiraAuthenticationContext.getLoggedInUser();
        String projectType = "business";
        LocalDate localDate = new LocalDate();
        String date = localDate.toString("dd");
        for (int i = 0; i < num; i++) {
            String suffix = date + "_" + i;
            String projectKeySuffix = date + i;
            ProjectCreationData build = new ProjectCreationData.Builder().withName("pro" + suffix)
                    .withKey("P" + projectKeySuffix).withType(projectType).withLead(loggedInUser)
                    .withAssigneeType(AssigneeTypes.PROJECT_LEAD)
                    .build();
            ProjectService.CreateProjectValidationResult createProjectValidationResult = projectService
                    .validateCreateProject(loggedInUser, build);
            if (createProjectValidationResult.getErrorCollection().hasAnyErrors()) {
                continue;
            }

            Project project = projectService.createProject(createProjectValidationResult);

            generateIssueForTest(project, 200);
        }
    }

    public void generateIssueForTest(Project project, int num) {
        ApplicationUser user = jiraAuthenticationContext.getLoggedInUser();
        LocalDate localDate = new LocalDate();
        String date = localDate.toString("MMdd");
        for (int i = 0; i < num; i++) {
            String suffix = date + "_" + i;
            IssueFactory issueFactory = ComponentAccessor.getIssueFactory();
            MutableIssue issue = issueFactory.getIssue();
            issue.setIssueTypeId("10000");
            issue.setProjectId(project.getId());
            issue.setDescription("memo" + suffix + i);
            issue.setSummary(project.getName() + "_" + project.getKey() + "_" + i);
            issue.setReporter(user);
            issue.setAssigneeId(user.getKey());
            try {
                issueManager.createIssueObject(jiraAuthenticationContext.getLoggedInUser(), issue);
            } catch (CreateException e) {
                log.warn("create issue for test failed");
            }
        }
    }


    public void generateUserForTest(int num) {
        for (int i = 0; i < num; i++) {
            String random = RandomStringUtils.random(4);
            String displayName = random;
            String emailAddress = random + "@shdsd.com";
            String password = random;
            String userName = random;
            UserDetails userDetail = new UserDetails(userName, displayName);
            userDetail.withDirectory((long) 1);
            userDetail.withPassword(password);
            userDetail.withEmail(emailAddress);
            try {
                userManager.createUser(userDetail);
            } catch (Exception e) {
                log.warn("create user for test failed");
            }
        }
    }
}
