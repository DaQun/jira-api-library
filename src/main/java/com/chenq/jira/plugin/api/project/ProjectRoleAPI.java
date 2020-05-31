package com.chenq.jira.plugin.api.project;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleActors;
import com.atlassian.jira.security.roles.ProjectRoleImpl;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * jira project role常用API
 * 2020/4/16 18:15
 * Created by chenq
 */
@RequiredArgsConstructor
public class ProjectRoleAPI {
    @ComponentImport private final ProjectRoleManager projectRoleManager;

    /**
     * 查询项目下某一角色的用户
     *
     * @param project         项目
     * @param projectRoleName 项目角色名
     * @return 用户set集合
     */
    public Set<ApplicationUser> getProjectRoleUsers(Project project, String projectRoleName) {
        ProjectRole projectRole = projectRoleManager.getProjectRole(projectRoleName);
        ProjectRoleActors projectRoleActors = projectRoleManager.getProjectRoleActors(projectRole, project);

        return projectRoleActors.getApplicationUsers();
    }

    /**
     * 创建project role
     * @param projectRoleName 名称
     * @param projectRoleDescription 描述
     */
    public void createProjectRole(String projectRoleName, String projectRoleDescription) {
        ProjectRole projectRole = new ProjectRoleImpl(projectRoleName, projectRoleDescription);
        projectRoleManager.createRole(projectRole);
    }

    /**
     * 刪除project role
     * @param projectRoleName 項目角色名
     */
    public void deleteProjectRole(String projectRoleName) {
        projectRoleManager.deleteRole(projectRoleManager.getProjectRole(projectRoleName));
    }
}
