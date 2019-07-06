package com.example.quartz.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware {

	private static BeanDefinitionRegistry registry;
	private static BeanFactory beanFactory;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		SpringUtils.registry = registry;

	}

	public static BeanDefinitionRegistry getBeanDefinitionRegistry() {
		return registry;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	public static Object getBean(String name){
		return beanFactory.getBean(name);
	}

}
