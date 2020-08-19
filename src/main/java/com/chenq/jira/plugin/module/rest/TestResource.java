package com.chenq.jira.plugin.module.rest;

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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * 2020/7/30 15:46
 * Created by chenq
 */
@Path("test")
@RequiredArgsConstructor
public class TestResource {

    private final UserManager userManager;

    @GET
    @Path("createUser")
    public Response createUser(@QueryParam("num") int num) throws CreateException, PermissionException, UserNotFoundException, OperationNotPermittedException, GroupNotFoundException, OperationFailedException {
        for (int i = 0; i < num; i++) {
            String random = RandomStringUtils.randomAlphabetic(5);
            String emailAddress = random + "@shdsd-test.com";
            UserDetails userDetail = new UserDetails(random, random).withDirectory(1L).withPassword(random).withEmail(emailAddress);
            ApplicationUser user = userManager.createUser(userDetail);
            GroupManager groupManager = ComponentAccessor.getGroupManager();
            Group group = groupManager.getGroup("jira-administrators");
            groupManager.addUserToGroup(user, group);
        }

        return Response.ok().build();
    }
}
