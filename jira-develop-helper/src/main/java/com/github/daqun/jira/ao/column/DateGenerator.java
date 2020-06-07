package com.github.daqun.jira.ao.column;

import net.java.ao.EntityManager;
import net.java.ao.ValueGenerator;

import java.util.Date;

/**
 * @Classname DateGenerator
 * @Description
 * @Date 2019/3/1 17:34
 * @Created by chenq
 */
public class DateGenerator implements ValueGenerator<Date> {
    @Override
    public Date generateValue(EntityManager entityManager) {

        return new Date();
    }
}
