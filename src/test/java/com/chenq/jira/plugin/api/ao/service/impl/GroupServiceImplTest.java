package com.chenq.jira.plugin.api.ao.service.impl;

import com.chenq.jira.plugin.api.constants.GroupTypeEnum;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 2020/6/14 21:38
 * Created by chenq
 */
public class GroupServiceImplTest {


    @Test
    public void testEnumUtils() {
        GroupTypeEnum c = EnumUtils.getEnum(GroupTypeEnum.class, "c");
        Assert.assertNull(c);
        GroupTypeEnum anEnum = EnumUtils.getEnum(GroupTypeEnum.class, "COMPANY");
        Assert.assertEquals(anEnum, GroupTypeEnum.COMPANY);
    }
}
