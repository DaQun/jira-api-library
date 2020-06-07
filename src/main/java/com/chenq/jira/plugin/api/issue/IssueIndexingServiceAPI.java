package com.chenq.jira.plugin.api.issue;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.index.IndexException;
import com.atlassian.jira.issue.index.IssueIndexingService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 重新索引
 * jira中有些通过api实现的操作，在jira系统里是搜索不到的，需要执行下索引
 * 比如复制issue的操作，变更经办人的操作，都需要reIndex一下
 * 2020/6/2 16:43
 * Created by chenq
 */
@Component
public class IssueIndexingServiceAPI {
    @ComponentImport
    private final IssueIndexingService issueIndexingService;

    public IssueIndexingServiceAPI(IssueIndexingService issueIndexingService) {
        this.issueIndexingService = issueIndexingService;
    }

    public void reIndex(List<Issue> issueList) {
        try {
            issueIndexingService.reIndexIssueObjects(issueList);
        } catch (IndexException e) {
            e.printStackTrace();
        }
    }
}
