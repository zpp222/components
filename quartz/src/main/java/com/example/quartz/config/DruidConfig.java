package com.example.quartz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class DruidConfig {

	@Autowired
	DruidProperties druidProperties;

	@Bean
	@ConditionalOnMissingBean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(druidProperties.getDriverClassName());
		dataSource.setUrl(druidProperties.getUrl());
		dataSource.setUsername(druidProperties.getUsername());
		dataSource.setPassword(druidProperties.getPassword());
		dataSource.setInitialSize(druidProperties.getInitialSize());
		dataSource.setMaxActive(druidProperties.getMaxActive());
		dataSource.setMinIdle(druidProperties.getMinIdle());
		dataSource.setMaxWait(druidProperties.getMaxWait());
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
		dataSource.setValidationQuery(druidProperties.getValidationQuery());
		dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
		dataSource.setRemoveAbandoned(druidProperties.isRemoveAbandoned());
		dataSource.setRemoveAbandonedTimeout(druidProperties.getRemoveAbandonedTimeoutMillis());
		return dataSource;
	}
}
