package com.example.quartz.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import com.example.quartz.config.QuartzJobCustomizer;

@Component
public class MyQuartzJobCustomizer implements QuartzJobCustomizer {

	@Override
	public void jobDetailCustomize(BeanDefinition jobDetailFactoryBeanDefinition) {
		jobDetailFactoryBeanDefinition.getPropertyValues().add("description", "demo1");
	}

	@Override
	public void cronTriggerCustomize(BeanDefinition cronTriggerFactoryBeanDefinition) {
		cronTriggerFactoryBeanDefinition.getPropertyValues().add("description", "demo2");
	}

}
