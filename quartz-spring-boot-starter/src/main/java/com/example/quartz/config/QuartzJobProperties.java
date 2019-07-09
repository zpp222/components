package com.example.quartz.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.example.quartz.bean.QuartzJobBean;

@Component
@PropertySource(value = { "classpath:quartzJobs.properties" }, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "qrtz.jobs")
public class QuartzJobProperties {

	private List<QuartzJobBean> items;

	public List<QuartzJobBean> getItems() {
		return items;
	}

	public void setItems(List<QuartzJobBean> items) {
		this.items = items;
	}

}
