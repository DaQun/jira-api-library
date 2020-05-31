package com.chenq.jira.plugin;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.cache.Cache;
import com.atlassian.cache.CacheManager;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @Classname DsdApp
 * @Description 系统工具类
 * @Date 2018/12/27 10:19
 * @Created by chenq
 */
public class DsdApp {
    private static ApplicationContext applicationContext;

//    private DsdApp() {
//    }

    public DsdApp(ApplicationContext applicationContext) {
        if (DsdApp.applicationContext == null) {
            DsdApp.applicationContext = applicationContext;
        }
    }

    public static void init(ApplicationContext applicationContext) {
        if (DsdApp.applicationContext == null) {
            DsdApp.applicationContext = applicationContext;
        }
    }

    public static boolean isInited() {
        return applicationContext != null;
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("shdsd plugin does not start");
        }
        return applicationContext;
    }

    public static <T> T getBean(Class<T> beanClazz) {
        try {
            return getApplicationContext().getBean(beanClazz);
        } catch (NoSuchBeanDefinitionException e) {
            return ComponentAccessor.getComponent(beanClazz);
        }
    }

    public static ApplicationUser getLoggedInUser() {
        return getBean(JiraAuthenticationContext.class).getLoggedInUser();
    }

    public static ActiveObjects getAO() {
        return getBean(ActiveObjects.class);
    }


    public static CacheManager getCacheManager() {
        return getBean(CacheManager.class);
    }

}