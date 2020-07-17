!(function ($, contextPath) {
    $(function () {
        /**
         * Issue页面获取issue信息
         */
        JIRA.Issue.getIssueKey()
        JIRA.Issue.getIssueId()
        /**
         * 异步刷新issue信息
          */
        require(["jira/issue", "jira/util/events", 'jira/util/events/types'] , function(issue, jiraUtilEvents, EventTypes) {
            jiraUtilEvents.trigger(EventTypes.REFRESH_ISSUE_PAGE, [issue.getIssueId()])
        });


    })
}(AJS.$, AJS.contextPath()))