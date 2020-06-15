package com.github.daqun.jira.manager;

import com.atlassian.plugin.PluginInformation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 *  2019/4/10 18:45
 * created by chenq
 */
@RequiredArgsConstructor
public abstract class LicenseManager {

    private final JiraPluginManager jiraPluginManager;

    /**
     * 插件是否启用线上license
     * @return
     */
    public boolean isAtlassianLicensingEnabled() {
        PluginInformation pluginInformation = jiraPluginManager.getPlugin().getPluginInformation();
        Map<String, String> parameters = pluginInformation.getParameters();
        String s = parameters.get("atlassian-licensing-enabled");
        return StringUtils.equals(s, "true");
    }


}
