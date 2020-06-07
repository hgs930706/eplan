package com.lcm.domain;

import java.io.Serializable;

public class RJobDashboardPk implements Serializable {
	private static final long serialVersionUID = 1L;

	private String jobId;
	private String woId;

	public RJobDashboardPk() {
	}

	public RJobDashboardPk(String jobId, String woId) {
		this.jobId = jobId;
		this.woId = woId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(this == arg0) return true;
		
		if(!(arg0 instanceof RJobDashboardPk)) return false;
		RJobDashboardPk that = (RJobDashboardPk)arg0;
		return (getJobId() == that.getJobId() &&
				getWoId() == that.getWoId());
	}

	@Override
	public String toString() {
		return "RJobDashboardPk [jobId=" + jobId + ", woId=" + woId + "]";
	}

	
}
