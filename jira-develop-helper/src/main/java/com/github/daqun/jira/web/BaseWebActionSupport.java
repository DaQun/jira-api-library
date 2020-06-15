package com.github.daqun.jira.web;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import com.atlassian.webresource.api.assembler.RequiredResources;
import com.github.daqun.jira.core.DsdApp;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 2019/3/4 15:07
 * created by chenq
 */
public abstract class BaseWebActionSupport extends JiraWebActionSupport {

    protected PageBuilderService pageBuilderService = DsdApp.getBean(PageBuilderService.class);
    private RequiredResources requiredResources;

    protected final RequiredResources getResources() {
        if (requiredResources == null) {
            requiredResources = pageBuilderService.assembler().resources();
            requireWebResource(commonResources());
        }
        return requiredResources;
    }

    /**
     * 通用资源，子类可以覆写
     * @return
     */
    protected List<String> commonResources() {
        return Lists.newArrayList("dsd-common-resource");
    }

    /**
     * 加载资源方法
     * @param resourceList 资源列表
     */
    protected final void requireWebResource(List<String> resourceList) {
        resourceList.forEach(resourceName -> getResources().requireWebResource(spliceResourceName(resourceName)));
    }

    /**
     * 插件key
     * @return groupId + '.' + artifactId
     */
    protected abstract String getPluginKey();

    /**
     * 拼接Resource名称
     * @param resourceKey
     * @return
     */
    protected final String spliceResourceName(String resourceKey) {
        return getPluginKey() + ":" + resourceKey;
    }
}
