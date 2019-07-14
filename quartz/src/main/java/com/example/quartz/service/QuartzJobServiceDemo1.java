package com.example.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.quartz.job.QuartzJobService;

@Component("quartzJobServiceDemo1")
public class QuartzJobServiceDemo1 implements QuartzJobService {
	Logger logger = LoggerFactory.getLogger(QuartzJobServiceDemo1.class);

	@Override
	public void invoke() {
		logger.info("demo1 is running ...");
	}

}
