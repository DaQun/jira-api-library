package com.github.daqun.jira.config;

/**
 *  2019/4/10 19:41
 * created by chenq
 */
public interface ConfigBase {
    /**
     * 插件组
     */
    String getGroup();

    /**
     * 设置项
     */
    ConfigItem getConfigItem();

}
