/**
 * jira的rest拦截功能需要写一个package-info类，通过InterceptorChain指定拦截的处理方法
 *
 * 2019/3/28 20:42
 * created by chenq
 */
@InterceptorChain(SecurityInterceptor.class)
package com.chenq.jira.plugin.module.rest;

import com.atlassian.plugins.rest.common.interceptor.InterceptorChain;
import com.chenq.jira.plugin.module.rest.interceptor.SecurityInterceptor;
