package com.example.quartz.config;

import java.util.List;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.example.quartz.job.QuartzJob;
import com.example.quartz.util.JsonUtil;
import com.example.quartz.util.SpringUtils;

@Configuration
@EnableConfigurationProperties(QuartzJobProperties.class)
public class QuartzJobConfig implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(QuartzJobConfig.class);

	@Autowired
	QuartzJobProperties quartzJobProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		BeanDefinitionRegistry beanDefinitionRegistry = SpringUtils.getBeanDefinitionRegistry();
		List<QuartzJobBean> items = quartzJobProperties.getItems();
		logger.info("items {}", JsonUtil.toJsonStr(items));
		String jobDetailPrefix = "JOB";
		String cronTriggerPrefix = "Trigger";
		for (QuartzJobBean job : items) {
			logger.info("初始化{}...{}", JsonUtil.toJsonStr(job));
			// jobDetailFactoryBean
			RootBeanDefinition jobDetailFactoryBeanDefinition = new RootBeanDefinition();
			jobDetailFactoryBeanDefinition.setBeanClass(JobDetailFactoryBean.class);
			jobDetailFactoryBeanDefinition.setLazyInit(true);
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("service", job.getService());
			jobDataMap.put("method", job.getMethod());
			jobDetailFactoryBeanDefinition.getPropertyValues().add("jobDataMap", jobDataMap);
			jobDetailFactoryBeanDefinition.getPropertyValues().add("durability", true);
			jobDetailFactoryBeanDefinition.getPropertyValues().add("jobClass", QuartzJob.class);
			beanDefinitionRegistry.registerBeanDefinition(job.getName() + jobDetailPrefix,
					jobDetailFactoryBeanDefinition);
			// cronTriggerFactoryBean
			RootBeanDefinition cronTriggerFactoryBeanDefinition = new RootBeanDefinition();
			cronTriggerFactoryBeanDefinition.setBeanClass(CronTriggerFactoryBean.class);
			cronTriggerFactoryBeanDefinition.setLazyInit(true);
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("jobDetail",
					SpringUtils.getBean(job.getName() + jobDetailPrefix));
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("cronExpression", job.getCronExpression());
			beanDefinitionRegistry.registerBeanDefinition(job.getName() + cronTriggerPrefix,
					cronTriggerFactoryBeanDefinition);
		}

	}

}
