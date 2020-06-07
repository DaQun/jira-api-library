package com.github.daqun.jira.compatibility;


import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchResults;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname SearchResultsCompatibility
 * @Description 为了兼容jira8中的SearchResults#getIssues方法名改为#getResults
 * @Date 2019/3/25 15:04
 * @Created by chenq
 */
@Slf4j
public class SearchResultsCompatibility {
    // 大版本
    private Integer majorVersion;
    // 小版本
    private Integer minorVersion;
    // jira8中的新方法
    private static final String getResults = "getResults";
    // jira8之前的api的方法
    private static final String getIssues = "getIssues";

    private static Method method;

    public SearchResultsCompatibility() {
        if (method == null) {
            decodeJIRAVersion();
            initMethods();
        }
    }

    /**
     * 解析jira的版本信息
     */
    private void decodeJIRAVersion() {
        ApplicationProperties applicationProperties = ComponentAccessor
                .getApplicationProperties();
        String versionString = applicationProperties.getString("com.github.daqun.jira.version");

        try {
            String versionRegex = "^(\\d+)\\.(\\d+)";
            Pattern versionPattern = Pattern.compile(versionRegex);
            Matcher versionMatcher = versionPattern.matcher(versionString);
            versionMatcher.find();
            majorVersion = Integer.decode(versionMatcher.group(1));
            minorVersion = Integer.decode(versionMatcher.group(2));
        } catch (Exception e) {
            log.error("Can't decode major and minor version number from:" + versionString);
        }
    }

    /**
     * 初始化方法
     */
    private void initMethods() {
        try {
            if (majorVersion > 7) {
                method = SearchResults.class.getMethod(getResults);
            } else {
                method = SearchResults.class.getMethod(getIssues);
            }
        } catch (NoSuchMethodException e) {
            log.error("Can't get method references using reflection", e);
            // fail installation of add-on
            throw new RuntimeException(e);
        }
    }


    public List<Issue> getResults(SearchResults searchResults) {
        try {
            return (List<Issue>) method.invoke(searchResults);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Can't get issue list from SearchResults", e);
            throw new RuntimeException(e);
        }
    }

}