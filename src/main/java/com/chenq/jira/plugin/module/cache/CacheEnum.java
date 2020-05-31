package com.chenq.jira.plugin.module.cache;

import com.atlassian.cache.CacheSettings;
import com.atlassian.cache.CacheSettingsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Classname CacheEnum
 * @Description
 * @Date 2019/2/15 18:07
 * @Created by chenq
 */
public enum CacheEnum {
    // 默认的缓存配置,一小时过期
    COMMON("common-cache", simpleSetting(1, TimeUnit.HOURS)),
    // 短时缓存，一分钟过期
    SHORT("short-time-cache", simpleSetting(1, TimeUnit.MINUTES)),
    // 1天过期
    MEDIUM("medium-time-cache", simpleSetting(1, TimeUnit.DAYS)),
    // 长时缓存，7天
    LONG("long-time-cache", simpleSetting(7, TimeUnit.DAYS))
    ;

    private String name;
    private CacheSettings cacheSettings;

    public String getName() {
        return name;
    }

    public CacheSettings getCacheSettings() {
        return cacheSettings;
    }

    CacheEnum(String name, CacheSettings cacheSettings) {
        this.name = name;
        this.cacheSettings = cacheSettings;
    }

    /**
     * 缓存的简单配置
     * @param num 时长
     * @param timeUnit 单位
     * @return 缓存设置
     */
    private static CacheSettings simpleSetting(int num, TimeUnit timeUnit) {
        return new CacheSettingsBuilder().expireAfterWrite(num, timeUnit).flushable().build();
    }

}
