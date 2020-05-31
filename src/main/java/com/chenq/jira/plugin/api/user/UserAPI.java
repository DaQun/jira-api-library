package com.chenq.jira.plugin.api.user;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.exception.PermissionException;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserDetails;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 2020/4/16 18:05
 * Created by chenq
 */
@RequiredArgsConstructor
public class UserAPI {

    @ComponentImport
    private final UserManager userManager;

    /**
     * 创建user
     * @return user
     * @throws PermissionException
     * @throws CreateException
     */
    public ApplicationUser createUser() throws PermissionException, CreateException {
        String random = RandomStringUtils.random(4);
        String displayName = random;
        String emailAddress = random + "@chenq.com";
        String password = random;
        String userName = random;
        UserDetails userDetail = new UserDetails(userName, displayName);
        // jira的默认用户目录
        userDetail.withDirectory((long) 1);
        userDetail.withPassword(password);
        userDetail.withEmail(emailAddress);
        return userManager.createUser(userDetail);
    }

    /**
     * 获取当前登录用户
     * @return 用户
     */
    public ApplicationUser getLoggedUser() {
        return ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    }


}
