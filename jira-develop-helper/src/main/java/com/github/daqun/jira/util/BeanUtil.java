package com.github.daqun.jira.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.java.ao.Entity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.List;
import java.util.Map;

/**
 * @Classname BeanUtil
 * @Description 未完成
 * @Date 2019/3/14 13:14
 * @Created by chenq
 */
public class BeanUtil {

    private static final List<String> REMOVED_PROPERTY =
            Lists.newArrayList("entityManager", "entityType", "entityProxy", "deleted");

    /**
     * 将entity转为map
     *
     * @param entity
     * @return 除去entity动态生成的一些属性，转成的map
     */
    public static Map<String, Object> entity2Map(Entity entity) {
        Map<String, Object> map = Maps.newHashMap();
        if (entity == null) {
            return map;
        }

        BeanMap beanMap = BeanMap.create(entity);
        for (Object keyObj : beanMap.keySet()) {
            String key = String.valueOf(keyObj);
            Object value = beanMap.get(key);

            if (REMOVED_PROPERTY.contains(key) || value == null) {
                continue;
            }
            if (StringUtils.equals("ID", key)) {
                key = "id";
            }
            map.put(String.valueOf(key), value);
        }

        return map;
    }

    public static void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }


}
