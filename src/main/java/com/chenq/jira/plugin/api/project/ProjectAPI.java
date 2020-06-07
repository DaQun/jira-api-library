package com.chenq.jira.plugin.api.project;

import com.atlassian.jira.bc.project.ProjectCreationData;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.project.AssigneeTypes;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.constant.IConstant;
import lombok.RequiredArgsConstructor;

/**
 * com.github.daqun.jira project常用API
 * 2020/4/16 17:55
 * Created by chenq
 */
@RequiredArgsConstructor
public class ProjectAPI {

    @ComponentImport private final JiraAuthenticationContext jiraAuthenticationContext;
    @ComponentImport private final ProjectService projectService;


    /**
     * 创建project
     * @return project,创建失败则返回null
     */
    public Project createProject() {
        ApplicationUser loggedInUser = jiraAuthenticationContext.getLoggedInUser();
        ProjectCreationData build = new ProjectCreationData.Builder()
                .withName("projectNameDemo")
                .withKey("PDD")
                .withType("business")
                .withLead(loggedInUser)
                .withAssigneeType(AssigneeTypes.PROJECT_LEAD)
                // 需要设置一下template,不然没有配置方案
                .withProjectTemplateKey("com.atlassian.com.github.daqun.jira-core-project-templates:com.github.daqun.jira-core-project-management")
                .build();
        ProjectService.CreateProjectValidationResult createProjectValidationResult = projectService
                .validateCreateProject(loggedInUser, build);
        if (createProjectValidationResult.getErrorCollection().hasAnyErrors()) {
            return null;
        }

        return projectService.createProject(createProjectValidationResult);
    }

    /**
     * 删除project
     * @param projectKey 项目key
     * @return 是否成功
     */
    public boolean deleteProject(String projectKey) {
        ProjectService.DeleteProjectValidationResult deleteProjectValidationResult = projectService
                .validateDeleteProject(IConstant.OPERATOR, projectKey);
        if (deleteProjectValidationResult.isValid()) {
            projectService.deleteProject(IConstant.OPERATOR, deleteProjectValidationResult);
            return true;
        }

        return false;
    }
}