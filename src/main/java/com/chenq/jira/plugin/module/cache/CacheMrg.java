package com.chenq.jira.plugin.module.cache;

import com.atlassian.cache.Cache;
import com.chenq.jira.plugin.DsdApp;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CacheMrg
 * @Description 缓存管理类
 * @Date 2019/2/15 18:18
 * @Created by chenq
 */
@RequiredArgsConstructor
public class CacheMrg {

    private static final Map<CacheEnum, Cache> cacheEnumCacheMap = initCacheMap();
//    @ComponentImport
//    private final CacheManager cacheManager;

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
