package com.chenq.jira.plugin.module.listener;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.Issue;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * issue的监听事件
 * 2020/4/17 17:25
 * Created by chenq
 */
@Slf4j
@Named
public class IssueEventListener implements InitializingBean, DisposableBean {
    @ComponentImport private final EventPublisher eventPublisher;

    @Inject
    public IssueEventListener(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override

    public void destroy() throws Exception {
        this.eventPublisher.unregister(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.eventPublisher.register(this);
    }

    @EventListener
    public void onIssueEvent(IssueEvent event) {
        Issue issue = event.getIssue();
        log.debug("Issue {} event trigger. ", issue.getKey());
        System.out.println(event.getUser());
    }
}