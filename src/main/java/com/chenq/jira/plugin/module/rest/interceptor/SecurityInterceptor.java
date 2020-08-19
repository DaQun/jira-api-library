package com.chenq.jira.plugin.module.rest.interceptor;


import com.atlassian.jira.util.SimpleErrorCollection;
import com.atlassian.plugins.rest.common.interceptor.MethodInvocation;
import com.atlassian.plugins.rest.common.interceptor.ResourceInterceptor;
import com.github.daqun.jira.core.validator.BeanValidator;
import com.sun.jersey.api.model.Parameter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * resource的拦截器，可以做一些处理,预留
 */
public class SecurityInterceptor implements ResourceInterceptor {

    @Override
    public void intercept(final MethodInvocation methodInvocation)
            throws IllegalAccessException, InvocationTargetException {
//        HttpContext httpContext = methodInvocation.getHttpContext();
//        AbstractResourceMethod method = methodInvocation.getMethod();
//        Object[] paramValues = methodInvocation.getParameters();
//        Object resource = methodInvocation.getResource();
//        final ApplicationUser user = jac.getUser();
//        ApplicationUser loggedInUser = jac.getLoggedInUser();
//        System.out.println(238957893);
//        System.out.println(loggedInUser.getUsername());
//        methodInvocation.invoke();
//        if (adminRequired(methodInvocation) && !permissionManager.hasPermission(Permissions.ADMINISTER, user)) {
//            methodInvocation.getHttpContext().getResponse()
//                    .setResponse(Response.status(user == null ? Response.Status.UNAUTHORIZED : Response.Status.FORBIDDEN)
//                            .entity(ErrorCollection.of(jac.getI18nHelper().getText("rest.security.admin.required")))
//                            .cacheControl(never()).build());
//        } else {
        List<Parameter> parameterList = methodInvocation.getMethod().getParameters();
        Object[] paramValues = methodInvocation.getParameters();
        for (int i = 0; i < parameterList.size(); i++) {
            Parameter parameter = parameterList.get(i);
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Valid) {
                    SimpleErrorCollection validate = BeanValidator.validate(paramValues[i]);
                    if (validate.hasAnyErrors()) {
                        // TODO 创建一个异常类
                        throw new RuntimeException("错误！！！");
                    }
                }

               if (annotation instanceof NotNull) {
                   checkNotNull(paramValues[i], "[" + parameter.getSourceName() + "] can't be null!");
               }
            }
        }

        methodInvocation.invoke();
    }
}
