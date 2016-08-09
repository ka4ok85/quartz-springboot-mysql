package com.example.rest;

import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import com.example.job.SampleJob;
import com.example.service.SampleService;

@RestController
public class WelcomeController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleService.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @RequestMapping(value = "/api/start/{jobName}/{groupName}/{quantity}/{interval}", method = RequestMethod.GET, produces = "application/json")
    public Boolean addJob(@PathVariable("jobName") String jobName, @PathVariable("groupName") String groupName, @PathVariable("quantity") int quantity, @PathVariable("interval") int interval) {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail job = newJob(SampleJob.class)
              .withIdentity(jobName, groupName)
              .build();

        // Trigger the job to run now
        Trigger trigger = newTrigger()
              .withIdentity(jobName, groupName)
              .startNow()
              .withSchedule(
                  simpleSchedule()
                  .withIntervalInSeconds(interval)
                  .withRepeatCount(quantity)
               )
              .build();

        // Tell quartz to schedule the job using our trigger
        try {
            scheduler.scheduleJob(job, trigger);
            return true;
        } catch (SchedulerException e) {
            LOG.info(e.getMessage());
            return false;
        }
    }
}
