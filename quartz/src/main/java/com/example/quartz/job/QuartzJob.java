package com.example.quartz.job;

import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.example.quartz.util.SpringUtils;

@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String serviceStr = jobDataMap.getString("service");
		String methodStr = jobDataMap.getString("method");
		logger.info("开始执行定时任务{}.{}...", serviceStr, methodStr);
		// 通过反射方式调用配置的service.method
		try {
			Object target = SpringUtils.getBean(serviceStr);
			Method method = target.getClass().getMethod(methodStr);
			method.invoke(target);
		} catch (Exception e) {
			logger.info("执行定时任务异常:{}", e.getMessage());
			e.printStackTrace();
		}
	}

}
