package com.chenq.jira.plugin.module.job;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.scheduler.*;
import com.atlassian.scheduler.config.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

/**
 * 定时执行任务
 */
@Named
@RequiredArgsConstructor
public class ScheduleJob implements JobRunner, InitializingBean, DisposableBean {

    private static final long EVERY_MINUTE = TimeUnit.MINUTES.toMillis(1);
    private static final JobRunnerKey JOB_RUNNER_KEY = JobRunnerKey.of(ScheduleJob.class.getName());
    private static final JobId JOB_ID = JobId.of(ScheduleJob.class.getName());
    @ComponentImport
    private final SchedulerService scheduler;

    @Override
    public JobRunnerResponse runJob(JobRunnerRequest request) {
        System.out.println("Running scheduleJob at " + request.getStartTime());
        return JobRunnerResponse.success();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Starting...");

        scheduler.registerJobRunner(JOB_RUNNER_KEY, this);
        final JobConfig jobConfig = JobConfig.forJobRunnerKey(JOB_RUNNER_KEY).withRunMode(RunMode.RUN_LOCALLY)
                .withSchedule(Schedule.forInterval(EVERY_MINUTE, null));

        try {
            scheduler.scheduleJob(JOB_ID, jobConfig);
        } catch (SchedulerServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Stopping...");
        scheduler.unscheduleJob(JOB_ID);
    }

}