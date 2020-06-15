package com.chenq.jira.plugin.api.constants;

import com.atlassian.plugin.PluginAccessor;

/**
 * 2020/6/15 9:29
 * Created by chenq
 */
public class PluginConstant {
    /**
     * The unique key identifying this plugin to JIRA.
     */
    public static final String PLUGIN_KEY = "com.chenq.jira.plugin.jira-api-library";

    public static String getPluginVersion(PluginAccessor pluginAccessor) {
        return pluginAccessor.getPlugin(PLUGIN_KEY).getPluginInformation().getVersion();
    }

}
