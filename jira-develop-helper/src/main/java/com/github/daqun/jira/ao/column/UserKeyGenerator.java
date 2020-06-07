package com.github.daqun.jira.ao.column;

import com.atlassian.jira.component.ComponentAccessor;
import net.java.ao.EntityManager;
import net.java.ao.ValueGenerator;

import java.util.Optional;

/**
 * @Classname UserKeyGenerator
 * @Description
 * @Date 2019/3/1 17:41
 * @Created by chenq
 */
public class UserKeyGenerator implements ValueGenerator<String>{
    @Override
    public String generateValue(EntityManager entityManager) {
        return Optional.ofNullable(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()).map(e -> e.getKey()).orElse("");
    }
}
