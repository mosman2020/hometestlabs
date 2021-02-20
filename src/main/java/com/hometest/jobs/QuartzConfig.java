/**
 * 
 */
package com.hometest.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author hometest
 *
 */
@Configuration
//@EnableAutoConfiguration
public class QuartzConfig {
	
	private Logger logger = LoggerFactory.getLogger(QuartzConfig.class);
	
	
	@Bean
    public JobDetail unlockusersJobDetails() {
        return JobBuilder.newJob(UnlockUsersJob.class).withIdentity("UnlockUsersJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger unlockusersJobTrigger(JobDetail unlockusersJobDetails) {

        return TriggerBuilder.newTrigger().forJob(unlockusersJobDetails)

                .withIdentity("unlockusersJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 * ? * * *"))
                .build();
    }


    @Bean
    public JobDetail deleteExpiredOtpJobDetails() {
        return JobBuilder.newJob(DeleteExpiredOtpJob.class).withIdentity("deleteExpiredOtpJobDetails")
                .storeDurably().build();
    }

    @Bean
    public Trigger deleteExpiredOtpJobTrigger(JobDetail deleteExpiredOtpJobDetails) {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("somedata", UUID.randomUUID().toString());

        return TriggerBuilder.newTrigger().forJob(deleteExpiredOtpJobDetails)

                .withIdentity("deleteExpiredOtpJobTrigger")

                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 ? * * *"))
                .usingJobData(jobDataMap)
                .build();
    }
    
/*	
	 	@Autowired
	    private ApplicationContext applicationContext;

	    @PostConstruct
	    public void init() {
	        logger.info("Hello world from Spring...");
	    }

	    @Bean
	    public SpringBeanJobFactory springBeanJobFactory() {
	        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
	        logger.debug("Configuring Job factory");
	        jobFactory.setApplicationContext(applicationContext);
	        return jobFactory;
	    }

	    @Bean
	    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job,DataSource quartzDataSource) {

	        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//	        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
	        logger.debug("Setting the Scheduler up");
	        schedulerFactory.setJobFactory(springBeanJobFactory());
	        schedulerFactory.setJobDetails(job);
	        schedulerFactory.setTriggers(trigger);
	        return schedulerFactory;
	    }
	    
	 @Bean
		public JobDetailFactoryBean unlockusersJobDetails() {
		    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		    jobDetailFactory.setJobClass(UnlockUsersJob.class);
		    jobDetailFactory.setDescription("Invoke UnlockUsers Job service...");
		    jobDetailFactory.setDurability(true);
		    return jobDetailFactory;
		}
 
	 @Bean
	    public SimpleTriggerFactoryBean unlockusersTrigger(JobDetail job) {
	        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	        trigger.setJobDetail(job);
	        int frequencyInSec = 60;
	        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
	        trigger.setRepeatInterval(frequencyInSec*1000);
	        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	        trigger.setName("Qrtz_Trigger_unlock_users");
	        return trigger;
	    }

	 @Bean
		public JobDetailFactoryBean deleteExpiredOtpJobDetails() {
		    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		    jobDetailFactory.setJobClass(DeleteExpiredOtpJob.class);
		    jobDetailFactory.setDescription("Invoke Delete Expirted OTP Job service...");
		    jobDetailFactory.setDurability(true);
		    return jobDetailFactory;
		}

	 @Bean
	    public SimpleTriggerFactoryBean deleteExpiredOtpTrigger(JobDetail job) {
	        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	        trigger.setJobDetail(job);
	        int frequencyInSec = 10;
	        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
	        trigger.setRepeatInterval(frequencyInSec*1000);
	        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	        trigger.setName("Qrtz_Trigger_delete_expired_otp");
	        return trigger;
	    }
*/	
}
