package com.github.daqun.jira.ao.column;

import net.java.ao.EntityManager;
import net.java.ao.ValueGenerator;

import java.util.Date;

/**
 * 数据库日期字段生成器
 * 2019/3/1 17:34
 * created by chenq
 */
public class DateGenerator implements ValueGenerator<Date> {
    @Override
    public Date generateValue(EntityManager entityManager) {

        return new Date();
    }
}
