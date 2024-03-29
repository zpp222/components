package com.example.quartz.config;

import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.example.quartz.bean.QuartzJobBean;
import com.example.quartz.job.QuartzJob;
import com.example.quartz.util.JsonUtil;
import com.example.quartz.util.SpringUtils;
import com.example.quartz.util.StringConstants;

@Configuration
@ConditionalOnClass({ JobDetail.class, CronTrigger.class })
@EnableConfigurationProperties(QuartzJobProperties.class)
@AutoConfigureBefore({ QuartzAutoConfiguration.class })
@ComponentScan(basePackages = { "com.example.quartz" })
public class QuartzJobAutoConfig {

	private static Logger logger = LoggerFactory.getLogger(QuartzJobAutoConfig.class);

	private final ObjectProvider<QuartzJobCustomizer> customizers;
	private final QuartzJobProperties quartzJobProperties;

	public QuartzJobAutoConfig(ObjectProvider<QuartzJobCustomizer> customizers,
			QuartzJobProperties quartzJobProperties) {
		this.customizers = customizers;
		this.quartzJobProperties = quartzJobProperties;
	}

	@Bean
	public QuartzJobAutoConfig init() throws Exception {
		BeanDefinitionRegistry beanDefinitionRegistry = SpringUtils.getBeanDefinitionRegistry();
		List<QuartzJobBean> items = quartzJobProperties.getItems();
		logger.info("items {}", JsonUtil.toJsonStr(items));
		String jobDetailPrefix = "JOB";
		String cronTriggerPrefix = "Trigger";
		for (QuartzJobBean job : items) {
			logger.info("初始化{}...", JsonUtil.toJsonStr(job));
			// jobDetailFactoryBean
			RootBeanDefinition jobDetailFactoryBeanDefinition = new RootBeanDefinition();
			jobDetailFactoryBeanDefinition.setBeanClass(JobDetailFactoryBean.class);
			jobDetailFactoryBeanDefinition.setLazyInit(true);
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put(StringConstants.SERVICE_STR, job.getService());
			jobDetailFactoryBeanDefinition.getPropertyValues().add("name", job.getName());
			jobDetailFactoryBeanDefinition.getPropertyValues().add("group", job.getGroup());
			jobDetailFactoryBeanDefinition.getPropertyValues().add("jobClass", QuartzJob.class);
			jobDetailFactoryBeanDefinition.getPropertyValues().add("jobDataMap", jobDataMap);
			jobDetailFactoryBeanDefinition.getPropertyValues().add("durability", true);
			jobDetailCustomize(jobDetailFactoryBeanDefinition); // 扩展接口
			beanDefinitionRegistry.registerBeanDefinition(job.getName() + jobDetailPrefix,
					jobDetailFactoryBeanDefinition);
			// cronTriggerFactoryBean
			RootBeanDefinition cronTriggerFactoryBeanDefinition = new RootBeanDefinition();
			cronTriggerFactoryBeanDefinition.setBeanClass(CronTriggerFactoryBean.class);
			cronTriggerFactoryBeanDefinition.setLazyInit(true);
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("name", job.getName());
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("group", job.getGroup());
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("jobDetail",
					SpringUtils.getBean(job.getName() + jobDetailPrefix));
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("cronExpression", job.getCronExpression());
			cronTriggerFactoryBeanDefinition.getPropertyValues().add("misfireInstruction", job.getMisfireInstruction());
			cronTriggerCustomize(cronTriggerFactoryBeanDefinition); // 扩展接口
			beanDefinitionRegistry.registerBeanDefinition(job.getName() + cronTriggerPrefix,
					cronTriggerFactoryBeanDefinition);
		}
		return null;
	}

	private void jobDetailCustomize(BeanDefinition jobDetailFactoryBeanDefinition) {
		this.customizers.orderedStream()
				.forEach((customizer) -> customizer.jobDetailCustomize(jobDetailFactoryBeanDefinition));
	}

	private void cronTriggerCustomize(BeanDefinition cronTriggerFactoryBeanDefinition) {
		this.customizers.orderedStream()
				.forEach((customizer) -> customizer.cronTriggerCustomize(cronTriggerFactoryBeanDefinition));
	}

}
