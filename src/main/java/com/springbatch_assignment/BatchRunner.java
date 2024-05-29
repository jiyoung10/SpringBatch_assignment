package com.springbatch_assignment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job memberWorkJob;

    public BatchRunner(JobLauncher jobLauncher, @Qualifier("memberWorkJob") Job memberWorkJob) {
        this.jobLauncher = jobLauncher;
        this.memberWorkJob = memberWorkJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(memberWorkJob, jobParameters);
    }
}
