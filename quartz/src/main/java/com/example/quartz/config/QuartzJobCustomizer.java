package com.example.quartz.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.Ordered;

/**
 * quartz属性扩展 ,适用于公共属性
 * 
 * @author zpp
 *
 */
public interface QuartzJobCustomizer extends Ordered {

	void jobDetailCustomize(BeanDefinition jobDetailFactoryBeanDefinition);

	void cronTriggerCustomize(BeanDefinition cronTriggerFactoryBeanDefinition);
}
