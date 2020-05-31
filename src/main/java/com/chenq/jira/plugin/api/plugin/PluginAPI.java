package com.chenq.jira.plugin.api.plugin;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.Plugin;

import java.util.Collection;

/**
 * 2020/4/17 16:07
 * Created by chenq
 */
public class PluginAPI {

    public Collection<Plugin> getEnabledPlugins() {
       return ComponentAccessor.getPluginAccessor().getEnabledPlugins();
    }
}
