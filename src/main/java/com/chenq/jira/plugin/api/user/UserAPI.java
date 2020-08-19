package com.chenq.jira.plugin.api.user;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.exception.GroupNotFoundException;
import com.atlassian.crowd.exception.OperationFailedException;
import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.UserNotFoundException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.exception.PermissionException;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserDetails;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 2020/4/16 18:05
 * Created by chenq
 */
@Service
public class UserAPI {

    @ComponentImport
    private final UserManager userManager;

    @Inject
    public UserAPI(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 创建user
     * @return user
     * @throws PermissionException
     * @throws CreateException
     */
    public ApplicationUser createUser() throws PermissionException, CreateException, UserNotFoundException, OperationFailedException, GroupNotFoundException, OperationNotPermittedException {
        String random = RandomStringUtils.randomAlphabetic(5);
        String emailAddress = random + "@chenq.com";
        UserDetails userDetail = new UserDetails(random, random).withDirectory(1L).withPassword(random).withEmail(emailAddress);
        ApplicationUser user = userManager.createUser(userDetail);
        GroupManager groupManager = ComponentAccessor.getGroupManager();
        Group group = groupManager.getGroup("jira-administrators");
        Group group2 = groupManager.getGroup("jira-servicedesk-users");
        groupManager.addUserToGroup(user, group);
        groupManager.addUserToGroup(user, group2);

        return user;
    }

    /**
     * 获取当前登录用户
     * @return 用户
     */
    public ApplicationUser getLoggedUser() {
        return ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    }


}
