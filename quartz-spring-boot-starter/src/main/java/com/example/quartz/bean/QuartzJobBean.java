package com.example.quartz.bean;

import java.io.Serializable;

public class QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 3522480859051163772L;
	private String name;
	private String group;
	private String service;
	private String cronExpression;
	// 0-MISFIRE_INSTRUCTION_SMART_POLICY (default)
	// 1-MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
	// 2-MISFIRE_INSTRUCTION_DO_NOTHING
	private int misfireInstruction;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public int getMisfireInstruction() {
		return misfireInstruction;
	}

	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}

}
