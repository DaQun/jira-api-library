package com.chenq.jira.plugin.api.search;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.compatibility.SearchResultsCompatibility;
import com.chenq.jira.plugin.constant.IConstant;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * jql查询issue
 * 2020/4/17 14:27
 * Created by chenq
 */
@RequiredArgsConstructor
public class IssueSearchAPI {
    @ComponentImport
    private final SearchService searchService;

    /**
     * 查询issue
     * @param jql jql语句
     */
    public List<Issue> searchIssue(String jql) throws SearchException {
        SearchService.ParseResult parseResult = searchService.parseQuery(IConstant.OPERATOR, jql);
        if (parseResult.isValid()) {
            SearchResults searchResults = searchService
                    .search(IConstant.OPERATOR, parseResult.getQuery(), PagerFilter.getUnlimitedFilter());
            // jira 7 的方法 searchResults.getIssues()
            // jira 8 的方法 searchResults.getResults()
            // 这里使用兼容处理类
            return new SearchResultsCompatibility().getResults(searchResults);
        }

        return new ArrayList();
    }
}
