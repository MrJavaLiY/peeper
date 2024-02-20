package com.monitor.peeper.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PollJob {
    @Resource
    MyJob myJob;

    @Scheduled(fixedDelay = 5000)
    public void task1() {
        myJob.job1();
    }
}
