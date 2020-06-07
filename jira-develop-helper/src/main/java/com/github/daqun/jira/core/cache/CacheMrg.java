package com.github.daqun.jira.core.cache;

import com.atlassian.cache.Cache;
import com.github.daqun.jira.core.DsdApp;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CacheMrg
 * @Description 缓存管理类
 * @Date 2019/2/15 18:18
 * @Created by chenq
 */
public class CacheMrg {

    private static final Map<CacheEnum, Cache> cacheEnumCacheMap = initCacheMap();

    /**
     * 初始化缓存map，缓存在用到时才去加载
     * @return
     */
    private static Map initCacheMap() {
        HashMap<Object, Object> map = Maps.newHashMap();
        for (CacheEnum cacheEnum : CacheEnum.values()) {
            map.put(cacheEnum, null);
        }

        return map;
    }

    public static Cache getCache(CacheEnum cacheEnum) {
        Cache cache = cacheEnumCacheMap.computeIfAbsent(cacheEnum,
                e -> DsdApp.getCacheManager().getCache(e.getName(), null, e.getCacheSettings()));

        return cache;
    }

    /**
     * 获取默认的缓存类
     * @return
     */
    public static Cache getDefaultCache() {
        return getCache(CacheEnum.COMMON);
    }

}
