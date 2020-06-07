package com.lcm.web.rest.param;

import java.util.List;

public class ManualRunParam {
	
	private String site;
	private List<String> fab;
	private List<String> area;
	private String planStart;
	private String planEnd;
	private String trxId;
	private String status;
	private String message;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public List<String> getFab() {
		return fab;
	}
	public void setFab(List<String> fab) {
		this.fab = fab;
	}
	public List<String> getArea() {
		return area;
	}
	public void setArea(List<String> area) {
		this.area = area;
	}
	public String getPlanStart() {
		return planStart;
	}
	public void setPlanStart(String planStart) {
		this.planStart = planStart;
	}
	public String getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(String planEnd) {
		this.planEnd = planEnd;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ManualRunParam [site=" + site + ", fab=" + fab + ", area=" + area + ", planStart=" + planStart
				+ ", planEnd=" + planEnd + ", trxId=" + trxId + ", status=" + status + ", message=" + message + "]";
	}
	
	
	
}
