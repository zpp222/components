package com.example.quartz.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import com.example.quartz.config.QuartzJobCustomizer;

@Component
public class MyQuartzJobCustomizer2 implements QuartzJobCustomizer {

	@Override
	public void jobDetailCustomize(BeanDefinition jobDetailFactoryBeanDefinition) {
		jobDetailFactoryBeanDefinition.getPropertyValues().add("description", "demo3");
	}

	@Override
	public void cronTriggerCustomize(BeanDefinition cronTriggerFactoryBeanDefinition) {
		cronTriggerFactoryBeanDefinition.getPropertyValues().add("description", "demo4");
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
