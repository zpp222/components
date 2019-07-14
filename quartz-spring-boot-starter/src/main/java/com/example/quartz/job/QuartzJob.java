package com.example.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.example.quartz.util.SpringUtils;
import com.example.quartz.util.StringConstants;

@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String serviceStr = jobDataMap.getString(StringConstants.SERVICE_STR);
		logger.info("开始执行定时任务{}...", serviceStr);
		try {
			QuartzJobService service = SpringUtils.getBean(serviceStr, QuartzJobService.class);
			service.invoke();
		} catch (Exception e) {
			logger.info("执行定时任务异常:{}", e.getMessage());
		}
	}

}
