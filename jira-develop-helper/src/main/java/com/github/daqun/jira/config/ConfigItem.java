package com.github.daqun.jira.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  2019/4/10 20:40
 * created by chenq
 */
@Getter
@Setter
@AllArgsConstructor
public  class ConfigItem  {
    private String configKey;
    private String configName;
    private String configValue;
}
