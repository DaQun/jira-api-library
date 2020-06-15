package com.github.daqun.jira.manager;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.Plugin;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.PluginInformation;
import com.atlassian.upm.api.license.PluginLicenseManager;
import com.atlassian.upm.api.license.entity.PluginLicense;
import lombok.RequiredArgsConstructor;

/**
 * 插件管理类
 * 2019/4/11 21:20
 * created by chenq
 */
@RequiredArgsConstructor
public class JiraPluginManager {

    private final PluginLicenseManager pluginLicenseManager;

    /**
     * 获取插件key
     */
    public String getPluginKey() {
        return pluginLicenseManager.getPluginKey();
    }

    /**
     * 获取插件的version
     */
    public String getPluginVersion() {
        return getPluginInformation().getVersion();
    }

    private PluginAccessor pluginAccessor = ComponentAccessor.getPluginAccessor();
    /**
     * 获取插件描述类
     */
    public Plugin getPlugin() {
        return pluginAccessor.getEnabledPlugin(getPluginKey());
    }

    /**
     * 获取插件的信息类，这个类中有关于在atlassian-plugin.xml中配置的很多信息
     */
    public PluginInformation getPluginInformation() {
        return getPlugin().getPluginInformation();
    }

    public boolean isValid() {
        String pluginKey = pluginLicenseManager.getPluginKey();
        PluginLicense pluginLicense = pluginLicenseManager.getLicense().get();

        if (pluginLicense == null) {
            return false;
        }

        return false;
    }
}
