package com.lcm.domain;

import java.io.Serializable;
import java.util.Objects;

public class SJobDashboardPk implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7625690055100610540L;
	private String jobId;
	private String jobType;
	private String trxId;
	private String woId;
	
	public SJobDashboardPk() {
	}

	public SJobDashboardPk(String jobId, String jobType, String trxId, String woId) {
		this.jobId = jobId;
		this.jobType = jobType;
		this.trxId = trxId;
		this.woId = woId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getTrxId() {
		return trxId;
	}
	
	public void setTrxId(String trxId) {
		this.trxId = trxId;
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
		
		if(!(arg0 instanceof SJobDashboardPk)) return false;
		SJobDashboardPk that = (SJobDashboardPk)arg0;
		return (getJobId() == that.getJobId() &&
				getJobType() == that.getJobType() &&
				getTrxId() == that.getTrxId() &&
				getWoId() == that.getWoId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getJobId(), getJobType(), getTrxId(), getWoId());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "SJobDashboardPk [jobId=" + jobId + ", jobType=" + jobType + ", trxId=" + trxId + ", woId=" + woId + "]";
	}
	
	
}
