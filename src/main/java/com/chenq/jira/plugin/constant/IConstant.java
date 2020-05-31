package com.chenq.jira.plugin.constant;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserUtils;

/**
 * 2020/4/16 18:09
 * Created by chenq
 */
public interface IConstant {
    /**
     * 默认使用admin操作
     */
    ApplicationUser OPERATOR = UserUtils.getUser("admin");
}
