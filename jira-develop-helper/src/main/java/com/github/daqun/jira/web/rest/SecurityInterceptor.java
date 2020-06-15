package com.github.daqun.jira.web.rest;


import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugins.rest.common.interceptor.MethodInvocation;
import com.atlassian.plugins.rest.common.interceptor.ResourceInterceptor;

import javax.ws.rs.ext.Provider;
import java.lang.reflect.InvocationTargetException;

/**
 * @since v6.1
 */
@Provider
public class SecurityInterceptor implements ResourceInterceptor {
    private final JiraAuthenticationContext jac;
    private final PermissionManager permissionManager;

    public SecurityInterceptor(final JiraAuthenticationContext jac, final PermissionManager permissionManager) {
        this.jac = jac;
        this.permissionManager = permissionManager;
    }

    @Override
    public void intercept(final MethodInvocation methodInvocation)
            throws IllegalAccessException, InvocationTargetException {
        final ApplicationUser user = jac.getLoggedInUser();
//        if (adminRequired(methodInvocation) && !permissionManager.hasPermission(Permissions.ADMINISTER, user)) {
//            methodInvocation.getHttpContext().getResponse()
//                    .setResponse(Response.status(user == null ? Response.Status.UNAUTHORIZED : Response.Status.FORBIDDEN)
//                            .entity(ErrorCollection.of(jac.getI18nHelper().getText("rest.security.admin.required")))
//                            .cacheControl(never()).build());
//        } else {
//            methodInvocation.invoke();
//        }
    }

}
