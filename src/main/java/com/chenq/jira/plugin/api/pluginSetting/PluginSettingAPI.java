package com.chenq.jira.plugin.api.pluginSetting;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Jira提供了PROPERTYENTRY 表，可以用来存储、读取一些全局变量
 * 2020/5/29 13:16
 * Created by chenq
 */
@Component
public class PluginSettingAPI {
    @ComponentImport
    private final PluginSettingsFactory pluginSettingsFactory;

    @Inject
    public PluginSettingAPI(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    public String get(String key) {
        return (String) pluginSettingsFactory.createGlobalSettings().get(key);
    }

    public void put(String key, String value) {
        pluginSettingsFactory.createGlobalSettings().put(key, value);
    }
}
