package com.github.daqun.jira.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Date 2019/4/10 20:40
 * @Created by chenq
 */
@Getter
@Setter
@AllArgsConstructor
public  class ConfigItem  {
    private String configKey;
    private String configName;
    private String configValue;
}
