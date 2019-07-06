package com.example.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String service = jobDataMap.getString("service");
		String method = jobDataMap.getString("method");
		logger.info("开始执行定时任务{}.{}...", service, method);
		// TODO
		
		try {
			Thread.sleep(2*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
